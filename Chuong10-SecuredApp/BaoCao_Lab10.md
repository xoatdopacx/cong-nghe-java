# BÁO CÁO BÀI THỰC HÀNH LAB 10
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Thêm Login, Role, Bảo Vệ URL và Hoàn Thiện Ứng Dụng Đa Lớp trong Jakarta EE

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên:** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Database:** MySQL 9.5 (`lab10_secured`)
- **Framework & Libraries:** Jakarta EE 10 (Servlet 6.0, JSP 3.1, JSTL 3.0), Hibernate ORM 6.4.4.Final, Jakarta Persistence 3.1, Apache Tomcat 10.1.x

---

## 1. MỤC TIÊU BÀI LAB

1. **Xác thực người dùng (Authentication):** Xây dựng luồng Đăng nhập / Đăng xuất kiểm tra thông tin tài khoản người dùng trực tiếp từ bảng `users` trong cơ sở dữ liệu MySQL qua JPA.
2. **Quản lý phiên (Session Management):** Lưu trữ thông tin đối tượng người dùng (`User`) vào `HttpSession` (`currentUser`), kiểm soát vòng đời session khi đăng xuất (`session.invalidate()`).
3. **Phân quyền dựa trên vai trò (Role-Based Access Control - RBAC):** Thiết lập enum `Role` với 3 cấp độ: `ADMIN`, `STAFF`, `USER`.
4. **Bảo vệ URL bằng Filter:** 
   - `AuthenticationFilter`: Chặn các request chưa đăng nhập truy cập các URL nội bộ (`/admin/*`, `/staff/*`, `/user/*`, `/dashboard`), tự động điều hướng về `login.jsp`.
   - `AuthorizationFilter`: Kiểm tra role của người dùng hiện tại; nếu không đủ quyền sẽ chuyển tiếp sang trang lỗi `403.jsp`.
5. **Giao diện Menu động theo Role:** Menu thanh điều hướng (Sidebar) chỉ hiển thị các chức năng tương ứng với quyền hạn của người dùng đang đăng nhập.
6. **Xử lý trang lỗi chuẩn hóa:** Cấu hình và hiển thị trang lỗi thân thiện `403.jsp` (Forbidden), `404.jsp` (Not Found), `500.jsp` (Internal Server Error).
7. **Quản trị người dùng & Hồ sơ cá nhân:** 
   - ADMIN: Quản lý danh sách tài khoản, thêm mới, sửa vai trò, khóa/mở khóa tài khoản (toggle active).
   - USER/STAFF/ADMIN: Xem và cập nhật hồ sơ (họ tên, email), đổi mật khẩu cá nhân có validate.
8. **Hoàn thiện ứng dụng đa module:** Tích hợp đầy đủ 5 module nghiệp vụ: Sinh Viên, Lớp Học, Môn Học, Điểm, Sản Phẩm.

---

## 2. BẢNG PHÂN QUYỀN & QUY TẮC BẢO VỆ URL

### 2.1. Ma trận phân quyền theo vai trò

| Vai trò (Role) | Mô tả quyền hạn | Các URL được phép truy cập |
| :--- | :--- | :--- |
| **ADMIN** | Quản trị toàn bộ hệ thống: Quản lý người dùng, sinh viên, lớp học, môn học, điểm, sản phẩm, dashboard | `/dashboard`, `/admin/*`, `/staff/*`, `/user/*` |
| **STAFF** | Nhân viên nghiệp vụ: Quản lý điểm số, sản phẩm, xem dashboard, hồ sơ cá nhân | `/dashboard`, `/staff/*`, `/user/*` |
| **USER** | Người dùng tiêu chuẩn: Xem dashboard, cập nhật hồ sơ cá nhân, đổi mật khẩu | `/dashboard`, `/user/*` |
| **GUEST** | Chưa đăng nhập: Chỉ được xem trang đăng nhập và tài nguyên tĩnh | `/login.jsp`, `/auth`, `/resources/*` |

### 2.2. Phân loại URL

