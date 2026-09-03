package vn.edu.eaut.lab12.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Bài 1: Model Student phục vụ CRUD với các Annotation Validation.
 */
public class Student {

    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(min = 5, max = 20, message = "Mã sinh viên phải có từ 5 đến 20 ký tự")
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2 đến 100 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng hợp lệ (VD: user@eaut.edu.vn)")
    private String email;

    @NotBlank(message = "Lớp không được để trống")
    private String className;

    private String phoneNumber;
    private String address;

    public Student() {
    }

    public Student(Long id, String studentCode, String fullName, String email, String className, String phoneNumber, String address) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return studentCode + " - " + fullName + " (" + className + ")";
    }
}
