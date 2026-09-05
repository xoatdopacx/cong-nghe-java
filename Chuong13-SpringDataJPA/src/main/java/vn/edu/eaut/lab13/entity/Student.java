package vn.edu.eaut.lab13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Bài 2: Entity Student ánh xạ bảng "students" trong cơ sở dữ liệu.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(min = 5, max = 20, message = "Mã sinh viên phải từ 5 đến 20 ký tự")
    @Column(name = "student_code", nullable = false, unique = true, length = 20)
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Column(length = 100)
    private String email;

    @NotBlank(message = "Lớp không được để trống")
    @Column(name = "class_name", length = 50)
    private String className;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(length = 200)
    private String address;

    public Student() {}

    public Student(String studentCode, String fullName, String email, String className) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
