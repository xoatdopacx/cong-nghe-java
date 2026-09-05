package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.repository.CourseRepository;

import java.util.List;

/**
 * Bài 9: Service tầng nghiệp vụ cho Course.
 */
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
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với ID = " + id));
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return courseRepository.findByCourseNameContainingIgnoreCase(keyword.trim());
    }

    public boolean existsByCourseCode(String courseCode) {
        return courseRepository.existsByCourseCode(courseCode);
    }

    public boolean existsByCourseCodeExcludeId(String courseCode, Long id) {
        return courseRepository.existsByCourseCodeAndIdNot(courseCode, id);
    }

    public long count() {
        return courseRepository.count();
    }
}
