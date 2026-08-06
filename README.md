# Công Nghệ Java

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