| URL Pattern | Yêu cầu quyền | Xử lý khi vi phạm |
| :--- | :--- | :--- |
| `/login.jsp`, `/auth` | Public (Công khai) | Cho phép truy cập tự do |
| `/resources/*` | Public (Tải CSS, JS, Fonts) | Cho phép truy cập tự do |
| `/dashboard` | Yêu cầu đăng nhập (`ADMIN`, `STAFF`, `USER`) | Redirect về `/login.jsp` |
| `/user/*` | Yêu cầu đăng nhập | Redirect về `/login.jsp` |
| `/staff/*` | Yêu cầu quyền `ADMIN` hoặc `STAFF` | Redirect về `/error/403.jsp` |
| `/admin/*` | Yêu cầu quyền `ADMIN` | Redirect về `/error/403.jsp` |

---

## 3. CẤU TRÚC DỰ ÁN

```text
lab10-secured-app/
├── pom.xml
├── database/
│   └── lab10_secured.sql
├── BaoCao_Lab10.md
└── src/main/
    ├── java/vn/edu/eaut/lab10/
    │   ├── config/
    │   │   └── JPAUtil.java
    │   ├── model/
    │   │   ├── Role.java (Enum: ADMIN, STAFF, USER)
    │   │   ├── User.java (Entity users)
    │   │   ├── SinhVien.java
    │   │   ├── LopHoc.java
    │   │   ├── MonHoc.java
    │   │   ├── Diem.java
    │   │   └── SanPham.java
    │   ├── repository/
    │   │   ├── BaseRepository.java (Generic CRUD JPA)
    │   │   ├── UserRepository.java
    │   │   ├── SinhVienRepository.java
    │   │   ├── LopHocRepository.java
    │   │   ├── MonHocRepository.java
    │   │   ├── DiemRepository.java
    │   │   └── SanPhamRepository.java
    │   ├── service/
    │   │   ├── AuthService.java (Xác thực, đổi MK, sửa profile)
    │   │   └── SinhVienService.java (Validate nghiệp vụ)
    │   ├── controller/
    │   │   ├── AuthController.java (/auth - Login/Logout)
    │   │   ├── DashboardController.java (/dashboard)
    │   │   ├── ProfileController.java (/user/profile)
    │   │   ├── UserManagementController.java (/admin/users)
    │   │   ├── SinhVienController.java (/admin/sinh-vien)
    │   │   ├── LopHocController.java (/admin/lop-hoc)
    │   │   ├── MonHocController.java (/admin/mon-hoc)
    │   │   ├── DiemController.java (/staff/diem)
    │   │   └── SanPhamController.java (/staff/san-pham)
    │   ├── filter/
    │   │   ├── EncodingFilter.java (UTF-8)
    │   │   ├── AuthenticationFilter.java (Bảo vệ session)
    │   │   └── AuthorizationFilter.java (Phân quyền Role)
    │   └── listener/
    │       └── AppListener.java (Khởi tạo JPA + Seed Database)
    ├── resources/META-INF/
    │   └── persistence.xml
    └── webapp/
        ├── login.jsp
        ├── error/
        │   ├── 403.jsp
        │   ├── 404.jsp
        │   └── 500.jsp
        ├── resources/
        │   └── css/
        │       └── style.css
        └── WEB-INF/
            ├── web.xml
            └── views/
                ├── header.jsp (Menu phân quyền động theo Role)
                ├── footer.jsp
                ├── dashboard.jsp
                ├── admin/
                │   ├── sinhvien/ (list.jsp, form.jsp)
                │   ├── lophoc/   (list.jsp, form.jsp)
                │   ├── monhoc/   (list.jsp, form.jsp)
                │   └── users/    (list.jsp, form.jsp)
                ├── staff/
                │   ├── diem/     (list.jsp, form.jsp)
                │   └── sanpham/  (list.jsp, form.jsp)
                └── user/
                    └── profile.jsp
```

---

## 4. TÀI KHOẢN THỰC NGHIỆM & KIỂM THỬ

Hệ thống tự động nạp dữ liệu mẫu ban đầu qua `AppListener` hoặc qua file script `lab10_secured.sql`:

