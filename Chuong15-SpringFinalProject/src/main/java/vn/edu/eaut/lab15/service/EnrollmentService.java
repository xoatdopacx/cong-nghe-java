package vn.edu.eaut.lab15.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab15.entity.*;
import vn.edu.eaut.lab15.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Enrollment findById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đăng ký với ID: " + id));
    }

    // Bài 4: Đăng ký học phần
    public void enroll(Long studentId, Long courseId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Sinh viên đã đăng ký môn học này rồi!");
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());
        enrollmentRepository.save(enrollment);
    }

    // Bài 7: Hủy đăng ký học phần
    public void cancelEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    // Bài 8: Danh sách môn học của một sinh viên
    public List<Enrollment> findByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Bài 9: Thống kê
    public long count() {
        return enrollmentRepository.count();
    }
}
