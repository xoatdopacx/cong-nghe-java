# Lab 10 – Thêm Login, Role, Bảo Vệ URL và Hoàn Thiện Ứng Dụng

**Học phần:** Công nghệ Java - IT3242  
**Sinh viên:** Nguyễn Văn Hùng  
**MSSV:** 20230752  
**Lớp:** DCCNTT 14.2  

---

## 1. Giới thiệu

Lab 10 là bài thực hành tổng kết chuỗi Lab 6–10 trong học phần Công nghệ Java (IT3242). Hệ thống tích hợp toàn diện kiến trúc Jakarta EE đa tầng:
- **Xác thực người dùng (Authentication):** Đăng nhập / Đăng xuất với CSDL MySQL qua JPA.
- **Quản lý phiên (Session Management):** Lưu trữ `currentUser` trong `HttpSession`, chống cache khi logout.
- **Phân quyền Role-Based Access Control (RBAC):** Enum `Role` (ADMIN, STAFF, USER).
- **Chuỗi Filter bảo mật:** `AuthenticationFilter` bảo vệ session + `AuthorizationFilter` phân quyền URL.
- **Giao diện Menu động theo Role:** Sidebar hiển thị tương ứng quyền hạn người dùng.
- **Xử lý trang lỗi chuẩn hóa:** `403.jsp` (Forbidden), `404.jsp` (Not Found), `500.jsp` (Server Error).
- **5 module nghiệp vụ hoàn chỉnh:** Sinh Viên, Lớp Học, Môn Học, Điểm, Sản Phẩm.

---

## 2. Công nghệ sử dụng

- **JDK 21 LTS** + **Apache Maven 3.9.x**
- **Jakarta Persistence API (JPA) 3.1.0**
- **Hibernate ORM 6.4.4.Final**
- **MySQL Server & Driver 8.3.0** (Database: `lab10_secured`)
- **Jakarta Servlet 6.0 & JSP 3.1 / JSTL 3.0**
- **Apache Tomcat 10.1.x** (Embedded qua Cargo Maven Plugin)

---

## 3. Cấu trúc thư mục

```text
Chuong10-SecuredApp/
├── pom.xml
├── BaoCao_Lab10.md
├── database/
│   └── lab10_secured.sql
└── src/main/
    ├── java/vn/edu/eaut/lab10/
    │   ├── model/         (Role enum, User, SinhVien, LopHoc, MonHoc, Diem, SanPham)
    │   ├── repository/    (BaseRepository, UserRepository, SinhVienRepository, ...)
    │   ├── service/       (AuthService, SinhVienService)
    │   ├── filter/        (EncodingFilter, AuthenticationFilter, AuthorizationFilter)
    │   ├── listener/      (AppListener - khởi tạo JPA & seed data)
    │   ├── config/        (JPAUtil)
    │   └── controller/    (AuthController, DashboardController, ProfileController,
    │                       UserManagementController, SinhVienController, ...)
    ├── resources/META-INF/
    │   └── persistence.xml
    └── webapp/
        ├── login.jsp
        ├── error/         (403.jsp, 404.jsp, 500.jsp)
        ├── resources/css/style.css
        └── WEB-INF/
            ├── web.xml
            └── views/     (header.jsp, footer.jsp, dashboard.jsp, admin/, staff/, user/)
```

---

## 4. Tài khoản kiểm thử

| Email | Mật khẩu | Vai trò | Quyền hạn truy cập |
|---|---|---|---|
| `admin@eaut.edu.vn` | `admin123` | **ADMIN** | Toàn quyền: Thấy toàn bộ menu (`/admin/*`, `/staff/*`, `/user/*`, `/dashboard`) |
| `staff@eaut.edu.vn` | `staff123` | **STAFF** | Thấy menu Điểm & Sản phẩm (`/staff/*`), Hồ sơ. Vào `/admin/*` $\rightarrow$ lỗi 403 |
| `user@eaut.edu.vn` | `user123` | **USER** | Chỉ thấy menu Dashboard và Hồ sơ cá nhân. Vào `/admin/*`, `/staff/*` $\rightarrow$ lỗi 403 |

---

## 5. Hướng dẫn chạy ứng dụng

```bash
# 1. Tạo database và nạp dữ liệu mẫu
mysql -u root -p < database/lab10_secured.sql

# 2. Build và khởi chạy trên Tomcat
mvn clean package cargo:run

# 3. Truy cập trình duyệt:
# http://localhost:8080/lab10-secured-app/login.jsp
```
