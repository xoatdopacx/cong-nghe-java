# Chương 14: Bảo mật ứng dụng với Spring Security (Lab 14)

> **Học phần:** Công nghệ Java – IT3242  
> **Sinh viên:** Nguyễn Văn Hùng | **MSV:** 20230752 | **Lớp:** DCCNTT 14.2  
> **Trường:** Đại học Công nghệ Đông Á (EAUT)

---

## 🎯 Mục tiêu
- Tích hợp **Spring Security 6.x** vào ứng dụng Spring Boot 3.3.
- Xây dựng **Form đăng nhập tùy chỉnh** (`/login`) và trang xử lý lỗi truy cập **403 Forbidden** (`/error/403`).
- Phân quyền URL theo vai trò: `ROLE_ADMIN` và `ROLE_USER`.
- Bảo vệ các thao tác Thêm, Sửa, Xóa sinh viên và Quản lý môn học (`/courses/**`).
- Tích hợp **Thymeleaf Security Extras** để ẩn/hiện nút chức năng và hiển thị danh tính người dùng theo vai trò.
- Lưu trữ người dùng trong cơ sở dữ liệu với mật khẩu băm chuẩn **BCrypt** (`UserDetailsService` + `DaoAuthenticationProvider`).

---

## 🛠️ Công nghệ & Thư viện
- **JDK:** 21 LTS
- **Spring Boot:** 3.3.2
- **Spring Security 6.3**
- **Thymeleaf Extras Springsecurity6**
- **Spring Data JPA & Hibernate ORM**
- **H2 Database (In-Memory)**
- **BCryptPasswordEncoder**
- **Maven:** 3.9.x

---

## 🚀 Hướng dẫn chạy ứng dụng

```bash
mvn spring-boot:run
```
- **Trang chủ:** `http://localhost:8080/`
- **Đăng nhập:** `http://localhost:8080/login`
- **Quản lý Sinh viên:** `http://localhost:8080/students`
- **Quản lý Khóa học (ADMIN only):** `http://localhost:8080/courses`

### Tài khoản thử nghiệm:
- `admin` / `123456` (ROLE_ADMIN — toàn quyền)
- `hung` / `123456` (ROLE_ADMIN — toàn quyền)
- `user` / `123456` (ROLE_USER — chỉ xem danh sách, không có quyền thêm/sửa/xóa, bị chặn 403 khi vào `/courses`)

---

## 📋 Danh sách 10 bài tập hoàn thành
1. **Bài 1:** Thêm dependency Spring Security và Thymeleaf Extras trong `pom.xml`.
2. **Bài 2:** Cấu hình `SecurityFilterChain` cơ bản với `@EnableWebSecurity`.
3. **Bài 3:** Phân quyền URL chi tiết (`permitAll`, `authenticated`, `hasRole("ADMIN")`).
4. **Bài 4:** Xây dựng trang đăng nhập tùy chỉnh `/login` với thông báo lỗi và đăng xuất.
5. **Bài 5:** Ẩn/hiện nút Thêm mới, Sửa, Xóa theo role bằng `sec:authorize`.
6. **Bài 6:** Bảo vệ đường dẫn `/courses/**` chỉ cho phép `ROLE_ADMIN`.
7. **Bài 7:** Tạo trang báo lỗi tùy biến 403 Forbidden (`error/403.html`).
8. **Bài 8:** Tùy biến thanh điều hướng menu thích ứng theo quyền hạn người dùng.
9. **Bài 9:** Bảo vệ kép cho chức năng xóa sinh viên (tầng UI và tầng URL).
10. **Bài 10:** Lưu trữ tài khoản người dùng trong CSDL với mật khẩu mã hóa BCrypt.
