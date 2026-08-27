# BÁO CÁO BÀI THỰC HÀNH LAB 11
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Khởi tạo ứng dụng Spring Boot và giao diện Thymeleaf

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên:** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Framework & Libraries:** Spring Boot 3.3.2, Spring MVC, Thymeleaf Template Engine, Spring Boot DevTools, Maven 3.9.x, JDK 21 LTS

---

## 1. MỤC TIÊU BÀI LAB

1. Khởi tạo dự án Spring Boot theo đúng cấu trúc Maven chuẩn (`spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-boot-devtools`).
2. Nắm vững cơ chế hoạt động của Spring MVC: Luồng xử lý request từ Controller qua Service/Model và trả về View.
3. Truyền dữ liệu động từ `Controller` sang `View` thông qua đối tượng `org.springframework.ui.Model`.
4. Thành thạo cú pháp cơ bản của template engine **Thymeleaf**: `th:text`, `th:href`, `th:each`, `th:src`.
5. Xây dựng cấu trúc điều hướng đa trang (Navbar) hoàn chỉnh với 5 chức năng:
   - **Trang chủ (`/`):** Hero section, thống kê tổng quan, thông tin bài lab.
   - **Sinh viên (`/students`):** Model `Student`, hiển thị bảng danh sách sinh viên bằng `th:each`.
   - **Khóa học (`/courses`):** Model `Course`, hiển thị bảng 5 học phần mẫu kèm số tín chỉ.
   - **Giới thiệu (`/about`):** Giới thiệu học phần Công nghệ Java và nội dung Chương 4.
   - **Liên hệ (`/contact`):** Thông tin liên hệ Khoa CNTT, Bộ môn CNPM và thông tin sinh viên thực hiện.
6. Thiết kế giao diện hiện đại bằng CSS độc lập trong thư mục `static/css/style.css`.

---

## 2. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

| Công cụ / Thư viện | Phiên bản | Mục đích sử dụng |
| :--- | :--- | :--- |
| **JDK** | 21 LTS | Biên dịch và chạy ứng dụng Spring Boot |
| **Apache Maven** | 3.9.x | Quản lý dependency, build và chạy project |
| **Spring Boot Starter Parent** | 3.3.2 | Quản trị phiên bản tự động của Spring ecosystem |
| **Spring Boot Starter Web** | 3.3.2 | Spring MVC, nhúng sẵn Apache Tomcat Web Server |
| **Spring Boot Starter Thymeleaf** | 3.3.2 | Template Engine render giao diện HTML động phía Server |
| **Spring Boot DevTools** | 3.3.2 | Hỗ trợ hot-reload khi chỉnh sửa mã nguồn / template |

---

## 3. CẤU TRÚC DỰ ÁN

```text
lab11-springboot-thymeleaf/
├── pom.xml
├── BaoCao_Lab11.md
└── src/main/
    ├── java/vn/edu/eaut/lab11/
    │   ├── Lab11Application.java          # Lớp khởi chạy SpringApplication.run()
    │   ├── controller/
    │   │   ├── HomeController.java        # Xử lý URL: /, /about, /contact
    │   │   ├── StudentController.java     # Xử lý URL: /students
    │   │   └── CourseController.java      # Xử lý URL: /courses
    │   └── model/
    │       ├── Student.java               # Model Sinh viên (mã, họ tên, email, lớp)
    │       └── Course.java                # Model Khóa học (mã môn, tên môn, số TC)
    └── resources/
        ├── application.properties         # Cấu hình cổng 8080 & tắt cache Thymeleaf
        ├── static/
        │   └── css/
        │       └── style.css              # Hệ thống style CSS giao diện hiện đại
        └── templates/
            ├── index.html                 # Giao diện Trang chủ (Bài 2, 7)
            ├── students.html              # Giao diện Danh sách Sinh viên (Bài 4)
            ├── courses.html               # Giao diện Danh sách Khóa học (Bài 8, 9)
            ├── about.html                 # Giao diện Giới thiệu (Bài 5)
            └── contact.html               # Giao diện Liên hệ (Bài 6)
```

---

## 4. TỔNG HỢP 10 BÀI TẬP THỰC HIỆN

