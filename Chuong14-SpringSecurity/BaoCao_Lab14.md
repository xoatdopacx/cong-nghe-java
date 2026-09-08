# BÁO CÁO BÀI THỰC HÀNH LAB 14
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Bảo mật ứng dụng với Spring Security, Thymeleaf Security & Phân quyền người dùng theo Role

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên:** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Công nghệ áp dụng:** Spring Boot 3.3.2, Spring Security 6.3, Spring Data JPA, Thymeleaf, Thymeleaf Extras Springsecurity6, Jakarta Bean Validation, H2 In-Memory Database, BCrypt, Maven 3.9.x, JDK 21 LTS

---

## 1. MỤC TIÊU BÀI LAB

1. **Hiểu rõ cơ chế bảo mật của Spring Security:**
   - **Authentication (Xác thực):** Kiểm tra định danh người dùng đăng nhập bằng tên đăng nhập và mật khẩu (sử dụng thuật toán mã hóa mật khẩu `BCryptPasswordEncoder`).
   - **Authorization (Phân quyền):** Kiểm soát quyền truy cập tài nguyên theo từng URL tương ứng với vai trò (`ROLE_ADMIN`, `ROLE_USER`).
2. **Xây dựng Form đăng nhập tùy chỉnh (Custom Login Page):**
   - Thiết kế giao diện đăng nhập hiện đại với thông báo lỗi khi đăng nhập thất bại và thông báo khi đăng xuất thành công.
3. **Phân quyền truy cập URL theo vai trò:**
   - Cho phép mọi người truy cập trang chủ (`/`), trang thông tin (`/about`), và các tài nguyên tĩnh (`/css/**`, `/js/**`).
   - Yêu cầu người dùng đăng nhập khi truy cập danh sách sinh viên (`/students/**`).
   - Giới hạn quyền thêm, sửa, xóa sinh viên và quản lý khóa học (`/courses/**`) chỉ dành cho người dùng có vai trò `ROLE_ADMIN`.
4. **Xử lý trang lỗi từ chối truy cập 403 (Access Denied):**
   - Điều hướng người dùng không đủ quyền hạn đến trang thông báo lỗi 403 tùy biến với giao diện thân thiện, nút quay lại trang chủ.
5. **Tích hợp Thymeleaf Security Extras:**
   - Ẩn/hiện linh hoạt các nút chức năng (Thêm mới, Sửa, Xóa) trên bảng dữ liệu bằng thuộc tính `sec:authorize="hasRole('ADMIN')"`.
   - Hiển thị thông tin người dùng đang đăng nhập và vai trò trên thanh điều hướng (`sec:authentication="name"`).
6. **Lưu trữ tài khoản người dùng trong cơ sở dữ liệu (Database-backed Authentication):**
   - Định nghĩa Entity `AppUser`, Repository `AppUserRepository`, triển khai `UserDetailsService` (`AppUserDetailsService`) để nạp thông tin user từ bảng CSDL `app_users`.
   - Khởi tạo tài khoản mật khẩu đã băm bằng BCrypt qua `CommandLineRunner`.

---

## 2. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

| Công cụ / Thư viện | Phiên bản | Mục đích sử dụng |
| :--- | :--- | :--- |
| **JDK (Java Development Kit)** | 21 LTS | Môi trường biên dịch và thực thi ứng dụng |
| **Apache Maven** | 3.9.x | Quản lý vòng đời build và các dependency |
| **Spring Boot Starter Web** | 3.3.2 | Spring MVC, nhúng sẵn Web Server Apache Tomcat 10.1 |
| **Spring Boot Starter Security** | 3.3.2 | Khung bảo mật Spring Security 6.3 |
| **Thymeleaf Extras Springsecurity6** | 3.1.x | Hỗ trợ cú pháp phân quyền bảo mật trên giao diện Thymeleaf |
| **Spring Boot Starter Data JPA** | 3.3.2 | Spring Data JPA và Hibernate ORM quản lý thực thể CSDL |
| **H2 Database** | 2.2.224 | Cơ sở dữ liệu In-Memory lưu trữ dữ liệu sinh viên, khóa học và tài khoản |
| **Spring Boot Starter Validation** | 3.3.2 | Jakarta Bean Validation kiểm tra ràng buộc dữ liệu đầu vào |

