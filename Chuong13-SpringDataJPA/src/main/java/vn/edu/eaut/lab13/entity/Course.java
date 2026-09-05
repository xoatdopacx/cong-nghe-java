package vn.edu.eaut.lab13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Bài 8: Entity Course ánh xạ bảng "courses" gồm mã môn, tên môn, số tín chỉ.
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã môn học không được để trống")
    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode;

    @NotBlank(message = "Tên môn học không được để trống")
    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Min(value = 1, message = "Số tín chỉ tối thiểu là 1")
    @Max(value = 10, message = "Số tín chỉ tối đa là 10")
    @Column(nullable = false)
    private int credits;

    public Course() {}

    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}
