package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.lab13.entity.Course;

import java.util.List;

/**
 * Bài 8-9: CourseRepository kế thừa JpaRepository.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCourseNameContainingIgnoreCase(String keyword);

    boolean existsByCourseCode(String courseCode);

    boolean existsByCourseCodeAndIdNot(String courseCode, Long id);
}
