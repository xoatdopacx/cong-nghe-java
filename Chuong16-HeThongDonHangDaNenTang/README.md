# Chương 16 – Hệ Thống Xử Lý Đơn Hàng Đa Nền Tảng (Lab 16)

> **Học phần:** Công nghệ Java (IT3242)  
> **Sinh viên:** Nguyễn Văn Hùng &nbsp;|&nbsp; **MSSV:** 20230752 &nbsp;|&nbsp; **Lớp:** DCCNTT 14.2  
> **Trường:** Đại học Công nghệ Đông Á (EAUT)

---

## 🎯 Mục tiêu & Kiến trúc hệ thống

Bài thực hành tổng hợp tích hợp **3 nền tảng Java** cùng hoạt động trên một cơ sở dữ liệu **MySQL Server** tập trung (`java_integrated_lab`):

1. **Customer Portal (Jakarta EE)** *(Port 8080)*:
   - Công nghệ: Jakarta Servlet 6.0, JSP 3.1, JSTL, Embedded Tomcat 10.1 fat-jar.
   - Vai trò: Khách hàng (`ROLE_CUSTOMER`) duyệt catalog sản phẩm, quản lý giỏ hàng, đặt đơn hàng mới (trạng thái `PENDING`), xem lịch sử và hủy đơn nếu chưa được duyệt.
2. **Warehouse Desktop (Java Swing)** *(Desktop Application)*:
   - Công nghệ: Java SE 21, Java Swing, JDBC, BCrypt, SwingWorker đa luồng, Dark Modern Theme.
   - Vai trò: Nhân viên kho (`ROLE_WAREHOUSE`) tiếp nhận đơn `PENDING`, xác nhận chuyển `PROCESSING` (tự động kiểm tra và trừ tồn kho `products.stock`), sau đó đóng gói chuyển sang `READY`.
   - Áp dụng khóa lạc quan (**Optimistic Locking**) với trường `version` chống xung đột.
3. **Management Portal (Spring Boot)** *(Port 8081)*:
   - Công nghệ: Spring Boot 3.3.2, Spring Data JPA, Spring Security 6.3 (RBAC), Thymeleaf.
   - Vai trò: Quản lý (`ROLE_MANAGER`) và Quản trị viên (`ROLE_ADMIN`) xem Dashboard doanh thu theo thời gian thực, điều phối giao hàng (`SHIPPING`), quản lý sản phẩm và tra cứu toàn bộ dòng thời gian kiểm toán đa nền tảng (**Audit History Timeline**).

---

## 📁 Cấu trúc thư mục

```
Chuong16-HeThongDonHangDaNenTang/
├── database/
│   ├── schema.sql                         # Lược đồ CSDL chung (users, products, orders, order_items, history)
│   └── sample-data.sql                    # Dữ liệu mẫu (tài khoản BCrypt, sản phẩm ban đầu)
│
├── customer-portal-jakartaee/             # Phân hệ Web Khách Hàng (Port 8080)
│   ├── pom.xml
│   └── src/main/
│       ├── java/vn/edu/eaut/customer/     # Servlets, DAO, Model, Embedded Tomcat runner
│       └── webapp/                        # JSP views, CSS giao diện
│
├── warehouse-desktop-swing/               # Phân hệ Desktop Quản Lý Kho (Java Swing)
│   ├── pom.xml
│   └── src/main/java/vn/edu/eaut/warehouse/ # UI (LoginFrame, MainFrame), DAO, Model
│
├── management-portal-spring/              # Phân hệ Quản Trị Doanh Nghiệp (Port 8081)
│   ├── pom.xml
│   └── src/main/                          # Controllers, Services, Repositories, Entities, Templates
│
├── BaoCao_Lab16.md                        # Báo cáo kỹ thuật chi tiết
└── README.md                              # Hướng dẫn khởi chạy và vận hành hệ thống
```

---

## 🚀 Hướng dẫn khởi chạy 3 phân hệ

### 1. Khởi tạo Cơ sở dữ liệu MySQL

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample-data.sql
```

### 2. Chạy Customer Portal (Jakarta EE - Port 8080)

```bash
cd customer-portal-jakartaee
mvn clean package -DskipTests
java -jar target/customer-portal-jakartaee.jar
# Truy cập: http://localhost:8080
```

### 3. Chạy Warehouse Desktop (Java Swing)

```bash
cd warehouse-desktop-swing
mvn clean package -DskipTests
java -jar target/warehouse-desktop-swing-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### 4. Chạy Management Portal (Spring Boot - Port 8081)

```bash
cd management-portal-spring
mvn clean package -DskipTests
java -jar target/management-portal-spring-1.0-SNAPSHOT.jar
# Truy cập: http://localhost:8081
```

---

## 👥 Tài khoản thử nghiệm có sẵn

| Tài khoản | Mật khẩu | Vai trò (Role) | Phân hệ sử dụng |
|---|---|---|---|
| `customer01` | `123456` | `ROLE_CUSTOMER` | Customer Portal (Web 8080) |
| `warehouse01` | `123456` | `ROLE_WAREHOUSE` | Warehouse Desktop (Swing GUI) |
| `admin01` | `123456` | `ROLE_ADMIN` | Management Portal (Spring Boot 8081) |
| `manager01` | `123456` | `ROLE_MANAGER` | Management Portal (Spring Boot 8081) |
