# BÁO CÁO BÀI THỰC HÀNH LAB 16
## Học phần: Công nghệ Java (IT3242)
### Đề tài: Xây dựng hệ thống xử lý đơn hàng đa nền tảng sử dụng chung cơ sở dữ liệu
### Nền tảng tích hợp: Java Swing - Jakarta EE - Spring Boot - MySQL

---

### THÔNG TIN SINH VIÊN
- **Họ và tên:** Nguyễn Văn Hùng
- **Mã sinh viên (MSSV):** 20230752
- **Lớp:** DCCNTT 14.2
- **Trường:** Đại học Công nghệ Đông Á (EAUT)
- **Công nghệ áp dụng:** 
  - **Java Desktop:** Java SE 21, Java Swing, JDBC, BCrypt, SwingWorker
  - **Java Web (Khách hàng):** Jakarta EE 10, Servlet 6.0, JSP 3.1, JSTL, Embedded Tomcat 10.1
  - **Java Enterprise (Quản trị):** Spring Boot 3.3.2, Spring MVC, Spring Data JPA, Spring Security 6.3, Thymeleaf, Hibernate 6.5
  - **Cơ sở dữ liệu:** MySQL 8.4 Server (`java_integrated_lab`)
  - **Kiến trúc dữ liệu:** Optimistic Locking (cột `version`), Transaction Management, Audit Logging (`order_status_history`)

---

## 1. MỤC TIÊU BÀI LAB

1. **Tích hợp toàn diện kiến thức học phần Công nghệ Java:**
   - Kết hợp 3 nền tảng cốt lõi đã học trong chương trình: **Java Desktop (Swing)**, **Java Web truyền thống (Jakarta EE - Servlet/JSP)** và **Java Enterprise hiện đại (Spring Boot Framework)**.
2. **Quy trình xử lý nghiệp vụ đơn hàng liên tục (End-to-End Workflow):**
   - Không tạo các ứng dụng rời rạc mà cùng giải quyết một bài toán nghiệp vụ xử lý đơn hàng thời gian thực:
     - **Khách hàng (Customer Portal - Jakarta EE):** Đăng ký, đăng nhập, duyệt danh mục sản phẩm, quản lý giỏ hàng, đặt hàng (trạng thái `PENDING`), xem lịch sử và hủy đơn nếu chưa duyệt.
     - **Nhân viên kho (Warehouse Desktop - Java Swing):** Đăng nhập xác thực BCrypt, giám sát danh sách đơn hàng chờ xử lý, tiếp nhận và xác nhận chuyển sang `PROCESSING` (tự động kiểm tra và trừ tồn kho trong CSDL), đóng gói hoàn tất chuyển sang `READY`.
     - **Ban quản lý (Management Portal - Spring Boot):** Đăng nhập phân quyền với Spring Security, theo dõi Dashboard doanh thu và đơn hàng theo trạng thái, quản lý sản phẩm, điều phối giao vận (`SHIPPING` / `COMPLETED`), tra cứu toàn bộ dòng thời gian biến động trạng thái (Audit History) do cả 3 nền tảng ghi lại.
3. **Sử dụng chung cơ sở dữ liệu MySQL (`java_integrated_lab`):**
   - Thiết kế lược đồ CSDL chuẩn hóa (3NF) gồm: `users`, `products`, `orders`, `order_items`, `order_status_history`.
   - Cả 3 ứng dụng đọc và ghi trực tiếp trên cùng một CSDL MySQL.
4. **Kiểm soát xung đột dữ liệu (Concurrency Control):**
   - Ứng dụng cơ chế khóa lạc quan (**Optimistic Locking**) thông qua trường `version` trong bảng `orders` và `products`, ngăn ngừa ghi đè dữ liệu khi nhiều nền tảng cùng thao tác trên một đơn hàng.
   - Quản lý giao dịch (**Transaction Management**) đảm bảo tính trọn vẹn (ACID) khi tạo đơn hàng và trừ số lượng tồn kho.
5. **Nhật ký biến động và truy vết kiểm toán (Audit History):**
   - Mọi thao tác chuyển đổi trạng thái đơn hàng đều được lưu vết chi tiết vào bảng `order_status_history` gồm: mã đơn, trạng thái cũ, trạng thái mới, người thay đổi, thời điểm, nền tảng thực hiện (`JAKARTA_EE`, `JAVA_SWING`, `SPRING_BOOT`) và ghi chú nghiệp vụ.

---

## 2. CÔNG NGHỆ & MÔI TRƯỜNG PHÁT TRIỂN

