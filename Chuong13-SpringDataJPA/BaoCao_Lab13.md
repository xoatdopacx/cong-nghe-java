# BÁO CÁO BÀI THỰC HÀNH LAB 13
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Kết nối cơ sở dữ liệu với Spring Data JPA, Hibernate ORM và H2/MySQL

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên:** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Công nghệ áp dụng:** Spring Boot 3.3.2, Spring Data JPA, Hibernate ORM 6.5, H2 Database (In-Memory), MySQL Connector/J, Thymeleaf, Jakarta Bean Validation, Maven 3.9.x, JDK 21 LTS

---

## 1. MỤC TIÊU BÀI LAB

1. **Hiểu và vận dụng kiến trúc phân tầng Spring Boot:** Phân định rõ ràng vai trò giữa Controller (`StudentController`, `CourseController`), Service (`StudentService`, `CourseService`), Repository (`StudentRepository`, `CourseRepository`) và Database (H2/MySQL).
2. **Khai thác sức mạnh của Spring Data JPA:**
   - Kế thừa `JpaRepository` để tự động thừa hưởng toàn bộ các phương thức CRUD chuẩn: `findAll()`, `findById()`, `save()`, `deleteById()`, `count()`.
   - Sử dụng **Derived Query Methods** (truy vấn phát sinh theo tên phương thức) như `findByFullNameContainingIgnoreCase()`, `existsByStudentCode()`.
3. **Ánh xạ thực thể ORM với Jakarta Persistence:**
   - Định nghĩa Entity `Student` và `Course` với các annotation `@Entity`, `@Table`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`, `@Column`.
4. **Tích hợp cơ sở dữ liệu H2 In-Memory:**
   - Cấu hình URL `jdbc:h2:mem:eautdb`.
   - Khởi tạo bảng tự động qua Hibernate DDL (`ddl-auto=update`).
   - Nạp dữ liệu mẫu ban đầu qua file `data.sql`.
   - Khai thác H2 Web Console tại `/h2-console` để giám sát dữ liệu trực tiếp.
5. **Đảm bảo toàn vẹn dữ liệu và kiểm tra ràng buộc:**
   - Kết hợp Jakarta Bean Validation (`@NotBlank`, `@Size`, `@Email`, `@Min`, `@Max`) và kiểm tra logic chống trùng mã sinh viên, mã môn học.
6. **Mở rộng sang hệ quản trị CSDL MySQL:**
   - Cấu hình chuyển đổi datasource sang MySQL qua file `application-mysql.properties` và database `lab13_eaut`.

---

## 2. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

| Công cụ / Thư viện | Phiên bản | Mục đích sử dụng |
| :--- | :--- | :--- |
| **JDK (Java Development Kit)** | 21 LTS | Môi trường biên dịch và thực thi ứng dụng |
| **Apache Maven** | 3.9.x | Quản lý vòng đời build và các dependency |
| **Spring Boot Starter Web** | 3.3.2 | Spring MVC, nhúng sẵn Web Server Apache Tomcat 10.1 |
| **Spring Boot Starter Data JPA** | 3.3.2 | Spring Data JPA và Hibernate ORM Core |
| **H2 Database** | 2.2.224 | Cơ sở dữ liệu In-Memory, hỗ trợ H2 Web Console |
| **MySQL Connector/J** | 8.3.0 | Trình điều khiển kết nối MySQL Database |
| **Spring Boot Starter Thymeleaf** | 3.3.2 | Template Engine render giao diện HTML5 động |
| **Spring Boot Starter Validation** | 3.3.2 | Jakarta Bean Validation & Hibernate Validator |

---

## 3. CẤU TRÚC DỰ ÁN

```text
lab13-spring-data-jpa/
├── pom.xml                                    # Khai báo dependency (JPA, H2, MySQL, Validation)
├── BaoCao_Lab13.md                            # Báo cáo Markdown chi tiết
├── BaoCao_Lab13_NguyenVanHung_20230752.docx   # Báo cáo Word chuẩn khoa học EAUT
└── src/main/
    ├── java/vn/edu/eaut/lab13/
    │   ├── Lab13Application.java              # Lớp khởi chạy Spring Boot
    │   ├── controller/
    │   │   ├── HomeController.java            # Trang chủ hiển thị Dashboard thống kê
    │   │   ├── StudentController.java         # Controller CRUD Sinh viên kết nối JPA
    │   │   └── CourseController.java          # Controller CRUD Khóa học (Bài 9)
    │   ├── entity/
    │   │   ├── Student.java                   # JPA Entity ánh xạ bảng students
    │   │   └── Course.java                    # JPA Entity ánh xạ bảng courses (Bài 8)
    │   ├── repository/
    │   │   ├── StudentRepository.java         # Interface kế thừa JpaRepository<Student, Long>
    │   │   └── CourseRepository.java          # Interface kế thừa JpaRepository<Course, Long>
    │   └── service/
    │       ├── StudentService.java            # Service xử lý nghiệp vụ sinh viên
    │       └── CourseService.java             # Service xử lý nghiệp vụ khóa học
    └── resources/
        ├── application.properties             # Cấu hình H2, JPA, show-sql, h2-console
        ├── application-mysql.properties       # Cấu hình chuyển đổi sang MySQL (Bài 10)
        ├── data.sql                           # Khởi tạo dữ liệu mẫu tự động
        ├── static/css/style.css               # Giao diện CSS hiện đại, responsive
        └── templates/
            ├── index.html                     # Giao diện Dashboard trang chủ
            ├── students/
            │   ├── list.html                  # Danh sách sinh viên & Tìm kiếm
            │   └── form.html                  # Form thêm mới / Sửa sinh viên + Báo lỗi
            └── courses/
                ├── list.html                  # Danh sách khóa học (Bài 8, 9)
                └── form.html                  # Form thêm mới / Sửa khóa học (Bài 9)