| Email | Mật khẩu | Vai trò | Chức năng kiểm thử |
| :--- | :--- | :--- | :--- |
| `admin@eaut.edu.vn` | `admin123` | **ADMIN** | Toàn quyền: Thấy toàn bộ menu, quản lý người dùng, sinh viên, lớp, môn, điểm, sản phẩm |
| `staff@eaut.edu.vn` | `staff123` | **STAFF** | Nhân viên: Thấy menu Điểm & Sản phẩm. Truy cập `/admin/users` sẽ bị chuyển về 403 |
| `user@eaut.edu.vn` | `user123` | **USER** | Người dùng: Chỉ thấy menu Dashboard và Hồ sơ cá nhân. Truy cập `/admin/*` hoặc `/staff/*` sẽ bị 403 |

---

## 5. CHI TIẾT CÁC BÀI TẬP THỰC HIỆN

### Bài 1: Entity User và Role
- `Role.java`: Enum gồm 3 giá trị `ADMIN`, `STAFF`, `USER`.
- `User.java`: Entity ánh xạ bảng `users`, sử dụng `@Enumerated(EnumType.STRING)` để lưu tên vai trò dưới dạng chuỗi trong CSDL, thuộc tính `active` kiểm soát trạng thái hoạt động của tài khoản.

### Bài 2: UserRepository và AuthService
- `UserRepository.java`: Kế thừa `BaseRepository<User>`, bổ sung method `findByEmail(email)` dùng JPQL truy vấn chính xác người dùng theo địa chỉ email.
- `AuthService.java`: 
  - `login(email, password)`: Kiểm tra tài khoản tồn tại, trạng thái `active == true`, và mật khẩu khớp.
  - `changePassword(userId, oldPass, newPass, confirmPass)`: Kiểm tra mật khẩu cũ, độ dài mật khẩu mới (>= 4 ký tự) và khớp xác nhận.
  - `updateProfile(userId, fullName, email)`: Kiểm tra định dạng email regex và chống trùng email với người dùng khác.

### Bài 3: AuthController (Đăng nhập / Đăng xuất)
- Xử lý `POST /auth`: Tiếp nhận email & password từ form, gọi `AuthService.login`. Khi thành công, lưu `User` vào `session.setAttribute("currentUser", user)` và chuyển hướng tới `/dashboard`.
- Xử lý `GET /auth?action=logout`: Gọi `session.invalidate()` hủy bỏ toàn bộ phiên làm việc, xóa cache trình duyệt và chuyển hướng về `/login.jsp`.

### Bài 4: AuthenticationFilter (Bảo vệ URL)
- Đăng ký filter trên các pattern: `/admin/*`, `/staff/*`, `/user/*`, `/dashboard`.
- Kiểm tra `session == null || session.getAttribute("currentUser") == null` thì điều hướng ngay về `/login.jsp`.
- Thiết lập header HTTP chống cache (`Cache-Control: no-cache, no-store, must-revalidate`) ngăn chặn người dùng ấn nút Back của trình duyệt sau khi đã đăng xuất.

### Bài 5: AuthorizationFilter (Phân quyền theo Role)
- Đăng ký filter trên các pattern: `/admin/*`, `/staff/*`.
- Lấy `currentUser` từ session và kiểm tra URI:
  - Nếu đường dẫn chứa `/admin/` mà `user.getRole() != Role.ADMIN` $\rightarrow$ điều hướng về `/error/403.jsp`.
  - Nếu đường dẫn chứa `/staff/` mà role không phải `ADMIN` hoặc `STAFF` $\rightarrow$ điều hướng về `/error/403.jsp`.

### Bài 6: Tạo dữ liệu tài khoản mẫu
- Xây dựng `AppListener` (`@WebListener`) tự động kiểm tra số lượng tài khoản trong bảng `users`. Nếu bảng rỗng, hệ thống tự động chèn 3 tài khoản mẫu với đủ 3 vai trò và bộ dữ liệu 5 sinh viên, 3 lớp học, 4 môn học, 8 điểm, 5 sản phẩm.

