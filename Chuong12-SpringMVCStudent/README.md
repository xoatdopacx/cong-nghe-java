# Lab 12 – Phát triển ứng dụng web với Spring MVC

**Học phần:** Công nghệ Java - IT3242  
**Sinh viên:** Nguyễn Văn Hùng  
**MSSV:** 20230752  
**Lớp:** DCCNTT 14.2  

---

## 1. Giới thiệu

Lab 12 thuộc Chương 4 về phát triển ứng dụng web hiện đại với **Spring Boot 3.3.2** và **Spring MVC**:
- Tổ chức Controller, Model, Service và View chuẩn mực.
- Thymeleaf Form binding dữ liệu tự động hai chiều với `@ModelAttribute`.
- Kiểm tra tính hợp lệ dữ liệu (Validation) với Jakarta Bean Validation (`@NotBlank`, `@Size`, `@Email`) và `BindingResult`.
- Xây dựng đầy đủ hệ thống CRUD sinh viên trên bộ nhớ: Xem danh sách, Thêm mới, Sửa thông tin, Xóa, Xem chi tiết, Tìm kiếm.
- Validation tùy chỉnh chống trùng lặp mã sinh viên.

---

## 2. Công nghệ sử dụng

- **JDK 21 LTS** + **Apache Maven 3.9.x**
- **Spring Boot 3.3.2**
- **Spring MVC**
- **Thymeleaf Template Engine**
- **Spring Boot Starter Validation (Hibernate Validator)**
- **Spring Boot DevTools**

---

## 3. Cấu trúc thư mục

```text
Chuong12-SpringMVCStudent/
├── pom.xml
├── BaoCao_Lab12.md
└── src/main/
    ├── java/vn/edu/eaut/lab12/
    │   ├── Lab12Application.java      # Điểm khởi chạy Spring Boot
    │   ├── controller/
    │   │   ├── HomeController.java    # Redirect / sang /students
    │   │   └── StudentController.java # CRUD sinh viên + Validation
    │   ├── model/
    │   │   └── Student.java           # Model sinh viên kèm Validation annotations
    │   └── service/
    │       └── StudentService.java    # In-memory List sinh viên
    └── resources/
        ├── application.properties
        ├── static/css/style.css
        └── templates/students/
            ├── list.html              # Danh sách & Tìm kiếm
            ├── form.html              # Form Thêm / Sửa + Báo lỗi validation
            └── detail.html            # Xem chi tiết sinh viên
```

---

## 4. Hướng dẫn chạy ứng dụng

```bash
# Di chuyển vào thư mục dự án
cd Chuong12-SpringMVCStudent

# Khởi chạy ứng dụng bằng lệnh Maven
mvn spring-boot:run

# Truy cập trình duyệt:
# http://localhost:8080/students
```
