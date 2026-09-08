package vn.edu.eaut.lab14.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab14.entity.Course;
import vn.edu.eaut.lab14.repository.CourseRepository;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repo;
    public CourseService(CourseRepository repo) { this.repo = repo; }

    public List<Course> findAll() { return repo.findAll(); }
    public Course findById(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy môn học ID = " + id)); }
    public Course save(Course c) { return repo.save(c); }
    public void deleteById(Long id) { repo.deleteById(id); }
    public long count() { return repo.count(); }
}
