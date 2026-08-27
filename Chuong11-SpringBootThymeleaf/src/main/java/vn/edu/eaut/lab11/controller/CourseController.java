package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Course;

import java.util.List;

/**
 * Bài 8, 9: CourseController - hiển thị danh sách 5 khóa học mẫu.
 */
@Controller
public class CourseController {

    /**
     * URL /courses - truyền danh sách khóa học sang Thymeleaf.
     */
    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
                new Course("IT3242", "Công nghệ Java", 3),
                new Course("IT3220", "Lập trình Web", 3),
                new Course("IT1010", "Nhập môn lập trình", 4),
                new Course("IT2030", "Cơ sở dữ liệu", 3),
                new Course("IT3050", "Phát triển ứng dụng di động", 3)
        );
        model.addAttribute("courses", courses);
        return "courses";
    }
}
