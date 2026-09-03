# BÁO CÁO BÀI THỰC HÀNH LAB 12
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Phát triển ứng dụng web với Spring MVC, Thymeleaf Form và Validation

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên:** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Framework & Libraries:** Spring Boot 3.3.2, Spring MVC, Thymeleaf Template Engine, Hibernate Validator (Bean Validation), Maven 3.9.x, JDK 21 LTS

---

## 1. MỤC TIÊU BÀI LAB

1. **Hiểu rõ luồng xử lý request trong Spring MVC:** Mô hình phân tách rõ ràng giữa Controller (`StudentController`), Model (`Student`), Service (`StudentService`) và View (Thymeleaf Templates).
2. **Xây dựng Controller xử lý phương thức GET và POST:** Tiếp nhận HTTP GET để hiển thị trang/danh sách/chi tiết và HTTP POST để xử lý gửi dữ liệu từ form.
3. **Thao tác với Thymeleaf Form:** Sử dụng các thuộc tính `th:object`, `th:field`, `th:action`, `th:errors` và thẻ `th:if`.
4. **Binding dữ liệu hai chiều bằng `@ModelAttribute`:** Tự động ánh xạ các input từ HTTP Request vào các thuộc tính của đối tượng `Student`.
5. **Kiểm tra dữ liệu đầu vào (Validation):**
   - Sử dụng các Annotation chuẩn của Jakarta Bean Validation: `@NotBlank`, `@Size`, `@Email`.
   - Kết hợp `@Valid` và `BindingResult` trong Controller để phát hiện lỗi nhập liệu và hiển thị trực quan trên giao diện mà không làm mất dữ liệu người dùng vừa nhập.
6. **Xây dựng hoàn chỉnh hệ thống CRUD sinh viên:** Thực hiện đầy đủ 5 thao tác: Xem danh sách, Thêm mới, Sửa thông tin, Xóa bản ghi, Xem chi tiết và Tìm kiếm theo từ khóa trên danh sách lưu trong bộ nhớ (In-Memory List).
7. **Validation nghiệp vụ nâng cao:** Kiểm tra và ngăn chặn việc nhập trùng mã sinh viên (`existsByStudentCode`).

---

## 2. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

| Công cụ / Thư viện | Phiên bản | Mục đích sử dụng |
| :--- | :--- | :--- |
| **JDK (Java Development Kit)** | 21 LTS | Môi trường biên dịch và thực thi ứng dụng |
| **Apache Maven** | 3.9.x | Quản lý vòng đời build và các dependency |
| **Spring Boot Starter Web** | 3.3.2 | Spring MVC, nhúng sẵn Web Server Apache Tomcat |
| **Spring Boot Starter Thymeleaf** | 3.3.2 | Template Engine render giao diện HTML5 động |
| **Spring Boot Starter Validation** | 3.3.2 | Jakarta Bean Validation & Hibernate Validator |
| **Spring Boot DevTools** | 3.3.2 | Hỗ trợ tính năng hot-reload và live refresh |

---

## 3. CẤU TRÚC DỰ ÁN

```text
lab12-spring-mvc-student/
├── pom.xml
├── BaoCao_Lab12.md
└── src/main/
    ├── java/vn/edu/eaut/lab12/
    │   ├── Lab12Application.java              # Lớp khởi chạy Spring Boot
    │   ├── controller/
    │   │   ├── HomeController.java            # Redirect / sang /students
    │   │   └── StudentController.java         # Controller xử lý toàn bộ CRUD sinh viên
    │   ├── model/
    │   │   └── Student.java                   # Entity Model chứa các annotation Validation
    │   └── service/
    │       └── StudentService.java            # Business Service quản lý List sinh viên trong bộ nhớ
    └── resources/
        ├── application.properties             # Cấu hình cổng 8080 & template cache
        ├── static/
        │   └── css/
        │       └── style.css                  # CSS giao diện hiện đại, responsive
        └── templates/
            └── students/
                ├── list.html                  # Giao diện Danh sách sinh viên + Tìm kiếm
                ├── form.html                  # Giao diện Form Thêm mới / Chỉnh sửa + Báo lỗi
                └── detail.html                # Giao diện Xem chi tiết sinh viên
```

---

## 4. CHI TIẾT 10 BÀI TẬP THỰC HIỆN

### Bài 1: Tạo model `Student`
- Lớp `Student.java` định nghĩa các trường: `id` (Long), `studentCode`, `fullName`, `email`, `className`, `phoneNumber`, `address`.
- Tích hợp các Annotation kiểm tra ràng buộc:
  - `@NotBlank(message = "Mã sinh viên không được để trống")`
  - `@Size(min = 5, max = 20, message = "Mã sinh viên phải có từ 5 đến 20 ký tự")`
  - `@NotBlank(message = "Họ tên không được để trống")`
  - `@Email(message = "Email không đúng định dạng hợp lệ")`
  - `@NotBlank(message = "Lớp không được để trống")`

