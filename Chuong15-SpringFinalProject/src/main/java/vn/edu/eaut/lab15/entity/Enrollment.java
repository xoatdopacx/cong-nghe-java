package vn.edu.eaut.lab15.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    public Enrollment() {}

    public Enrollment(Student student, Course course, LocalDate enrollDate) {
        this.student = student;
        this.course = course;
        this.enrollDate = enrollDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getEnrollDate() { return enrollDate; }
    public void setEnrollDate(LocalDate enrollDate) { this.enrollDate = enrollDate; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