### Bài 7: Quản lý người dùng cho ADMIN
- Servlet `UserManagementController` (`/admin/users`) hỗ trợ đầy đủ các thao tác:
  - Danh sách tài khoản có tìm kiếm theo email/họ tên.
  - Thêm tài khoản mới, phân vai trò (`ADMIN`, `STAFF`, `USER`).
  - Sửa thông tin tài khoản, đổi vai trò, cập nhật mật khẩu mới.
  - Khóa / Mở khóa tài khoản (`toggle active`) an toàn ngay trên danh sách.

### Bài 8: Trang hồ sơ cá nhân
- Servlet `ProfileController` (`/user/profile`) cho phép người dùng hiện tại xem thông tin, chỉnh sửa họ và tên, email liên hệ với cơ chế xác thực dữ liệu đầy đủ.

### Bài 9: Đổi mật khẩu
- Tích hợp trong trang hồ sơ cá nhân, yêu cầu nhập mật khẩu cũ, mật khẩu mới và xác nhận mật khẩu mới. Kiểm tra nghiệp vụ chặt chẽ trước khi cập nhật vào CSDL.

### Bài 10: Hiển thị Menu theo Role
- Tệp `header.jsp` sử dụng thẻ JSTL `<c:if test="${user.role == '...'}">` để ẩn/hiển thị menu tương ứng.
- Đảm bảo tính nhất quán: Menu giao diện được tinh gọn, kết hợp lớp bảo vệ chắc chắn từ `AuthorizationFilter` ở backend.

### Bài 11: Trang lỗi 403, 404, 500
- Xây dựng các trang giao diện lỗi hiện đại trong thư mục `/error/` (`403.jsp`, `404.jsp`, `500.jsp`) với mã lỗi gradient, thông điệp rõ ràng và nút bấm quay về Dashboard / Đăng nhập.
- Cấu hình thẻ `<error-page>` trong `web.xml`.

### Bài 12 & 13: Hoàn thiện giao diện & Kiểm soát luồng
- Đồng bộ hóa toàn bộ hệ thống CSS hiện đại (Dark theme cao cấp, bo góc mượt mà, hiệu ứng hover, badge trạng thái màu sắc phân biệt theo role, bảng dữ liệu responsive).
- Thiết lập phân tầng kiến trúc 4 lớp chuẩn mực: Controller $\rightarrow$ Service $\rightarrow$ Repository $\rightarrow$ JPA Entity $\rightarrow$ MySQL.

---

## 6. TRẢ LỜI CÂU HỎI CỦNG CỐ

#### 1. Authentication và Authorization khác nhau như thế nào?
- **Authentication (Xác thực):** Là quá trình xác minh danh tính của người dùng (xác định "Bạn là ai?"), ví dụ thông qua việc kiểm tra email/tên đăng nhập và mật khẩu khi đăng nhập hệ thống.
- **Authorization (Phân quyền/Ủy quyền):** Là quá trình kiểm tra quyền hạn của người dùng đã được xác thực (xác định "Bạn được phép làm những gì?"), ví dụ kiểm tra vai trò `ADMIN` mới được xóa sinh viên hoặc quản lý người dùng, còn `STAFF` chỉ được nhập điểm.

#### 2. Vì sao cần dùng Filter để bảo vệ URL thay vì chỉ ẩn menu?
- Việc ẩn menu trên giao diện chỉ mang tính chất trải nghiệm người dùng (UX). Nếu kẻ tấn công hoặc người dùng biết được URL (ví dụ gõ trực tiếp `/admin/users` trên thanh địa chỉ trình duyệt), họ vẫn có thể gửi request đến server.
- Sử dụng `Filter` ở tầng backend đảm bảo mọi HTTP request đều phải đi qua chốt kiểm soát an ninh trước khi đến Servlet Controller. Nếu không đủ quyền, request sẽ bị chặn đứng ngay lập tức.

#### 3. Dữ liệu người dùng nên lưu gì trong session và không nên lưu gì?
- **Nên lưu:** Các thông tin định danh và quyền hạn cơ bản cần dùng thường xuyên như `id`, `email`, `fullName`, `role`, thời điểm đăng nhập.
- **Không nên lưu:** Mật khẩu (kể cả mật khẩu đã băm), thông tin thẻ tín dụng, dữ liệu quá lớn (danh sách hàng nghìn bản ghi) gây quá tải bộ nhớ RAM của Web Server.

