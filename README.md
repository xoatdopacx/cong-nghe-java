# Công Nghệ Java – IT3242

> **Sinh viên:** Nguyễn Văn Hùng &nbsp;|&nbsp; **MSSV:** 20230752 &nbsp;|&nbsp; **Môn:** Công Nghệ Java – IT3242

---

## 📁 Cấu trúc repository

```
cong-nghe-java/
├── Chuong1-CaiDatMoiTruong/
│   ├── lab01-java-console/          # Lab 1 – Java SE Console, 5 bài toán
│   └── BaoCao_Lab1_NguyenVanHung_20230752.docx
├── Chuong2-MavenProject/
│   ├── lab02-java-maven-jar/        # Lab 2 – Quản lý SV, tính điểm
│   └── BaoCao_Lab2_NguyenVanHung_20230752.docx
└── Chuong3-JavaSwing/
    └── lab03-java-swing/            # Lab 3 – 8 bài Java Swing Desktop
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

**Tài khoản kiểm thử (Bài 6):**
```
admin / 123456 → vai trò Admin
user  / 123456 → vai trò User
```

**Chạy JAR (menu chọn bài):**
```bash
cd Chuong3-JavaSwing/lab03-java-swing
mvn clean package
java -jar target/lab03-java-swing-1.0-SNAPSHOT.jar
# → Hiện cửa sổ MainLauncher, nhấn vào bài để mở
```

**Chạy từng bài riêng lẻ bằng Maven:**
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai01HelloSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai02TongHaiSo"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai03PhuongTrinhBacNhat"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai04TamGiacSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai05FibonacciSwing"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai06LoginForm"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai07MayTinhMini"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai08QuanLySinhVien"
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.MainLauncher"
```

---

## ⚙️ Yêu cầu môi trường

```bash
java -version   # OpenJDK 21+
javac -version  # javac 21+
mvn -version    # Apache Maven 3.x
```
