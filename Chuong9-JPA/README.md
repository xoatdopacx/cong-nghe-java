# Lab 9 - Tích hợp JPA: Entity, Repository, Transaction

**Học phần:** Công nghệ Java - IT3242  
**Sinh viên:** Nguyễn Văn Hùng  
**MSSV:** 20230752  
**Lớp:** DCCNTT 14.2  

---

## 1. Giới thiệu

Lab 9 tích hợp kiến trúc **Jakarta Persistence (JPA 3.1)** và **Hibernate ORM 6.4.4** vào ứng dụng Java Web với cơ chế quản lý giao dịch (Transaction), mô hình Generic Repository Pattern (`BaseRepository<T>`), Service Layer và giao diện người dùng Servlet/JSP hiện đại.

## 2. Công nghệ sử dụng

- **JDK 21 LTS** + **Apache Maven 3.9.x**
- **Jakarta Persistence API (JPA) 3.1.0**
- **Hibernate ORM 6.4.4.Final**
- **MySQL Connector/J 9.2.0** (MySQL Database `lab09_jpa`)
- **Jakarta Servlet 6.0 & JSP 3.1 / JSTL 3.0**
- **Apache Tomcat 10.1.30** (Embedded via Cargo Maven Plugin)

## 3. Cấu trúc thư mục

```text
Chuong9-JPA/
├── pom.xml
├── BaoCao_Lab9.md
├── screenshots/
└── src/main/
    ├── java/vn/edu/eaut/lab9/
    │   ├── entity/        (SinhVien, LopHoc, MonHoc, Diem, SanPham, NguoiDung)
    │   ├── repository/    (BaseRepository, SinhVienRepository, LopHocRepository, ...)
    │   ├── service/       (SinhVienService)
    │   ├── controller/    (DashboardController, SinhVienController, LopHocController, ...)
    │   └── util/          (JPAUtil, DataSeeder, AppLifecycleListener, EncodingFilter)
    ├── resources/META-INF/
    │   └── persistence.xml
    └── webapp/
        ├── resources/css/style.css
        └── WEB-INF/
            ├── web.xml
            └── views/ (header.jsp, footer.jsp, dashboard.jsp, sinhvien/, lophoc/, monhoc/, diem/, sanpham/)
```

## 4. Các module chức năng

| Module | Entity | Mô tả chức năng |
|---|---|---|
| **Dashboard** | Thống kê đa thực thể | Tổng hợp số liệu realtime từ EntityManager, tự động seed dữ liệu ban đầu |
| **Sinh Viên** | `SinhVien.java` | CRUD, phân trang 5 bản ghi/trang, tìm kiếm theo họ tên/mã SV, quan hệ `@ManyToOne` với `LopHoc` |
| **Lớp Học** | `LopHoc.java` | CRUD, đếm số sinh viên theo quan hệ `@OneToMany` (sử dụng JPQL Fetch Join tối ưu) |
| **Môn Học** | `MonHoc.java` | CRUD danh mục môn học và số tín chỉ |
| **Điểm** | `Diem.java` | Quản lý bảng điểm học phần liên kết giữa Sinh Viên và Môn Học |
| **Sản Phẩm** | `SanPham.java` | CRUD sản phẩm, đơn giá định dạng VNĐ, số lượng tồn kho |

## 5. Hướng dẫn chạy ứng dụng

```bash
# Di chuyển vào thư mục Chuong9-JPA
cd Chuong9-JPA

# Build và khởi chạy trên Tomcat 10
mvn clean package cargo:run

# Truy cập ứng dụng tại:
# http://localhost:8080/lab09-jpa-repository/
```

## 6. Hình ảnh minh họa

| Màn hình | Hình ảnh minh họa |
|---|---|
| Dashboard | ![Dashboard](screenshots/01_dashboard.png) |
| Danh sách Sinh Viên | ![SinhVien List](screenshots/02_sinhvien_list.png) |
| Form Sinh Viên | ![SinhVien Form](screenshots/03_sinhvien_form.png) |
| Danh sách Lớp Học | ![LopHoc List](screenshots/05_lophoc_list.png) |
| Danh sách Môn Học | ![MonHoc List](screenshots/07_monhoc_list.png) |
| Danh sách Bảng Điểm | ![Diem List](screenshots/09_diem_list.png) |
| Danh sách Sản Phẩm | ![SanPham List](screenshots/11_sanpham_list.png) |
