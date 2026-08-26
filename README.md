# Công Nghệ Java – IT3242

> **Sinh viên:** Nguyễn Văn Hùng &nbsp;|&nbsp; **MSSV:** 20230752 &nbsp;|&nbsp; **Môn:** Công Nghệ Java – IT3242

---

## 📁 Cấu trúc repository

```
cong-nghe-java/
├── Chuong1-CaiDatMoiTruong/
│   └── lab01-java-console/          # Lab 1 – Java SE Console, 5 bài toán
├── Chuong2-MavenProject/
│   └── lab02-java-maven-jar/        # Lab 2 – Quản lý SV, tính điểm
├── Chuong3-JavaSwing/
│   └── lab03-java-swing/            # Lab 3 – 8 bài Java Swing Desktop
├── Chuong4-SwingWorker/
│   └── lab04-swingworker/           # Lab 4 – 10 bài SwingWorker, EDT & Multi-threading
├── Chuong5-MiniShop/
│   └── lab05-minishop-swing-jdbc/   # Lab 5 – Project MiniShop Java Swing, JDBC, MySQL (3 Lớp)
├── Chuong6-ServletJSP/
│   └── lab06-student-web/           # Lab 6 – Servlet, JSP, JSTL, Filter, Listener, MVC
├── Chuong8-JSFValidation/
│   └── lab08-jsf-validation/        # Lab 8 – JSF, CDI, Bean Validation, FacesMessage
├── Chuong9-JPA/
│   └── lab09-jpa-repository/        # Lab 9 – JPA 3.1, Hibernate ORM 6.4, Entity, Repository, Transaction
└── Chuong10-SecuredApp/
    └── lab10-secured-app/           # Lab 10 – Authentication, Role-Based Access Control, Filter Security
```

---

## 📗 Lab 1 – Cài đặt môi trường & Java SE Console

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Java SE, JDK, JVM, Maven |
| Package | `vn.edu.eaut.lab1` |
| Build | `mvn package` |
| Chạy | `java -jar target/lab01-java-console-1.0-SNAPSHOT.jar` |

**5 bài toán Console:**

| Bài | Tên | Kiểm thử |
|-----|-----|----------|
| 1 | Tổng số chẵn S = 2+4+…+n | n=10 → 30 |
| 2 | Tổng nghịch đảo S = 1+1/2+…+1/n | n=4 → 2.0833 |
| 3 | Kiểm tra số nguyên tố | 17 → nguyên tố |
| 4 | Kiểm tra & phân loại tam giác | 3,4,5 → vuông |
| 5 | Dãy Fibonacci | n=7 → 0 1 1 2 3 5 8 |

---

## 📘 Lab 2 – Maven Project & Đóng gói JAR

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Maven, JAR packaging, Java SE |
| Package | `vn.edu.eaut.lab2` |
| Lớp | `Student`, `GradeCalculator`, `App` |
| Build | `mvn clean package` |
| Chạy | `java -jar target/lab02-java-maven-jar-1.0-SNAPSHOT.jar` |

**Công thức tính điểm:**
```
Tổng kết = chuyên cần×10% + giữa kỳ×30% + cuối kỳ×60%
Xếp loại: A≥8.5 | B≥7.0 | C≥5.5 | D≥4.0 | F<4.0
```

**Kiểm thử:** `20230752 | 8.0 / 7.0 / 9.0 → Tổng kết: 8.30 → B`

---

## 📙 Lab 3 – Java Swing Desktop

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Java SE Swing, Layout, Event Handling, EDT |
| Package | `vn.edu.eaut.lab3` |
| Build | `mvn clean compile` |
| Chạy | `mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai01HelloSwing"` |

**8 bài tập Swing:**

