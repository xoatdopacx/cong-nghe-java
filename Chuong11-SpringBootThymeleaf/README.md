# Lab 11 – Khởi tạo ứng dụng Spring Boot và giao diện Thymeleaf

**Học phần:** Công nghệ Java - IT3242  
**Sinh viên:** Nguyễn Văn Hùng  
**MSSV:** 20230752  
**Lớp:** DCCNTT 14.2  

---

## 1. Giới thiệu

Lab 11 mở đầu Chương 4 về phát triển ứng dụng web với **Spring Boot 3.3.2** và template engine **Thymeleaf**:
- Khởi tạo project Spring Boot chuẩn theo Maven (`spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-boot-devtools`).
- Cấu trúc kiến trúc Spring MVC: Controller $\rightarrow$ Model $\rightarrow$ View (Thymeleaf).
- Sử dụng cú pháp Thymeleaf: `th:text`, `th:href`, `th:each`, `th:src`.
- Xây dựng 5 trang chức năng: Trang chủ (`/`), Sinh viên (`/students`), Khóa học (`/courses`), Giới thiệu (`/about`), Liên hệ (`/contact`).
- Hệ thống thanh điều hướng (Navbar) và giao diện hiện đại định dạng bởi CSS độc lập.

---

## 2. Công nghệ sử dụng

- **JDK 21 LTS** + **Apache Maven 3.9.x**
- **Spring Boot 3.3.2**
- **Spring MVC**
- **Thymeleaf Template Engine 3.1.x**
- **Spring Boot DevTools**

---

## 3. Cấu trúc thư mục

```text
Chuong11-SpringBootThymeleaf/
├── pom.xml
├── BaoCao_Lab11.md
└── src/main/
    ├── java/vn/edu/eaut/lab11/
    │   ├── Lab11Application.java      # Điểm khởi chạy Spring Boot
    │   ├── controller/
    │   │   ├── HomeController.java    # Xử lý /, /about, /contact
    │   │   ├── StudentController.java # Xử lý /students
    │   │   └── CourseController.java  # Xử lý /courses
    │   └── model/
    │       ├── Student.java           # Model Sinh viên (mã, tên, email, lớp)
    │       └── Course.java            # Model Khóa học (mã, tên, tín chỉ)
    └── resources/
        ├── application.properties
        ├── static/css/style.css
        └── templates/                 # index.html, students.html, courses.html, about.html, contact.html
```

---

## 4. Hướng dẫn chạy ứng dụng

```bash
# Di chuyển vào thư mục dự án
cd Chuong11-SpringBootThymeleaf

# Khởi chạy ứng dụng bằng lệnh Maven
mvn spring-boot:run

# Truy cập trình duyệt:
# http://localhost:8080/
```