#### 4. Khi người dùng không đủ quyền, ứng dụng nên xử lý như thế nào?
- Không cho phép thực hiện hành động và chuyển tiếp (redirect hoặc forward) người dùng đến trang thông báo lỗi `403 Forbidden` với giao diện thân thiện.
- Cung cấp nút điều hướng quay về Dashboard hoặc trang chủ để người dùng không bị kẹt.
- Ghi log (security log) để quản trị viên có thể theo dõi các hành vi truy cập trái phép.

#### 5. Vì sao mật khẩu không nên lưu dạng plain text trong hệ thống thật?
- Nếu cơ sở dữ liệu bị lộ lọt (do SQL Injection, lộ file backup, hay truy cập trái phép), mật khẩu dạng plain text sẽ bị lộ trực tiếp toàn bộ tài khoản người dùng.
- Trong hệ thống thực tế, bắt buộc phải mã hóa mật khẩu một chiều kèm chuỗi ngẫu nhiên (Salt) bằng các thuật toán mạnh như **BCrypt**, **Argon2**, hoặc **PBKDF2**.

#### 6. Các URL public và private trong ứng dụng cần được phân loại thế nào?
- **Public URL:** Các tài nguyên công khai ai cũng có thể truy cập mà không cần đăng nhập, bao gồm: trang login (`/login.jsp`, `/auth`), trang chủ công khai, các tài nguyên tĩnh (`/resources/css/*`, `/resources/js/*`, `/images/*`).
- **Private URL:** Các trang yêu cầu xác thực hoặc phân quyền: `/dashboard`, `/user/*` (yêu cầu đăng nhập), `/staff/*` (yêu cầu STAFF/ADMIN), `/admin/*` (chỉ dành cho ADMIN).

#### 7. Transaction có vai trò gì khi quản lý người dùng và đổi mật khẩu?
- Đảm bảo tính nguyên tử (Atomicity) và toàn vẹn dữ liệu (Consistency - ACID). Ví dụ khi tạo tài khoản đồng thời ghi log hoặc tạo hồ sơ phụ thuộc, nếu một bước lỗi thì toàn bộ thao tác sẽ được `rollback`, không để lại dữ liệu rác hoặc trạng thái không nhất quán trong CSDL.

#### 8. Ứng dụng Lab 10 đã tổng hợp những nội dung nào từ Lab 6 đến Lab 9?
- **Từ Lab 6:** Kiến trúc Maven Web, Servlet Controller, Filter (`EncodingFilter`), Listener (`ServletContextListener`), HttpSession.
- **Từ Lab 7:** Mô hình kiến trúc MVC (Model 2), tách biệt Controller - View (JSP/JSTL) - Repository.
- **Từ Lab 8:** Kỹ thuật kiểm tra tính hợp lệ dữ liệu (Validation), xử lý thông báo lỗi/thành công thân thiện.
- **Từ Lab 9:** Jakarta Persistence API (JPA), Hibernate ORM, Entity Mapping quan hệ (`@OneToMany`, `@ManyToOne`), Generic Repository Pattern (`BaseRepository`), Quản lý Transaction.
- **Bổ sung mới tại Lab 10:** Xác thực đăng nhập (Login/Logout), phân quyền Role-Based Access Control (RBAC), chuỗi Filter bảo mật URL (`AuthenticationFilter`, `AuthorizationFilter`), Dynamic UI theo Role, Module quản lý tài khoản và hồ sơ người dùng.

---

## 7. KẾT LUẬN & TỰ ĐÁNH GIÁ

- **Kết quả đạt được:** Hoàn thành 100% tất cả các bài tập (từ Bài 1 đến Bài 13) và các tiêu chí của bài thực hành tổng hợp cuối chuỗi Lab.
- **Độ tin cậy:** Mã nguồn biên dịch thành công không có lỗi (`BUILD SUCCESS`), kiểm thử đầy đủ các kịch bản phân quyền giữa ADMIN, STAFF, USER.
- **Tự đánh giá điểm:** 10/10.