| Bài | Lớp | Swing Components | Mô tả |
|-----|-----|-----------------|-------|
| 1 | `Bai01HelloSwing` | JTextField, JButton, FlowLayout | Chào người dùng theo tên |
| 2 | `Bai02TongHaiSo` | GridLayout, JLabel | Tính tổng hai số thực |
| 3 | `Bai03PhuongTrinhBacNhat` | BorderLayout, JPanel | Giải ax + b = 0 (3 trường hợp) |
| 4 | `Bai04TamGiacSwing` | BorderLayout, GridLayout | Kiểm tra & phân loại 5 loại tam giác |
| 5 | `Bai05FibonacciSwing` | JTextArea, JScrollPane | Dãy Fibonacci n số (n ≤ 92) |
| 6 | `Bai06LoginForm` | JPasswordField, JComboBox, JCheckBox | Form đăng nhập + hiển thị mật khẩu |
| 7 | `Bai07MayTinhMini` | JTextField readonly, JTextArea | Máy tính 4 phép + lịch sử |
| 8 | `Bai08QuanLySinhVien` | JTable, DefaultTableModel | CRUD sinh viên + xếp loại tự động |

---

## 📕 Lab 4 – Event Handling, EDT & SwingWorker

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Java Swing, Event Handling, EDT, Multi-threading, SwingWorker, JProgressBar, JFileChooser, JTable |
| Package | `vn.edu.eaut.lab4` |
| Build | `mvn clean package` |
| Chạy JAR | `java -jar target/lab04-swingworker-1.0-SNAPSHOT-jar-with-dependencies.jar` |

**10 bài tập SwingWorker & Xử lý tác vụ nền:**

| Bài | Lớp | Mô tả & Xử lý bất đồng bộ |
|-----|-----|---------------------------|
| 1 | `CountdownFrame` | Đồng hồ đếm ngược dùng `SwingWorker<Void, Integer>`, cập nhật qua `publish()/process()`. |
| 2 | `ProgressDemoFrame` | Mô phỏng tải dữ liệu 10s với `setProgress()` & `PropertyChangeListener`. |
| 3 | `PrimeSumFrame` | Tính tổng các số nguyên tố nhỏ hơn N bất đồng bộ, trả kết quả trong `done()`. |
| 4 | `FibonacciFrame` | Tìm số Fibonacci thứ N với `BigInteger` & Memoization (`Map<Integer, BigInteger>`). |
| 5 | `FileLineCounterFrame` | Chọn file lớn, đếm số dòng bất đồng bộ theo tỷ lệ bytes đã đọc. |
| 6 | `CancelableTaskFrame` | Hủy tác vụ an toàn với `worker.cancel(true)` và kiểm tra `isCancelled()`. |
| 7 | `KeywordSearchFrame` | Tìm từ khóa trong file văn bản lớn, dùng `publish()` nạp dòng khớp lên UI. |
| 8 | `StudentCsvStatsFrame` | Nạp dữ liệu từ file `students.csv`, tính điểm TB và sinh viên điểm cao nhất. |
| 9 | `ProductLoadDemoFrame` | Mô phỏng nạp danh sách sản phẩm bất đồng bộ (bước đệm sang Lab 5 CSDL). |
| 10 | `ProductManagerFrame` | Mini project Quản lý sản phẩm: Thêm, Sửa, Xóa, Đọc/Lưu file CSV bất đồng bộ. |

---

## 🏬 Lab 5 – Project MiniShop (Java Swing, JDBC, MySQL & 3 Lớp)

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Java Swing, JDBC, MySQL, Mô hình 3 lớp (DAL - BUS - GUI), SwingWorker, FlatLaf |
| Package | `vn.edu.eaut.lab5` |
| CSDL | `database/minishop_db.sql` (bảng: `danh_muc`, `san_pham`, `khach_hang`, `hoa_don`, `chi_tiet_hoa_don`, `tai_khoan`) |
| Build | `mvn clean package` |
| Chạy JAR | `java -jar target/lab05-minishop-swing-jdbc-1.0-SNAPSHOT-jar-with-dependencies.jar` |

**Tổng hợp 10 Chức năng Project MiniShop:**