| Công cụ / Thư viện | Phiên bản | Vai trò trong hệ thống |
| :--- | :--- | :--- |
| **JDK (Java Development Kit)** | 21 LTS | Môi trường biên dịch và thực thi ứng dụng Java |
| **MySQL Server** | 8.4.x | Hệ quản trị cơ sở dữ liệu quan hệ lưu trữ dữ liệu chung |
| **MySQL Connector/J** | 8.4.0 | Trình điều khiển kết nối JDBC chuẩn cho cả 3 phân hệ |
| **BCrypt (jbcrypt)** | 0.4 | Thuật toán băm mật khẩu bảo mật một chiều |
| **Jakarta Servlet / JSP API** | 6.0 / 3.1 | Nền tảng lập trình Web Servlet & JSP cho Cổng Khách Hàng |
| **Embedded Tomcat** | 10.1.28 | Web Server nhúng chạy cổng 8080 cho Customer Portal |
| **Spring Boot Starter Web** | 3.3.2 | Khung ứng dụng MVC cho Management Portal |
| **Spring Boot Data JPA** | 3.3.2 | Tầng thao tác dữ liệu tự động với Hibernate ORM 6.5 |
| **Spring Boot Security** | 3.3.2 | Khung xác thực và phân quyền RBAC cho Quản lý / Admin |
| **Thymeleaf Template Engine** | 3.1.x | Render giao diện quản trị Server-side linh hoạt |
| **Apache Maven** | 3.9.x | Quản lý dự án đa module và cấu hình build đóng gói |

---

## 3. THIẾT KẾ CƠ SỞ DỮ LIỆU CHUNG (`java_integrated_lab`)

Hệ thống sử dụng cơ sở dữ liệu MySQL tập trung với 5 bảng quan hệ:

```
+--------------------------------------------------------------------------------+
|                              users                                             |
| id (PK) | username (UQ) | password_hash | full_name | role | enabled | ...     |
+--------------------------------------------------------------------------------+
       | 1                                               | 1
       |                                                 |
       | N                                               | N
+------------------------------------+   +------------------------------------+
|               orders               |   |        order_status_history        |
| id (PK)                            |   | id (PK)                            |
| customer_id (FK -> users.id)       |<--| order_id (FK -> orders.id)         |
| created_at                         |   | old_status                         |
| total_amount                       |   | new_status                         |
| status                             |   | changed_by (FK -> users.id)        |
| note                               |   | changed_at                         |
| version (Optimistic Locking)       |   | platform (JAKARTA_EE|SWING|SPRING) |
+------------------------------------+   | note                               |
       | 1                               +------------------------------------+
       |
       | N
+------------------------------------+
|            order_items             |
| id (PK)                            |
| order_id (FK -> orders.id)         |
| product_id (FK -> products.id)     |----+
| quantity                           |    |
| unit_price                         |    | N
| subtotal                           |    |
+------------------------------------+    |
                                          | 1
                               +------------------------------------+
                               |              products              |
                               | id (PK)                            |
                               | code (UQ)                          |
                               | name                               |
                               | price                              |
                               | stock                              |
                               | active                             |
                               | version                            |
                               +------------------------------------+
```

### Danh sách tài khoản mẫu ban đầu:
- `customer01` / `123456` (ROLE_CUSTOMER): Khách hàng Nguyễn Văn Khách
- `customer02` / `123456` (ROLE_CUSTOMER): Khách hàng Trần Thị Mua
- `warehouse01` / `123456` (ROLE_WAREHOUSE): Nhân viên kho Trần Văn Kho
- `warehouse02` / `123456` (ROLE_WAREHOUSE): Nhân viên kho Lê Văn Kho
- `manager01` / `123456` (ROLE_MANAGER): Quản lý kho Lê Văn Quản Lý
- `admin01` / `123456` (ROLE_ADMIN): Quản trị hệ thống

---

## 4. CẤU TRÚC DỰ ÁN TÍCH HỢP