### Bài 1. Tạo project Spring Boot
- Khởi tạo project với `spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `spring-boot-devtools`.
- Lớp `Lab11Application` gắn chú thích `@SpringBootApplication`. Ứng dụng chạy trên cổng `8080`.

### Bài 2. Tạo trang chủ (`/`)
- `HomeController.java`: Phương thức `index(Model model)` gắn `@GetMapping("/")`.
- Truyền `title` và `message` qua `Model.addAttribute()`.
- `index.html`: Sử dụng `th:text="${title}"` và `th:text="${message}"` để render tiêu đề và thông điệp chào mừng.

### Bài 3. Tạo lớp `Student`
- Lớp `Student.java` trong package `vn.edu.eaut.lab11.model` gồm 4 trường: `studentCode`, `fullName`, `email`, `className` kèm đầy đủ Constructor, Getter, Setter.

### Bài 4. Hiển thị danh sách sinh viên (`/students`)
- `StudentController.java`: Phương thức `listStudents(Model model)` tạo danh sách `List<Student>` mẫu và truyền sang view với key `"students"`.
- `students.html`: Dùng cú pháp `th:each="s, iter : ${students}"` để duyệt và hiển thị từng dòng trong bảng.

### Bài 5. Tạo trang giới thiệu (`/about`)
- `HomeController.java`: Phương thức `about(Model model)` truyền `course = "Công nghệ Java"` và `chapter = "Chương 4 - Spring Framework"`.
- `about.html`: Hiển thị nội dung chi tiết 4 phần của Chương 4.

### Bài 6. Tạo trang liên hệ (`/contact`)
- Phương thức `contact(Model model)` truyền thông tin: Bộ môn Công nghệ Phần mềm, Khoa CNTT, Trường ĐH Công nghệ Đông Á, địa chỉ, hotline, email.

### Bài 7. Menu điều hướng dùng chung
- Thanh Navbar cố định trên đầu trang với liên kết đến cả 5 trang: Trang chủ (`/`), Sinh viên (`/students`), Khóa học (`/courses`), Giới thiệu (`/about`), Liên hệ (`/contact`).

### Bài 8 & 9. Model Course & Trang danh sách khóa học (`/courses`)
- `Course.java`: Gồm `courseCode`, `courseName`, `credits`.
- `CourseController.java`: Trả về danh sách 5 học phần: Công nghệ Java (3 TC), Lập trình Web (3 TC), Nhập môn lập trình (4 TC), Cơ sở dữ liệu (3 TC), Phát triển ứng dụng di động (3 TC).
- `courses.html`: Render bảng khóa học bằng Thymeleaf.

### Bài 10. Định dạng giao diện CSS riêng
- Tệp `static/css/style.css`: Thiết kế giao diện hiện đại với bảng màu xanh dương, Hero banner gradient, thẻ thông tin dạng Card, bảng dữ liệu sọc xen kẽ, Badge phân loại và hỗ trợ Responsive.

---

## 5. HÌNH ẢNH MINH CHỨNG KẾT QUẢ CHẠY ỨNG DỤNG

| STT | Chức năng / URL | Mô tả kết quả |
| :---: | :--- | :--- |
| **1** | Trang chủ: `http://localhost:8080/` | Hero banner chào mừng, thống kê 4 thẻ stat, bảng tóm tắt học phần |
| **2** | Danh sách SV: `http://localhost:8080/students` | Bảng 6 sinh viên mẫu render động bằng Thymeleaf `th:each` |
| **3** | Danh sách Môn học: `http://localhost:8080/courses` | Bảng 5 học phần kèm mã môn và số tín chỉ |
| **4** | Giới thiệu: `http://localhost:8080/about` | Chi tiết nội dung 4 chuyên đề Chương 4 Spring Framework |
| **5** | Liên hệ: `http://localhost:8080/contact` | Thông tin Khoa CNTT, Bộ môn CNPM và thông tin sinh viên thực hiện |

---

## 6. KẾT LUẬN & TỰ ĐÁNH GIÁ

- **Kết quả:** Hoàn thành 100% tất cả 10 bài tập (5 bài có gợi ý code + 5 bài mở rộng tự thực hiện).
- **Chất lượng:** Mã nguồn chuẩn quy ước Spring Boot, Controller/Model/View phân tách rõ ràng, giao diện thẩm mỹ cao.
- **Tự đánh giá điểm:** 10.0 / 10.0
