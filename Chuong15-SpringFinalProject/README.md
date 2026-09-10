# Chương 15: Bài tập tổng hợp — Ứng dụng Spring Framework hoàn chỉnh (Lab 15)

> **Học phần:** Công nghệ Java – IT3242  
> **Sinh viên:** Nguyễn Văn Hùng | **MSV:** 20230752 | **Lớp:** DCCNTT 14.2  
> **Trường:** Đại học Công nghệ Đông Á (EAUT)

---

## 🎯 Mục tiêu bài lab
- Tổng hợp và tích hợp toàn diện các nội dung cốt lõi của Chương 4: **Spring Boot 3.3**, **Spring MVC**, **Thymeleaf**, **Spring Data JPA**, và **Spring Security 6**.
- Xây dựng ứng dụng theo **kiến trúc đa tầng (Multi-tier Architecture)**: Controller – Service – Repository – Entity – Security.
- Thiết kế mô hình quan hệ nhiều-nhiều gián tiếp giữa Sinh viên (`Student`) và Khóa học (`Course`) qua thực thể Đăng ký học phần (`Enrollment`).
- Cài đặt đầy đủ các chức năng nghiệp vụ:
  - **Quản lý Sinh viên:** Thêm, sửa, xóa, tìm kiếm theo từ khóa.
  - **Quản lý Khóa học:** Thêm, sửa, xóa môn học.
  - **Đăng ký học phần:** Chọn sinh viên và môn học, kiểm tra không cho phép đăng ký trùng, hủy đăng ký.
  - **Lịch sử đăng ký:** Xem danh sách môn học đã đăng ký theo từng sinh viên.
  - **Dashboard thống kê:** Tổng số sinh viên, tổng số khóa học, tổng số lượt đăng ký.
- Bảo mật và phân quyền vai trò người dùng (**RBAC**) chặt chẽ:
  - `ROLE_ADMIN`: Toàn quyền quản trị hệ thống.
  - `ROLE_USER`: Xem thông tin và đăng ký học phần, bị chặn 403 khi cố truy cập chức năng quản trị.
  - Form đăng nhập tùy biến (`/login`), đăng xuất an toàn, trang lỗi 403 Forbidden (`/error/403`).

---

## 🛠️ Công nghệ & Thư viện sử dụng
- **JDK:** 21 LTS
- **Spring Boot:** 3.3.2
- **Spring Security 6.3**
- **Spring Data JPA & Hibernate ORM 6.5**
- **Thymeleaf & Thymeleaf Extras Springsecurity6**
- **Jakarta Bean Validation**
- **H2 In-Memory Database**
- **BCrypt Password Encoder**
- **Apache Maven:** 3.9.x

---

## 🚀 Hướng dẫn chạy ứng dụng

```bash
cd Chuong15-SpringFinalProject
mvn spring-boot:run
```

- **Trang chủ:** `http://localhost:8080/`
- **Đăng nhập:** `http://localhost:8080/login`
- **Quản lý Sinh viên:** `http://localhost:8080/students`
- **Quản lý Khóa học:** `http://localhost:8080/courses`
- **Đăng ký Học phần:** `http://localhost:8080/enrollments`
- **Dashboard thống kê:** `http://localhost:8080/dashboard`
- **Giới thiệu kiến trúc:** `http://localhost:8080/about`

### Tài khoản thử nghiệm:
- `admin` / `123456` (`ROLE_ADMIN` — toàn quyền hệ thống)
- `hung` / `123456` (`ROLE_ADMIN` — toàn quyền)
- `user` / `123456` (`ROLE_USER` — chỉ xem và đăng ký học phần)

---

## 📋 Danh sách 10 bài tập đã hoàn thành
1. **Bài 1:** Thiết kế entity `Course` quản lý thông tin môn học/khóa học với validation.
2. **Bài 2:** Thiết kế entity `Enrollment` thể hiện quan hệ sinh viên đăng ký môn học (`@ManyToOne`).
3. **Bài 3:** Tạo `EnrollmentRepository` với các truy vấn `findByStudentId` và `existsByStudentIdAndCourseId`.
4. **Bài 4:** Tạo `EnrollmentService` xử lý nghiệp vụ đăng ký học phần và chặn đăng ký trùng lặp.
5. **Bài 5:** Xây dựng `EnrollmentController` với form chọn sinh viên, chọn môn học và xử lý lưu.
6. **Bài 6:** Xây dựng trang danh sách đăng ký học phần gồm sinh viên, môn học, ngày đăng ký.
7. **Bài 7:** Viết chức năng hủy đăng ký học phần kèm hộp thoại xác nhận.
8. **Bài 8:** Viết chức năng xem danh sách môn học mà một sinh viên đã đăng ký (`/students/{id}/enrollments`).
9. **Bài 9:** Thêm Dashboard thống kê tổng số sinh viên, khóa học và lượt đăng ký (`/dashboard`).
10. **Bài 10:** Hoàn thiện giao diện hiện đại, menu linh hoạt, thông báo flash alert và phân quyền toàn bộ hệ thống.