| STT | Tên Chức Năng / Bài | Lớp chính (DAL / BUS / UI) | Mô tả chi tiết triển khai |
|-----|---------------------|---------------------------|---------------------------|
| 1 | Kết nối CSDL MySQL | `DBHelper.java`, `App.java` | Kết nối MySQL qua JDBC Driver `com.mysql.cj.jdbc.Driver`, quản lý URL, USER, PASSWORD. |
| 2 | Quản lý sản phẩm | `SanPhamDAL`, `SanPhamBUS`, `SanPhamPanel` | Thao tác CRUD sản phẩm, hiển thị JTable, tìm kiếm theo tên và lọc theo danh mục. |
| 3 | Quản lý khách hàng & SĐT | `KhachHangBUS`, `KhachHangPanel`, `PhoneDocumentFilter` | CRUD khách hàng, áp dụng `PhoneDocumentFilter` giới hạn SĐT chỉ nhập số & max 10 ký tự. |
| 4 | Lập hóa đơn & Chi tiết | `HoaDonDAL`, `HoaDonBUS`, `HoaDonPanel` | Lập hóa đơn sử dụng **JDBC Transaction (`setAutoCommit(false)`)** lưu hóa đơn, chi tiết & tự trừ kho. |
| 5 | Tìm kiếm & Thống kê | `ThongKeDAL`, `ThongKeBUS`, `ThongKePanel` | Thống kê doanh thu theo khoảng ngày, hóa đơn lớn nhất, sản phẩm bán chạy bất đồng bộ với **`SwingWorker`**. |
| 6 | Quản lý danh mục | `DanhMucDAL`, `DanhMucBUS`, `DanhMucPanel` | CRUD danh mục sản phẩm, JComboBox danh mục, ràng buộc không cho xóa danh mục đang chứa sản phẩm. |
| 7 | Quản lý tồn kho | `SanPhamPanel`, `HoaDonPanel` | Kiểm tra tồn kho trước khi bán, tự động trừ tồn kho, cảnh báo dòng màu vàng/đỏ cho sản phẩm tồn < 5. |
| 8 | Xuất hóa đơn TXT / CSV | `ExporterUtil.java`, `HoaDonPanel` | Xuất file hóa đơn vừa tạo ra định dạng TXT (`HoaDon_MaHD.txt`) hoặc CSV (`HoaDon_MaHD.csv`). |
| 9 | Tìm kiếm & Phân trang | `SanPhamDAL`, `SanPhamPanel` | Phân trang danh sách sản phẩm (10 dòng/trang) kết hợp tìm kiếm từ khóa bất đồng bộ qua SwingWorker. |
| 10 | Đăng nhập & Phân quyền | `TaiKhoanDAL`, `LoginFrame`, `MainFrame` | Đăng nhập hệ thống & phân quyền truy cập tab JTabbedPane theo vai trò (`ADMIN`, `NHANVIEN`, `KETOAN`). |

**Tài khoản kiểm thử Đăng nhập (Bài 10):**
```
admin / 123456 → Vai trò ADMIN    (Toàn quyền 5 tab: Sản phẩm, Khách hàng, Hóa đơn, Thống kê, Danh mục)
nv01  / 123456 → Vai trò NHANVIEN (Quản lý Sản phẩm, Khách hàng, Lập hóa đơn)
kt01  / 123456 → Vai trò KETOAN   (Lập hóa đơn & Thống kê doanh thu)
```

