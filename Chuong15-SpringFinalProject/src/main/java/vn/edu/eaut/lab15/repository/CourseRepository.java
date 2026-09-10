package vn.edu.eaut.lab15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.lab15.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseCode(String courseCode);
}
