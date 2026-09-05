package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.StudentRepository;

import java.util.List;

/**
 * Bài 4: Service tầng nghiệp vụ gọi StudentRepository.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID = " + id));
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    /** Bài 7: Tìm kiếm sinh viên theo từ khóa (họ tên, mã SV, lớp) */
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        // Tìm theo họ tên trước
        List<Student> results = studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
        if (results.isEmpty()) {
            results = studentRepository.findByStudentCodeContainingIgnoreCase(keyword.trim());
        }
        if (results.isEmpty()) {
            results = studentRepository.findByClassNameContainingIgnoreCase(keyword.trim());
        }
        return results;
    }

    /** Kiểm tra trùng mã SV (thêm mới) */
    public boolean existsByStudentCode(String studentCode) {
        return studentRepository.existsByStudentCode(studentCode);
    }

    /** Kiểm tra trùng mã SV nhưng bỏ qua chính nó (khi sửa) */
    public boolean existsByStudentCodeExcludeId(String studentCode, Long id) {
        return studentRepository.existsByStudentCodeAndIdNot(studentCode, id);
    }

    public long count() {
        return studentRepository.count();
    }
}