---

## 3. CẤU TRÚC DỰ ÁN

```
lab14-spring-security/
├── pom.xml
├── BaoCao_Lab14.md
├── BaoCao_Lab14_NguyenVanHung_20230752.docx
└── src/
    └── main/
        ├── java/vn/edu/eaut/lab14/
        │   ├── Lab14Application.java          # Lớp khởi chạy Spring Boot
        │   ├── config/
        │   │   ├── SecurityConfig.java         # Cấu hình SecurityFilterChain & phân quyền URL
        │   │   └── DataLoader.java             # Nạp tài khoản mặc định băm BCrypt vào CSDL
        │   ├── entity/
        │   │   ├── Student.java                # Thực thể Sinh viên
        │   │   ├── Course.java                 # Thực thể Khóa học
        │   │   └── AppUser.java                # Thực thể Người dùng hệ thống
        │   ├── repository/
        │   │   ├── StudentRepository.java      # JpaRepository Sinh viên
        │   │   ├── CourseRepository.java       # JpaRepository Khóa học
        │   │   └── AppUserRepository.java      # JpaRepository Người dùng
        │   ├── service/
        │   │   ├── StudentService.java         # Nghiệp vụ Sinh viên
        │   │   ├── CourseService.java          # Nghiệp vụ Khóa học
        │   │   └── AppUserDetailsService.java  # Triển khai UserDetailsService từ CSDL
        │   └── controller/
        │       ├── HomeController.java         # Điều hướng trang chủ và giới thiệu
        │       ├── AuthController.java         # Xử lý trang đăng nhập và trang lỗi 403
        │       ├── StudentController.java      # CRUD Sinh viên
        │       └── CourseController.java       # CRUD Khóa học (chỉ dành cho ADMIN)
        └── resources/
            ├── application.properties          # Cấu hình H2, Hibernate, Logging
            ├── data.sql                        # Dữ liệu ban đầu
            ├── static/css/style.css            # Bộ CSS tùy biến giao diện
            └── templates/
                ├── index.html                  # Trang chủ (hiển thị menu theo role)
                ├── auth/login.html             # Form đăng nhập tùy biến
                ├── error/403.html              # Trang thông báo lỗi 403 Forbidden
                ├── students/list.html          # Danh sách sinh viên (ẩn/hiện nút Sửa/Xóa)
                ├── students/form.html          # Form thêm/sửa sinh viên
                ├── courses/list.html           # Danh sách khóa học
                └── courses/form.html           # Form thêm/sửa khóa học
```

---

## 4. TÀI KHOẢN TRẢI NGHIỆM VÀ PHÂN QUYỀN

| Tên tài khoản | Mật khẩu | Vai trò (Role) | Quyền hạn trong hệ thống |
| :--- | :--- | :--- | :--- |
| `admin` | `123456` | `ROLE_ADMIN` | Toàn quyền: Xem, Thêm mới, Sửa, Xóa sinh viên; Quản lý Khóa học (`/courses/**`) |
| `hung` | `123456` | `ROLE_ADMIN` | Toàn quyền quản trị viên |
| `user` | `123456` | `ROLE_USER` | Chỉ xem: Xem danh sách sinh viên, không thể Thêm/Sửa/Xóa sinh viên; bị chặn (403) khi vào `/courses/**` |

---

## 5. NỘI DUNG 10 BÀI TẬP VÀ KẾT QUẢ TRIỂN KHAI