```

---

## 4. CHI TIẾT 10 BÀI TẬP THỰC HIỆN

### Bài 1: Cấu hình Spring Data JPA và Cơ sở dữ liệu H2
- Bổ sung các dependency vào `pom.xml`: `spring-boot-starter-data-jpa`, `com.h2database:h2`, `mysql-connector-j`.
- Thiết lập thông số cấu hình trong `application.properties`:
  ```properties
  spring.datasource.url=jdbc:h2:mem:eautdb
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.format_sql=true
  spring.h2.console.enabled=true
  spring.h2.console.path=/h2-console
  ```

### Bài 2: Tạo JPA Entity `Student`
- Khởi tạo lớp `vn.edu.eaut.lab13.entity.Student` ánh xạ bảng `students`.
- Áp dụng các JPA Annotation: `@Entity`, `@Table(name = "students")`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
- Tích hợp ràng buộc `@Column(name = "student_code", nullable = false, unique = true)` và Jakarta Validation (`@NotBlank`, `@Size`, `@Email`).

### Bài 3: Tạo `StudentRepository` kế thừa `JpaRepository`
- Khởi tạo interface `StudentRepository` kế thừa `JpaRepository<Student, Long>`.
- Định nghĩa các Derived Query Methods:
  - `List<Student> findByFullNameContainingIgnoreCase(String keyword)`: Tìm kiếm sinh viên theo họ tên không phân biệt hoa thường.
  - `List<Student> findByStudentCodeContainingIgnoreCase(String code)`: Tìm theo mã SV.
  - `boolean existsByStudentCode(String studentCode)`: Kiểm tra trùng mã sinh viên khi thêm mới.
  - `boolean existsByStudentCodeAndIdNot(String studentCode, Long id)`: Kiểm tra trùng mã sinh viên nhưng loại trừ chính nó khi cập nhật.

### Bài 4: Xây dựng tầng Service nghiệp vụ (`StudentService`)
- Lớp `@Service StudentService` tiêm phụ thuộc `StudentRepository` qua Constructor Injection.
- Cung cấp các hàm: `findAll()`, `findById(Long id)`, `save(Student student)`, `deleteById(Long id)`, `search(String keyword)`, `existsByStudentCode()`.

### Bài 5: Xây dựng Controller CRUD với CSDL
- `StudentController` tiếp nhận các HTTP Request:
  - `GET /students`: Lấy toàn bộ sinh viên từ CSDL và chuyển tới view `students/list`.
  - `GET /students/create`: Hiển thị form tạo mới `students/form`.
  - `POST /students/save`: Nhận dữ liệu `@Valid`, kiểm tra lỗi `BindingResult`, lưu xuống DB thông qua `studentService.save(student)`.
  - `GET /students/delete/{id}`: Gọi `studentService.deleteById(id)` và hiển thị thông báo flash.

### Bài 6: Chức năng sửa sinh viên theo ID
- Mở rộng `StudentController` với endpoint `GET /students/edit/{id}`.
- Tìm sinh viên từ CSDL qua `studentService.findById(id)` và đưa vào model với cờ `isEdit = true`.
- Khi người dùng bấm lưu, ID sinh viên được giữ lại qua `<input type="hidden" th:field="*{id}">` để Hibernate thực hiện lệnh SQL `UPDATE` thay vì `INSERT`.

### Bài 7: Chức năng tìm kiếm sinh viên theo họ tên
- Bổ sung ô tìm kiếm trên `students/list.html` và tham số `keyword` tại `GET /students`.
- Controller gọi `studentService.search(keyword)`, tầng Service sử dụng `studentRepository.findByFullNameContainingIgnoreCase(keyword)`.
- Hiển thị danh sách kết quả phù hợp cùng nút "Xóa lọc" để quay về toàn bộ danh sách.

### Bài 8: Thiết kế Entity `Course`
- Tạo entity `vn.edu.eaut.lab13.entity.Course` ánh xạ bảng `courses`.
- Gồm các thuộc tính: `id` (Long, Khóa chính tự tăng), `courseCode` (Mã môn, unique), `courseName` (Tên môn học), `credits` (Số tín chỉ, kiểm tra `@Min(1)`, `@Max(10)`).

### Bài 9: Xây dựng CRUD cho `Course`
- Xây dựng đầy đủ:
  - `CourseRepository` kế thừa `JpaRepository<Course, Long>`.
  - `CourseService` xử lý nghiệp vụ môn học.
  - `CourseController` mapping `/courses`, `/courses/create`, `/courses/edit/{id}`, `/courses/save`, `/courses/delete/{id}`.
  - Templates `courses/list.html` và `courses/form.html`.

### Bài 10: Chuyển cấu hình từ H2 sang MySQL
- Tạo file cấu hình `application-mysql.properties` với JDBC URL: `jdbc:mysql://localhost:3306/lab13_eaut`.
- Tạo database `lab13_eaut` và bảng `students`, `courses` trong MySQL Server.
- Kiểm tra truy vấn trực tiếp bằng MySQL Client hiển thị đầy đủ các bản ghi sinh viên và môn học.

---

## 5. KẾT QUẢ VÀ ĐÁNH GIÁ

1. **Hiệu năng & Sự tiện lợi của JPA:** Việc chuyển từ List In-Memory (Lab 12) sang Spring Data JPA (Lab 13) giúp dữ liệu được lưu trữ bền vững (Persistence). Khi ứng dụng khởi động lại, dữ liệu được nạp tự động qua file `data.sql` và không bị mất đi.
2. **Khả năng tự động hóa cao:** Nhờ Spring Data JPA, lập trình viên không cần viết các câu lệnh SQL INSERT, UPDATE, DELETE, SELECT thủ công, giảm thiểu nguy cơ lỗi cú pháp và SQL Injection.
3. **Tính độc lập với CSDL:** Chuyển đổi giữa H2 Database (kiểm thử nhanh) và MySQL (thực tế) chỉ bằng cấu hình DataSource trong file properties mà không cần sửa đổi bất kỳ dòng mã nguồn Java nào.
