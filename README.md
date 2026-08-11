# Công Nghệ Java

> **Sinh viên:** Nguyễn Văn Hùng | **MSSV:** 20230752 | **Môn học:** Công Nghệ Java

---

## 📁 Cấu trúc

```
cong-nghe-java/
├── Chuong1-CaiDatMoiTruong/
│   ├── lab01-java-console/        # 5 bài toán Console (Maven)
│   └── BaoCao_Lab1_NguyenVanHung_20230752.docx
└── Chuong2-MavenProject/
    └── lab02-java-maven-jar/      # Quản lý sinh viên, tính điểm (Maven)
```

---

## 📗 Chương 1 – Cài đặt môi trường & Java SE Console

> **Sinh viên:** Nguyễn Văn Hùng  
> **MSSV:** 20230752  
> **Môn học:** Công Nghệ Java

---

## 📁 Cấu trúc

```
cong-nghe-java/
└── Chuong1-CaiDatMoiTruong/
    ├── lab01-java-console/          # Maven project chính
    │   ├── pom.xml
    │   ├── src/main/java/vn/edu/eaut/lab1/
    │   │   ├── App.java             # Menu + Scanner input
    │   │   └── So.java              # Logic 5 bài toán
    │   └── target/
    │       └── lab01-java-console-1.0-SNAPSHOT.jar
    └── BaoCao_Lab1_NguyenVanHung_20230752.docx
```

---

## 📚 Chương 1 – Xây dựng ứng dụng Java SE Console bằng Maven

### Kiểm tra môi trường
```bash
java -version    # OpenJDK 25.0.1
javac -version   # javac 25.0.1
mvn -version     # Apache Maven 3.9.16
echo $JAVA_HOME  # /Library/Java/JavaVirtualMachines/temurin-21.jdk/...
```

### 5 bài tập Console

| Bài | Tên bài | Kết quả kiểm thử |
|-----|---------|-----------------|
| 1 | Tổng số chẵn S = 2+4+...+n | n=10 → S=30 |
| 2 | Tổng nghịch đảo S = 1+1/2+...+1/n | n=4 → 2.0833 |
| 3 | Kiểm tra số nguyên tố | 17 → nguyên tố |
| 4 | Kiểm tra & phân loại tam giác | 3,4,5 → vuông |
| 5 | Dãy Fibonacci | n=7 → 0 1 1 2 3 5 8 |

### Build và chạy
```bash
cd Chuong1-CaiDatMoiTruong/lab01-java-console

# Build JAR
mvn clean package

# Chạy JAR
java -jar target/lab01-java-console-1.0-SNAPSHOT.jar
```

### Quy trình biên dịch
```
.java ──(javac)──► .class (bytecode) ──(JVM/JIT)──► Thực thi
                                     ──(jar)──► .jar ──(java -jar)──► Thực thi
```

---

## 📘 Chương 2 – Quản lý dự án với Maven & đóng gói JAR

### Bài toán: Tính điểm tổng kết học phần

**Công thức:** `Tổng kết = chuyên cần×10% + giữa kỳ×30% + cuối kỳ×60%`

**Xếp loại:** A ≥ 8.5 | B ≥ 7.0 | C ≥ 5.5 | D ≥ 4.0 | F < 4.0

### Cấu trúc project
```
lab02-java-maven-jar/
├── pom.xml
└── src/main/java/vn/edu/eaut/lab2/
    ├── App.java              # Nhập liệu, hiển thị kết quả
    ├── Student.java          # Model sinh viên
    └── GradeCalculator.java  # Tính điểm, xếp loại, validate
```

### Chạy
```bash
cd Chuong2-MavenProject/lab02-java-maven-jar
mvn clean package
java -jar target/lab02-java-maven-jar-1.0-SNAPSHOT.jar
```

### Kiểm thử
```
Ma SV: 20230752 | Ho ten: Nguyen Van Hung
Chuyen can: 8.0 | Giua ky: 7.0 | Cuoi ky: 9.0
Tong ket: 8.30 → Xep loai: B
```

---

## 📙 Chương 3 – Java Swing Desktop

### 8 bài tập Swing

| Bài | Tên | Swing components |
|-----|-----|-----------------|
| 1 | Chào người dùng | JTextField, JButton, FlowLayout |
| 2 | Tính tổng hai số | GridLayout, JLabel |
| 3 | Giải PT bậc nhất ax+b=0 | BorderLayout, JPanel |
| 4 | Kiểm tra tam giác | BorderLayout, GridLayout |
| 5 | Dãy Fibonacci | JTextArea, JScrollPane |
| 6 | Form đăng nhập | JPasswordField, JComboBox, JCheckBox |
| 7 | Máy tính mini | Lịch sử tính với JTextArea |
| 8 | Quản lý SV JTable | JTable, DefaultTableModel, CRUD |

### Chạy
```bash
cd Chuong3-JavaSwing/lab03-java-swing
mvn clean compile
mvn exec:java -Dexec.mainClass="vn.edu.eaut.lab3.Bai01HelloSwing"
# Thay Bai01 -> Bai08 để chạy từng bài
```