### Bài 1: Thêm dependency Spring Security
- Bổ sung `spring-boot-starter-security` và `thymeleaf-extras-springsecurity6` trong `pom.xml`.
- Tự động kích hoạt cơ chế bảo vệ CSRF và chuỗi bộ lọc bảo mật `SecurityFilterChain`.

### Bài 2 & 3: Cấu hình phân quyền URL
- Tạo lớp `SecurityConfig` có đánh dấu `@Configuration` và `@EnableWebSecurity`.
- Cấu hình phân quyền đường dẫn:
  - Trang công khai: `/`, `/about`, `/css/**`, `/js/**`, `/images/**`.
  - Trang yêu cầu vai trò `ROLE_ADMIN`: `/students/create`, `/students/save`, `/students/edit/**`, `/students/delete/**`, `/courses/**`.
  - Trang yêu cầu đăng nhập: `/students/**`.

### Bài 4: Trang đăng nhập tùy chỉnh
- Viết `AuthController` ánh xạ `GET /login`.
- Thiết kế giao diện `auth/login.html` với CSRF token tự động, hiển thị thông báo lỗi khi đăng nhập thất bại (`?error`) và thông báo sau khi đăng xuất (`?logout`).

### Bài 5 & 8: Tích hợp Thymeleaf Security ẩn/hiện chức năng
- Sử dụng namespace `xmlns:sec="http://www.thymeleaf.org/extras/spring-security"`.
- Dùng `sec:authorize="hasRole('ADMIN')"` để chỉ hiển thị nút Thêm mới, Sửa, Xóa đối với ADMIN. Người dùng thường (`ROLE_USER`) chỉ thấy danh sách dữ liệu.
- Hiển thị tên đăng nhập và vai trò qua `sec:authentication="name"`.

### Bài 6: Phân quyền đường dẫn `/courses/**`
- Cấu hình `.requestMatchers("/courses/**").hasRole("ADMIN")`.
- Khi tài khoản `user` cố gắng nhập URL `/courses`, hệ thống chặn ngay lập tức và chuyển tiếp sang trang xử lý lỗi.

### Bài 7: Trang thông báo lỗi 403 Forbidden
- Thiết kế trang `error/403.html` thông báo người dùng không có đủ quyền hạn truy cập tài nguyên.
- Đăng ký trang xử lý lỗi từ chối truy cập: `.exceptionHandling(ex -> ex.accessDeniedPage("/error/403"))`.

### Bài 9: Bảo vệ kép cho chức năng xóa dữ liệu
- Bảo vệ tầng giao diện: Ẩn nút Xóa trên giao diện của người dùng thường.
- Bảo vệ tầng phân quyền URL: `/students/delete/**` bắt buộc phải có vai trò `ROLE_ADMIN`. Khi truy cập trực tiếp bằng URL, người dùng không có quyền sẽ bị chặn.

### Bài 10: Quản lý người dùng trong CSDL với mã hóa BCrypt
- Khai báo Entity `AppUser` tương ứng bảng `app_users` gồm các cột `id`, `username`, `password`, `role`, `enabled`.
- Triển khai `AppUserDetailsService` thực thi interface `UserDetailsService`, chuyển đổi thực thể `AppUser` thành đối tượng `UserDetails`.
- Khởi tạo dữ liệu người dùng với mật khẩu băm qua `BCryptPasswordEncoder` trong lớp `DataLoader`.

---

## 6. KẾT LUẬN

Bài thực hành Lab 14 đã hoàn thành xuất sắc toàn bộ 10 bài tập theo yêu cầu đề cương môn học:
1. Nắm vững và áp dụng thành thạo Spring Security 6.x trong kiến trúc Spring Boot hiện đại.
2. Thiết lập cơ chế bảo vệ nhiều lớp: tầng URL, tầng controller và tầng hiển thị giao diện người dùng.
3. Chuyển đổi thành công từ xác thực in-memory sang xác thực qua CSDL thực tế với mật khẩu băm chuẩn công nghiệp BCrypt.