```
java-integrated-lab/
├── database/
│   ├── schema.sql                         # Lược đồ CSDL chung, bảng, khóa ngoại, chỉ mục
│   └── sample-data.sql                    # Dữ liệu mẫu (tài khoản BCrypt, sản phẩm)
│
├── customer-portal-jakartaee/             # PHÂN HỆ 1: CỔNG KHÁCH HÀNG (Port 8080)
│   ├── pom.xml                            # Maven build fat-jar nhúng Tomcat 10.1
│   └── src/main/
│       ├── java/vn/edu/eaut/customer/
│       │   ├── CustomerPortalApp.java     # Entry point chạy Embedded Tomcat 10.1
│       │   ├── db/DBUtil.java             # Quản lý kết nối JDBC MySQL
│       │   ├── model/                     # Model: Product, Order, OrderItem
│       │   ├── dao/                       # UserDAO (Auth BCrypt), OrderDAO (Tạo đơn, giỏ, hủy)
│       │   └── web/                       # AuthFilter, Login, Register, Catalog, Cart, Checkout, Order Servlets
│       └── webapp/                        # JSP views (products, cart, orders, order-detail) & CSS
│
├── warehouse-desktop-swing/               # PHÂN HỆ 2: QUẢN LÝ KHO DESKTOP (Java Swing)
│   ├── pom.xml                            # Đóng gói Fat-JAR jar-with-dependencies
│   └── src/main/java/vn/edu/eaut/warehouse/
│       ├── WarehouseApp.java              # Entry point ứng dụng Swing Desktop
│       ├── db/DatabaseConnection.java     # Singleton JDBC connection pool
│       ├── model/                         # Order, OrderItem
│       ├── dao/                           # UserDAO (ROLE_WAREHOUSE), OrderDAO (Locking, trừ tồn kho)
│       └── ui/
│           ├── LoginFrame.java            # Đăng nhập Dark Theme, kiểm tra phân quyền kho
│           └── MainFrame.java            # Bảng đơn hàng, Stats panel, cập nhật PENDING->PROCESSING->READY
│
├── management-portal-spring/              # PHÂN HỆ 3: QUẢN TRỊ DOANH NGHIỆP (Port 8081)
│   ├── pom.xml                            # Spring Boot 3.3.2, Data JPA, Security, Thymeleaf
│   └── src/main/
│       ├── java/vn/edu/eaut/management/
│       │   ├── ManagementPortalApplication.java
│       │   ├── config/SecurityConfig.java # Phân quyền URL, Form Login, CSRF
│       │   ├── entity/                    # AppUser, Product, Order, OrderItem, OrderStatusHistory
│       │   ├── repository/                # Spring Data JPA Repositories
│       │   ├── service/                   # OrderService (Audit history, status), ProductService
│       │   └── controller/                # HomeController (Dashboard), OrderController, ProductController
│       └── resources/
│           ├── application.properties     # Cấu hình Datasource MySQL, Hibernate, Port 8081
│           └── templates/                 # Thymeleaf: dashboard, orders/list, orders/detail, products
│
├── BaoCao_Lab16.md                        # Báo cáo kỹ thuật chi tiết
└── BaoCao_Lab16_NguyenVanHung_20230752.docx # Báo cáo Word chuẩn khoa học EAUT
```

---

## 5. KỊCH BẢN KIỂM THỬ TÍCH HỢP ĐA NỀN TẢNG & KẾT QUẢ

### Bảng Kịch Bản Kiểm Thử (End-to-End Test Case)

