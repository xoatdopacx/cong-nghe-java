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
│   └── lab04-swingworker/          # Lab 4 – 10 bài SwingWorker, EDT & Multi-threading
└── Chuong5-MiniShop/
    └── lab05-minishop-swing-jdbc/  # Lab 5 – Project MiniShop Java Swing, JDBC, MySQL (3 Lớp)
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

## ⚙️ Yêu cầu môi trường

```bash
java -version   # OpenJDK 17+ / OpenJDK 21+
javac -version  # javac 17+ / javac 21+
mvn -version    # Apache Maven 3.x
mysql --version # MySQL Server 8.0+
```
