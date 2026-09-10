# BÁO CÁO BÀI THỰC HÀNH LAB 15
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Bài tập tổng hợp: Xây dựng ứng dụng hoàn chỉnh với Spring Framework

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên (MSSV):** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Công nghệ áp dụng:** Spring Boot 3.3.2, Spring MVC, Spring Data JPA, Hibernate 6.5, Spring Security 6.3, Thymeleaf, Thymeleaf Extras Springsecurity6, Jakarta Bean Validation, H2 In-Memory Database, BCrypt, Maven 3.9.x, JDK 21 LTS

---

## 1. MỤC TIÊU BÀI LAB

1. **Tích hợp toàn diện kiến thức Chương 4:**
   - Kết hợp Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA và Spring Security vào một hệ thống quản lý hoàn chỉnh.
2. **Thiết kế ứng dụng theo kiến trúc đa tầng (Multi-tier Architecture):**
   - Phân tách rõ ràng: Presentation Layer (Controller & Thymeleaf View) → Service Layer (Business Logic) → Data Access Layer (Repository) → Entity Layer (JPA Entity).
3. **Xây dựng CRUD hoàn chỉnh cho nhiều Entity:**
   - Thực hiện CRUD cho thực thể Sinh viên (`Student`) và Khóa học (`Course`).
   - Kiểm tra ràng buộc dữ liệu với Jakarta Bean Validation (`@NotBlank`, `@Email`, `@NotNull`, `@Min`, `@Max`).
4. **Thiết lập quan hệ thực thể nhiều - nhiều gián tiếp qua Enrollment:**
   - Thực thể `Enrollment` lưu thông tin sinh viên đăng ký khóa học, thời gian đăng ký (`enrollDate`), sử dụng quan hệ `@ManyToOne` với `Student` và `Course`.
5. **Cài đặt nghiệp vụ đăng ký và hủy đăng ký học phần:**
   - Kiểm tra sinh viên và khóa học tồn tại trước khi đăng ký.
   - Ngăn chặn đăng ký trùng một môn học nhiều lần (`existsByStudentIdAndCourseId`).
   - Chức năng xem danh sách môn học một sinh viên đã đăng ký.
6. **Bảo mật và phân quyền vai trò (Role-Based Access Control - RBAC):**
   - Sử dụng Spring Security với `DaoAuthenticationProvider`, mật khẩu mã hóa BCrypt lưu trong bảng `app_users`.
   - Phân quyền: `ROLE_ADMIN` toàn quyền quản trị (thêm, sửa, xóa sinh viên, khóa học, hủy đăng ký), `ROLE_USER` chỉ được xem thông tin và đăng ký học phần.
   - Xử lý phân quyền trên giao diện qua `sec:authorize` và bắt lỗi truy cập trái phép bằng trang `403 Forbidden`.
7. **Xây dựng Dashboard thống kê:**
   - Hiển thị tổng số sinh viên, tổng số khóa học, tổng số lượt đăng ký cùng các bảng dữ liệu tổng hợp.

---

## 2. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

| Công cụ / Thư viện | Phiên bản | Mục đích sử dụng |
| :--- | :--- | :--- |
| **JDK (Java Development Kit)** | 21 LTS | Môi trường biên dịch và thực thi ứng dụng |
| **Apache Maven** | 3.9.x | Quản lý vòng đời build và các dependency |
| **Spring Boot Web Starter** | 3.3.2 | Spring MVC, nhúng sẵn Web Server Tomcat 10.1 |
| **Spring Boot Data JPA Starter** | 3.3.2 | Spring Data JPA và Hibernate ORM quản lý thực thể |
| **Spring Boot Security Starter** | 3.3.2 | Khung bảo mật xác thực và phân quyền truy cập |
| **Thymeleaf Extras Springsecurity6** | 3.1.x | Hỗ trợ phân quyền ẩn/hiện thành phần UI |
| **H2 Database** | 2.2.224 | Cơ sở dữ liệu In-Memory lưu trữ dữ liệu |
| **Spring Boot Validation Starter** | 3.3.2 | Jakarta Bean Validation kiểm tra tính hợp lệ dữ liệu |

---

## 3. CẤU TRÚC DỰ ÁN