**Khởi chạy dự án:**
```bash
cd Chuong5-MiniShop/lab05-minishop-swing-jdbc
# 1. Khởi tạo CSDL MySQL trước khi chạy
mysql -u root < database/minishop_db.sql

# 2. Biên dịch và khởi chạy ứng dụng
mvn clean package
java -jar target/lab05-minishop-swing-jdbc-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## 🌐 Lab 6 – Servlet, JSP, JSTL, Filter, Listener & MVC (Chương 3 - Jakarta EE)

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Jakarta EE, Servlet 6.0, JSP, JSTL 3.0, Filter, Listener, Session, MVC |
| Package | `vn.edu.eaut.lab6` |
| Web Container | Apache Tomcat 10.x (embedded via Cargo Maven Plugin) |
| Build | `mvn clean package` |
| Deploy | `mvn package cargo:run` → http://localhost:8080/lab06-student-web/ |

**Tổng hợp 12 bài tập Servlet & MVC:**

| Bài | Tên | Lớp / File | Mô tả |
|-----|-----|-----------|-------|
| 1 | Hello Servlet | `HelloServlet` → `/hello` | Servlet cơ bản hiển thị thông báo |
| 2 | Form nhập sinh viên | `student-form.jsp` | Form HTML gửi POST đến Servlet |
| 3 | Danh sách SV (JSTL) | `StudentServlet`, `student-list.jsp` | `c:forEach`, `c:if`, `c:choose` hiển thị danh sách |
| 4 | Đăng nhập & Session | `LoginServlet`, `login.jsp`, `welcome.jsp` | Lưu session, redirect, hiển thị user |
| 5 | Filter & Listener | `AuthFilter`, `AppContextListener`, `SessionLogListener` | Chặn truy cập + log vòng đời |
| 6 | Tìm kiếm sinh viên | `StudentServlet` + `StudentStore.search()` | Tìm không phân biệt hoa/thường |
| 7 | Xóa sinh viên | `StudentServlet` (action=delete) | Confirm dialog + xóa khỏi danh sách |
| 8 | Cập nhật sinh viên | `StudentServlet` (action=edit) | Form sửa, mã SV readonly |
| 9 | Phân quyền Admin/User | `AuthFilter` + `403.jsp` | Admin CRUD, User chỉ xem |
| 10 | Dashboard | `DashboardServlet`, `dashboard.jsp` | Thống kê SV theo lớp, thời gian login |
| 11 | Ghi log truy cập | `AccessLogFilter` | Log URI, method, user, thời gian |
| 12 | Khởi tạo dữ liệu mẫu | `AppContextListener` | 7 SV mẫu khi ứng dụng khởi động |

**Tài khoản kiểm thử:**
```
admin / 123456 → Vai trò Admin (Toàn quyền CRUD sinh viên)
user  / 123456 → Vai trò User  (Chỉ xem danh sách, không thêm/sửa/xóa)
```

**Khởi chạy dự án:**
```bash
cd Chuong6-ServletJSP/lab06-student-web
mvn clean package cargo:run
# Truy cập: http://localhost:8080/lab06-student-web/
```

---

## 🧩 Lab 8 – JSF Validation & Message (Chương 3 - Jakarta Faces)

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Jakarta Faces (JSF) 4.0, CDI (Weld), Bean Validation (Hibernate Validator), Facelets |
| Package | `vn.edu.eaut.lab8` |
| Web Container | Apache Tomcat 10.x (embedded via Cargo Maven Plugin) |
| Build | `mvn clean package` |
| Deploy | `mvn package cargo:run` → http://localhost:8080/lab08-jsf-validation/ |

**Tổng hợp 13 bài tập JSF:**

| Bài | Tên | Lớp / File | Mô tả |
|-----|-----|-----------|-------|
| 1 | Trang JSF đầu tiên | `index.xhtml` | FacesServlet + danh sách bài tập |
| 2 | Model + Repository | `SinhVien.java`, `SinhVienRepository.java` | Bean Validation: @NotBlank, @Email, @Size |
| 3 | Managed Bean | `SinhVienBean.java` | CDI @Named @SessionScoped, CRUD + FacesMessage |
| 4 | Form JSF + Validation | `sinhvien-form.xhtml` | h:form, h:inputText, h:message, h:messages |
| 5 | h:dataTable + Xóa | `sinhvien-list.xhtml` | h:dataTable, f:facet, commandButton delete |
| 6 | Form Sách JSF | `SachBean`, `sach-form/list.xhtml` | Validate tên, tác giả @NotBlank, năm @Min/@Max |
| 7 | Form Sản phẩm JSF | `ProductBean`, `product-form/list.xhtml` | Validate giá @Positive, SL @PositiveOrZero |
| 8 | Form đăng nhập JSF | `LoginBean`, `login.xhtml` | h:inputSecret, FacesMessage error/success |
| 9 | Sửa sinh viên | `SinhVienBean.edit()` | Load dữ liệu lên form, cập nhật |
| 10 | Tìm kiếm sinh viên | `SinhVienBean.search()` | Filter theo họ tên, mã SV, lớp |
| 11 | Layout dùng chung | `template.xhtml` | ui:composition, ui:insert, ui:define |
| 12 | selectOneMenu | `sinhvien-form.xhtml` | h:selectOneMenu cho trường lớp |
| 13 | So sánh Servlet/JSP vs JSF | Trong báo cáo | Bảng so sánh 12 tiêu chí |

**Khởi chạy dự án:**
```bash
cd Chuong8-JSFValidation
mvn clean package cargo:run
# Truy cập: http://localhost:8080/lab08-jsf-validation/index.xhtml
```

---

## 📕 Lab 9 – Tích hợp JPA: Entity, Repository, Transaction

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Jakarta EE 10, Jakarta Persistence 3.1, Hibernate ORM 6.4.4, MySQL 9.5, Tomcat 10.1.30 |
| Database | `lab09_jpa` trên MySQL Server 9.5 |
| Package | `vn.edu.eaut.lab9` |
| Kiến trúc | MVC + Generic Repository Pattern + Service Layer + Singleton EntityManagerFactory |
| Build & Run | `cd lab09-jpa-repository && mvn clean package cargo:run` |
| URL | `http://localhost:8080/lab09-jpa-repository/` |

