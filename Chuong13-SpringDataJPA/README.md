# Chương 13: Kết nối cơ sở dữ liệu với Spring Data JPA (Lab 13)

> **Học phần:** Công nghệ Java – IT3242  
> **Sinh viên:** Nguyễn Văn Hùng | **MSV:** 20230752 | **Lớp:** DCCNTT 14.2  
> **Trường:** Đại học Công nghệ Đông Á (EAUT)

---

## 🎯 Mục tiêu
- Nắm vững kiến trúc 4 tầng chuẩn Spring Boot: Controller -> Service -> Repository -> Database.
- Tích hợp và sử dụng **Spring Data JPA** cùng **Hibernate ORM** để tự động hóa toàn bộ thao tác CRUD.
- Định nghĩa các JPA Entity (`Student`, `Course`) với annotation chuẩn Jakarta Persistence.
- Khai thác `JpaRepository` và các **Derived Query Methods** (`findByFullNameContainingIgnoreCase`, `existsByStudentCode`).
- Cấu hình cơ sở dữ liệu **H2 In-Memory** kết hợp kiểm tra bằng **H2 Web Console**.
- Mở rộng và chuyển đổi linh hoạt sang **MySQL Database** (`lab13_eaut`).

---

## 🛠️ Công nghệ & Thư viện
- **JDK:** 21 LTS
- **Spring Boot:** 3.3.2
- **Spring Data JPA & Hibernate ORM 6.5**
- **H2 Database (In-Memory)**
- **MySQL Connector/J 8.3**
- **Thymeleaf Template Engine**
- **Jakarta Bean Validation (Hibernate Validator)**
- **Maven:** 3.9.x

---

## 🚀 Hướng dẫn chạy ứng dụng

### 1. Chạy với H2 In-Memory Database (Mặc định)
```bash
mvn spring-boot:run
```
- **Trang chủ:** `http://localhost:8080/`
- **Quản lý Sinh viên:** `http://localhost:8080/students`
- **Quản lý Khóa học:** `http://localhost:8080/courses`
- **H2 Web Console:** `http://localhost:8080/h2-console`  
  *(JDBC URL: `jdbc:h2:mem:eautdb`, User: `sa`, Password: để trống)*

### 2. Chạy với MySQL Database
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```
*(Yêu cầu đã khởi tạo database `CREATE DATABASE lab13_eaut;` trên MySQL Server cổng 3306).*

---

## 📋 Danh sách 10 bài tập hoàn thành
1. **Bài 1:** Cấu hình dependency JPA, H2 và MySQL trong `pom.xml`, cấu hình `application.properties`.
2. **Bài 2:** Tạo entity `Student` ánh xạ bảng `students` với `@Entity`, `@Id`, `@GeneratedValue`, `@Column`.
3. **Bài 3:** Tạo `StudentRepository` kế thừa `JpaRepository<Student, Long>` với các phương thức tìm kiếm phát sinh.
4. **Bài 4:** Tạo tầng nghiệp vụ `StudentService` tiêm `StudentRepository`.
5. **Bài 5:** Xây dựng `StudentController` CRUD hoàn chỉnh với cơ sở dữ liệu.
6. **Bài 6:** Chức năng sửa sinh viên theo ID (`GET /students/edit/{id}`).
7. **Bài 7:** Chức năng tìm kiếm sinh viên theo họ tên/mã/lớp (`GET /students?keyword=...`).
8. **Bài 8:** Tạo entity `Course` gồm mã môn, tên môn học, số tín chỉ.
9. **Bài 9:** Xây dựng toàn diện CRUD cho Course (`CourseRepository`, `CourseService`, `CourseController`).
10. **Bài 10:** Chuyển đổi cấu hình sang MySQL (`application-mysql.properties`) và thực nghiệm bảng dữ liệu.