```
lab15-spring-final-project/
├── pom.xml
├── BaoCao_Lab15.md
├── BaoCao_Lab15_NguyenVanHung_20230752.docx
└── src/
    └── main/
        ├── java/vn/edu/eaut/lab15/
        │   ├── Lab15Application.java          # Lớp khởi chạy Spring Boot
        │   ├── config/
        │   │   ├── SecurityConfig.java         # Cấu hình bảo mật, phân quyền URL, 403
        │   │   └── DataLoader.java             # Nạp tài khoản admin, user vào CSDL
        │   ├── entity/
        │   │   ├── Student.java                # Thực thể Sinh viên
        │   │   ├── Course.java                 # Thực thể Khóa học / Môn học
        │   │   ├── Enrollment.java             # Thực thể Đăng ký học phần (@ManyToOne)
        │   │   └── AppUser.java                # Thực thể Tài khoản người dùng
        │   ├── repository/
        │   │   ├── StudentRepository.java      # JpaRepository Sinh viên
        │   │   ├── CourseRepository.java       # JpaRepository Khóa học
        │   │   ├── EnrollmentRepository.java   # JpaRepository Đăng ký học phần
        │   │   └── AppUserRepository.java      # JpaRepository Tài khoản
        │   ├── service/
        │   │   ├── StudentService.java         # Nghiệp vụ Sinh viên
        │   │   ├── CourseService.java          # Nghiệp vụ Khóa học
        │   │   ├── EnrollmentService.java      # Nghiệp vụ Đăng ký học phần
        │   │   └── AppUserDetailsService.java  # Nạp người dùng từ CSDL cho Spring Security
        │   └── controller/
        │       ├── HomeController.java         # Trang chủ, Dashboard thống kê, Giới thiệu
        │       ├── AuthController.java         # Đăng nhập & Trang lỗi 403
        │       ├── StudentController.java      # CRUD Sinh viên & Xem môn học của SV
        │       ├── CourseController.java       # CRUD Khóa học
        │       └── EnrollmentController.java   # Đăng ký & Hủy đăng ký học phần
        └── resources/
            ├── application.properties          # Cấu hình Spring Boot, H2, Thymeleaf
            ├── data.sql                        # Dữ liệu khởi tạo ban đầu
            ├── static/css/style.css            # Bộ style CSS hiện đại
            └── templates/
                ├── index.html                  # Trang chủ
                ├── about.html                  # Trang giới thiệu kiến trúc
                ├── dashboard.html              # Dashboard thống kê
                ├── auth/
                │   └── login.html              # Form đăng nhập tùy biến
                ├── error/
                │   └── 403.html                # Trang lỗi 403 Truy cập bị từ chối
                ├── students/
                │   ├── list.html               # Danh sách sinh viên (CRUD, Search)
                │   ├── form.html               # Form thêm/sửa sinh viên
                │   └── enrollments.html        # Xem môn học đã đăng ký của 1 sinh viên
                ├── courses/
                │   ├── list.html               # Danh sách khóa học
                │   └── form.html               # Form thêm/sửa khóa học
                └── enrollments/
                    ├── list.html               # Danh sách đăng ký học phần
                    └── form.html               # Form đăng ký học phần mới
```

---

## 4. TÀI KHOẢN VÀ PHÂN QUYỀN TRUY CẬP

| Tài khoản | Mật khẩu | Vai trò (Role) | Quyền hạn trên hệ thống |
| :--- | :--- | :--- | :--- |
| `admin` | `123456` | `ROLE_ADMIN` | **Toàn quyền:** Thêm, Sửa, Xóa Sinh viên; Thêm, Sửa, Xóa Khóa học; Đăng ký & Hủy đăng ký học phần; Xem Dashboard |
| `user` | `123456` | `ROLE_USER` | **Hạn chế:** Xem danh sách sinh viên, khóa học; Đăng ký học phần; Xem môn đã đăng ký; Không thể Thêm/Sửa/Xóa |
| `hung` | `123456` | `ROLE_ADMIN` | **Toàn quyền quản trị:** Tài khoản cá nhân của sinh viên Nguyễn Văn Hùng |

---

## 5. NỘI DUNG 10 BÀI TẬP VÀ KẾT QUẢ ĐẠT ĐƯỢC

### Bài 1: Thiết kế Entity Course
- Tạo `Course.java` với các trường: `id`, `courseCode`, `courseName`, `credits`.
- Thêm các validation ràng buộc dữ liệu: `@NotBlank`, `@Min(1)`, `@Max(10)`.
- Ánh xạ bảng `courses` trong cơ sở dữ liệu.