**Các module & chức năng chính:**

| Phân hệ | Entity JPA | Mô tả chức năng |
|---|---|---|
| **Dashboard** | Thống kê đa thực thể | Tổng hợp số liệu realtime từ EntityManager, tự động seed mẫu |
| **Sinh Viên** | `SinhVien.java` | CRUD, phân trang 5 bản ghi/trang, tìm kiếm, `@ManyToOne` với LopHoc |
| **Lớp Học** | `LopHoc.java` | CRUD, đếm số sinh viên theo quan hệ `@OneToMany` (JPQL Fetch Join) |
| **Môn Học** | `MonHoc.java` | CRUD danh mục học phần và số tín chỉ |
| **Điểm Học Phần** | `Diem.java` | Nhập điểm theo thang 10, liên kết SinhVien và MonHoc |
| **Sản Phẩm** | `SanPham.java` | CRUD hàng hóa, giá niêm yết VNĐ, quản lý tồn kho |

---

## 🔐 Lab 10 – Thêm Login, Role, Bảo Vệ URL và Hoàn Thiện Ứng Dụng

| Mục | Chi tiết |
|-----|----------|
| Công nghệ | Jakarta EE 10, JPA 3.1, Hibernate ORM 6.4, Servlet 6.0, JSP 3.1, JSTL 3.0, MySQL 9.5 |
| Database | `lab10_secured` trên MySQL Server |
| Package | `vn.edu.eaut.lab10` |
| Bảo mật | AuthenticationFilter, AuthorizationFilter, HttpSession, Role-Based Access Control |
| Build & Run | `cd Chuong10-SecuredApp && mvn clean package cargo:run` |
| URL | `http://localhost:8080/lab10-secured-app/login.jsp` |

**Tài khoản & Phân quyền kiểm thử:**

| Email | Mật khẩu | Vai trò (Role) | Menu hiển thị & Quyền truy cập |
|---|---|---|---|
| `admin@eaut.edu.vn` | `admin123` | **ADMIN** | Toàn quyền quản trị hệ thống: Quản lý Users, Sinh viên, Lớp học, Môn học, Điểm, Sản phẩm |
| `staff@eaut.edu.vn` | `staff123` | **STAFF** | Nhân viên nghiệp vụ: Quản lý Điểm & Sản phẩm. Truy cập `/admin/*` $\rightarrow$ Chặn báo lỗi 403 |
| `user@eaut.edu.vn` | `user123` | **USER** | Người dùng tiêu chuẩn: Xem Dashboard, Hồ sơ cá nhân & Đổi mật khẩu. Truy cập `/admin/*`, `/staff/*` $\rightarrow$ 403 |

**Các tính năng nổi bật:**
- **Authentication & Session:** Đăng nhập/Đăng xuất kiểm tra CSDL, lưu `currentUser` vào `HttpSession`, thiết lập header chống cache khi logout.
- **Authorization Filter:** Chặn các request trái phép theo Role, chuyển hướng về `403.jsp` thân thiện.
- **Dynamic UI:** Menu Sidebar tự động ẩn/hiện theo vai trò người dùng đang đăng nhập.
- **Error Pages:** Trang lỗi chuẩn hóa `403.jsp`, `404.jsp`, `500.jsp`.
- **5 Module Nghiệp vụ JPA:** Sinh viên, Lớp học, Môn học, Điểm, Sản phẩm.

---

## ⚙️ Yêu cầu môi trường

```bash
java -version   # OpenJDK 17+ / OpenJDK 21+
javac -version  # javac 17+ / javac 21+
mvn -version    # Apache Maven 3.x
mysql --version # MySQL Server 8.0+ (Lab 5)
```