| STT | Phân hệ thực hiện | Tài khoản / Vai trò | Thao tác thực hiện | Kết quả mong đợi | Trạng thái |
| :---: | :--- | :--- | :--- | :--- | :---: |
| **TC01** | **Customer Portal** (Jakarta EE) | Khách vãng lai | Truy cập `http://localhost:8080/products` | Hiển thị catalog sản phẩm, số lượng tồn kho | **ĐẠT** |
| **TC02** | **Customer Portal** (Jakarta EE) | `customer01` / `123456` | Đăng nhập hệ thống | Xác thực BCrypt thành công, chuyển hướng về catalog | **ĐẠT** |
| **TC03** | **Customer Portal** (Jakarta EE) | `customer01` (CUSTOMER) | Thêm 2 Bàn phím cơ (SP001) và 1 Chuột không dây (SP002) vào giỏ | Giỏ hàng cập nhật số lượng, tính đúng tạm tính 2.050.000₫ | **ĐẠT** |
| **TC04** | **Customer Portal** (Jakarta EE) | `customer01` (CUSTOMER) | Nhập ghi chú và nhấn "Đặt Hàng Ngay" | Đơn hàng #1 được tạo với trạng thái `PENDING`. Ghi 1 bản ghi vào `order_status_history` với platform `JAKARTA_EE` | **ĐẠT** |
| **TC05** | **Customer Portal** (Jakarta EE) | `customer01` (CUSTOMER) | Xem lịch sử đơn hàng tại `/orders` | Đơn hàng #1 xuất hiện với badge màu vàng `PENDING` | **ĐẠT** |
| **TC06** | **Warehouse Desktop** (Java Swing) | `warehouse01` / `123456` | Mở ứng dụng Swing và đăng nhập | Kiểm tra đúng `ROLE_WAREHOUSE`, mở màn hình chính | **ĐẠT** |
| **TC07** | **Warehouse Desktop** (Java Swing) | `warehouse01` (WAREHOUSE) | Quan sát bảng đơn hàng và thẻ thống kê | Nhìn thấy ngay đơn #1 trạng thái `PENDING` vừa tạo từ web | **ĐẠT** |
| **TC08** | **Warehouse Desktop** (Java Swing) | `warehouse01` (WAREHOUSE) | Double-click đơn #1 | Hộp thoại chi tiết mở ra hiển thị 2 mặt hàng kèm đơn giá | **ĐẠT** |
| **TC09** | **Warehouse Desktop** (Java Swing) | `warehouse01` (WAREHOUSE) | Chọn đơn #1, nhấn "Tiếp nhận & Xử lý (PROCESSING)" | Trạng thái chuyển sang `PROCESSING`, tồn kho SP001 giảm 20→18, SP002 giảm 30→29. Ghi lịch sử platform `JAVA_SWING` | **ĐẠT** |
| **TC10** | **Warehouse Desktop** (Java Swing) | `warehouse01` (WAREHOUSE) | Chọn đơn #1, nhấn "Đóng gói xong (READY)" | Đơn hàng chuyển sang trạng thái `READY`. Ghi lịch sử platform `JAVA_SWING` | **ĐẠT** |
| **TC11** | **Management Portal** (Spring Boot) | `admin01` / `123456` | Truy cập `http://localhost:8081` và đăng nhập | Spring Security cấp quyền, vào Dashboard thống kê | **ĐẠT** |
| **TC12** | **Management Portal** (Spring Boot) | `admin01` (ADMIN) | Xem thống kê doanh thu và đơn hàng | Doanh thu cập nhật 2.050.000₫, biểu đồ phân bố trạng thái | **ĐẠT** |
| **TC13** | **Management Portal** (Spring Boot) | `admin01` (ADMIN) | Vào chi tiết đơn #1 tại `/orders/1` | Dòng thời gian lịch sử hiển thị đầy đủ chuỗi biến động: `JAKARTA_EE` (PENDING) → `JAVA_SWING` (PROCESSING) → `JAVA_SWING` (READY) | **ĐẠT** |
| **TC14** | **Management Portal** (Spring Boot) | `admin01` (ADMIN) | Chuyển trạng thái đơn sang `SHIPPING` | Đơn hàng chuyển sang `SHIPPING`, ghi thêm bản ghi lịch sử với platform `SPRING_BOOT` | **ĐẠT** |
| **TC15** | **Toàn hệ thống** (MySQL Engine) | DBA / Kiểm thử viên | Kiểm tra bảng `order_status_history` trong MySQL | Xác nhận đầy đủ 4 sự kiện lịch sử ghi nhận từ cả 3 nền tảng khác nhau | **ĐẠT** |

---

## 6. ĐÁNH GIÁ & KẾT LUẬN

1. **Tính hoàn thiện của giải pháp đa nền tảng:**
   - Hệ thống chứng minh khả năng cộng tác hoàn hảo giữa các công nghệ Java khác nhau trong cùng một hệ sinh thái doanh nghiệp:
     - **Jakarta EE Web** đáp ứng tốt cho giao diện người dùng bên ngoài (khách hàng cuối, nhẹ nhàng, tương thích cao).
     - **Java Swing Desktop** mang lại sự tiện lợi, tốc độ phản hồi tức thì cho bộ phận vận hành kho nội bộ.
     - **Spring Boot Enterprise** cung cấp sức mạnh quản trị, bảo mật chặt chẽ (Spring Security), quản lý thực thể trực quan (Spring Data JPA) và bảng điều khiển tổng quan cho ban điều hành.
2. **Đảm bảo tính toàn vẹn và nhất quán dữ liệu:**
   - Việc áp dụng cơ chế khóa lạc quan (**Optimistic Locking**) giúp phát hiện và ngăn chặn xung đột dữ liệu hiệu quả khi nhiều nhân viên cùng thao tác.
   - Quản lý giao dịch (**ACID Transaction**) bảo đảm việc trừ kho và tạo đơn luôn đồng bộ, loại bỏ hoàn toàn nguy cơ âm kho hoặc sai lệch số liệu.
3. **Giá trị thực tiễn và học thuật:**
   - Bài thực hành Lab 16 là đỉnh cao tổng hợp của toàn bộ học phần **Công nghệ Java**, trang bị cho sinh viên tư duy kiến trúc hệ thống phân tán, kỹ năng tích hợp đa nền tảng và năng lực triển khai các bài toán thực tế trong môi trường sản xuất.
