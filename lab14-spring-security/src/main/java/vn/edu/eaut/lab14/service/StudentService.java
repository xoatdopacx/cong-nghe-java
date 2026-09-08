package vn.edu.eaut.lab14.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab14.entity.Student;
import vn.edu.eaut.lab14.repository.StudentRepository;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    public StudentService(StudentRepository repo) { this.repo = repo; }

    public List<Student> findAll() { return repo.findAll(); }
    public Student findById(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên ID = " + id)); }
    public Student save(Student s) { return repo.save(s); }
    public void deleteById(Long id) { repo.deleteById(id); }
    public List<Student> search(String kw) { return (kw == null || kw.isBlank()) ? findAll() : repo.findByFullNameContainingIgnoreCase(kw.trim()); }
    public boolean existsByStudentCode(String code) { return repo.existsByStudentCode(code); }
    public boolean existsByStudentCodeExcludeId(String code, Long id) { return repo.existsByStudentCodeAndIdNot(code, id); }
    public long count() { return repo.count(); }
}