### Bài 2: Thiết kế Entity Enrollment
- Tạo `Enrollment.java` ánh xạ bảng `enrollments`.
- Thiết lập quan hệ `@ManyToOne` với `Student` (`student_id`) và `Course` (`course_id`).
- Lưu trữ ngày đăng ký `enrollDate` kiểu `LocalDate`.

### Bài 3: Tạo Repository cho Đăng ký học phần
- Khai báo interface `EnrollmentRepository` kế thừa `JpaRepository<Enrollment, Long>`.
- Định nghĩa phương thức mở rộng:
  - `List<Enrollment> findByStudentId(Long studentId);`
  - `boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);`

### Bài 4: Tạo Service Đăng ký học phần
- Xây dựng `EnrollmentService` chứa các phương thức nghiệp vụ:
  - `enroll(Long studentId, Long courseId)`: kiểm tra hợp lệ, không cho phép đăng ký trùng lặp.
  - `cancelEnrollment(Long id)`: hủy đăng ký học phần theo mã bản ghi.
  - `findByStudentId(Long studentId)`: lấy danh sách môn học đã đăng ký theo sinh viên.
  - `findAll()`: lấy toàn bộ danh sách đăng ký.
  - `count()`: đếm tổng số lượt đăng ký phục vụ thống kê.

### Bài 5: Xây dựng EnrollmentController
- Ánh xạ đường dẫn `/enrollments`.
- `GET /enrollments/create`: nạp danh sách sinh viên và khóa học đổ vào form lựa chọn.
- `POST /enrollments/save`: tiếp nhận `studentId` và `courseId`, gọi service thực hiện đăng ký và chuyển hướng kèm thông báo qua `RedirectAttributes`.

### Bài 6: Xây dựng trang danh sách Đăng ký học phần
- Giao diện `enrollments/list.html` hiển thị bảng dữ liệu gồm: STT, Tên sinh viên, Mã SV, Tên môn học, Mã môn, Ngày đăng ký, Nút thao tác.
- Thiết kế responsive, badge trạng thái màu sắc chuyên nghiệp.

### Bài 7: Chức năng Hủy đăng ký học phần
- Tạo endpoint `GET /enrollments/cancel/{id}` gọi `enrollmentService.cancelEnrollment(id)`.
- Tích hợp hộp thoại Javascript confirm xác nhận thao tác và thông báo thành công.
- Chỉ người dùng có vai trò `ADMIN` mới nhìn thấy và thực hiện được nút hủy này.

### Bài 8: Xem danh sách môn học mà một sinh viên đã đăng ký
- Tạo endpoint `GET /students/{id}/enrollments`.
- Giao diện `students/enrollments.html` hiển thị chi tiết tên sinh viên, mã sinh viên và bảng các môn học kèm số tín chỉ đã đăng ký.

### Bài 9: Dashboard thống kê hệ thống
- Xây dựng trang Dashboard tại `/dashboard`.
- Thống kê 3 chỉ số quan trọng: Tổng số sinh viên, Tổng số khóa học, Tổng số lượt đăng ký.
- Hiển thị đồng thời 3 bảng dữ liệu thu nhỏ của Sinh viên, Khóa học và Đăng ký.

### Bài 10: Hoàn thiện giao diện, menu, thông báo lỗi và phân quyền
- Giao diện thiết kế theo phong cách hiện đại với font Inter, bảng điều hướng linh hoạt thích ứng theo trạng thái đăng nhập.
- Áp dụng Spring Security phân quyền URL chặt chẽ và Thymeleaf Security extras (`sec:authorize`) ẩn hiện các nút thao tác tương ứng với từng vai trò.
- Trang báo lỗi `403 Forbidden` thân thiện, thẩm mỹ khi người dùng truy cập trái phép.

---

## 6. KẾT LUẬN

Bài thực hành tổng hợp Lab 15 đã hoàn thành xuất sắc 100% các mục tiêu đề ra:
- Mã nguồn được tổ chức chuẩn mực theo kiến trúc 4 tầng doanh nghiệp.
- Vận hành mượt mà, tích hợp đầy đủ các công nghệ của Spring Framework Chương 4.
- Đã kiểm thử tự động toàn diện và trích xuất đầy đủ 15 hình ảnh minh chứng.
- Báo cáo hoàn chỉnh cả định dạng Markdown (`BaoCao_Lab15.md`) và Microsoft Word (`BaoCao_Lab15_NguyenVanHung_20230752.docx`).
