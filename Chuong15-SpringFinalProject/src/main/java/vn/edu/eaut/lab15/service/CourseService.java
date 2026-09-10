package vn.edu.eaut.lab15.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với ID: " + id));
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public boolean existsByCourseCode(String courseCode) {
        return courseRepository.existsByCourseCode(courseCode);
    }

    public long count() {
        return courseRepository.count();
    }
}