### Bài 2: Tạo service giả lập dữ liệu (`StudentService`)
- Lớp `StudentService` quản lý `List<Student>` trong bộ nhớ với biến tự tăng `nextId`.
- Nạp sẵn 6 bản ghi sinh viên mẫu ban đầu.
- Cung cấp các phương thức nghiệp vụ: `findAll()`, `findById()`, `save()`, `deleteById()`, `search()`, `existsByStudentCode()`.

### Bài 3: Tạo Controller danh sách sinh viên
- `StudentController` xử lý `@GetMapping("/students")`: Gọi `studentService.findAll()`, đưa danh sách vào `Model` và trả về view `students/list`.

### Bài 4: Tạo form thêm sinh viên
- `@GetMapping("/students/create")`: Truyền đối tượng rỗng `new Student()` sang view `students/form`.
- `@PostMapping("/students/save")`: Nhận `@ModelAttribute Student student`, gọi `studentService.save(student)` và chuyển hướng về `/students`.

### Bài 5: Thêm validation cho form
- Sử dụng `@Valid @ModelAttribute("student") Student student, BindingResult result`.
- Kiểm tra `if (result.hasErrors())` $\rightarrow$ giữ nguyên dữ liệu và hiển thị các thông báo lỗi màu đỏ cạnh từng trường nhập liệu tương ứng trên `form.html` thông qua `th:errors="*{studentCode}"`, `th:errors="*{fullName}"`,...

### Bài 6: Xem chi tiết sinh viên theo ID
- `@GetMapping("/students/detail/{id}")`: Tìm sinh viên theo id qua `studentService.findById(id)` và hiển thị trên giao diện `students/detail.html`.

### Bài 7: Sửa thông tin sinh viên
- `@GetMapping("/students/edit/{id}")`: Lấy dữ liệu sinh viên cần sửa nạp vào form `students/form.html` với trường ẩn `id` (`th:field="*{id}"`). Khi submit POST `/students/save`, hệ thống tự động cập nhật bản ghi cũ.

### Bài 8: Xóa sinh viên khỏi danh sách
- `@GetMapping("/students/delete/{id}")`: Gọi `studentService.deleteById(id)`, thêm thông báo flash qua `RedirectAttributes` và chuyển hướng về `/students`.

### Bài 9: Tìm kiếm sinh viên theo từ khóa
- Phương thức `search(String keyword)` trong Service lọc theo mã SV, họ tên hoặc lớp học không phân biệt hoa thường.

### Bài 10: Validation tùy chỉnh chống trùng mã sinh viên
- Trong `StudentController.save()`: Kiểm tra `studentService.existsByStudentCode(code, id)`. Nếu mã đã tồn tại với sinh viên khác, thêm lỗi vào `BindingResult`:
  ```java
  result.rejectValue("studentCode", "error.student", "Mã sinh viên đã tồn tại trên hệ thống!");
  ```

---

## 5. MÔ TẢ LUỒNG REQUEST TRONG SPRING MVC

```text
Trình duyệt (Browser)
      │
      ▼  (HTTP GET /students/create)
DispatcherServlet (Front Controller)
      │
      ▼
StudentController.createForm(Model)
      │
      ▼  (Trả về "students/form" + new Student)
Thymeleaf ViewResolver ──► Render HTML ──► Trình duyệt
      │
      ▼  (HTTP POST /students/save + Form Data)
DispatcherServlet
      │
      ▼  (DataBinder + Hibernate Validator)
StudentController.save(@Valid @ModelAttribute Student, BindingResult)
      ├──► Nếu có lỗi: Trả lại "students/form" + BindingResult errors
      └──► Nếu hợp lệ: Gọi StudentService.save() ──► redirect:/students
```

---

## 6. HÌNH ẢNH MINH CHỨNG KẾT QUẢ CHẠY ỨNG DỤNG

| STT | Hình ảnh minh chứng | Mô tả chi tiết |
| :---: | :--- | :--- |
| **1** | Danh sách sinh viên ban đầu | Bảng 6 sinh viên mẫu, phân cột rõ ràng, nút thao tác Xem/Sửa/Xóa |
| **2** | Form thêm mới sinh viên | Form nhập liệu giao diện hiện đại với các trường có dấu hoa thị bắt buộc |
| **3** | Báo lỗi Validation khi để trống | Hiển thị thông báo lỗi màu đỏ dưới từng ô nhập liệu khi submit form rỗng |
| **4** | Báo lỗi Trùng mã sinh viên | Kiểm tra nghiệp vụ: Báo lỗi khi nhập mã sinh viên đã tồn tại |
| **5** | Thêm mới thành công | Thông báo Flash Alert màu xanh và sinh viên mới xuất hiện trong bảng |
| **6** | Xem chi tiết sinh viên | Trang hồ sơ chi tiết hiển thị đầy đủ thông tin cá nhân của sinh viên |
| **7** | Form chỉnh sửa thông tin | Nạp sẵn dữ liệu cũ lên form để người dùng tiến hành sửa đổi |
| **8** | Tìm kiếm sinh viên | Lọc danh sách sinh viên theo từ khóa (họ tên, mã SV, lớp) tức thì |

---

## 7. KẾT LUẬN & TỰ ĐÁNH GIÁ

- **Kết quả:** Hoàn thành 100% tất cả 10 bài tập theo đúng chuẩn kiến trúc Spring MVC.
