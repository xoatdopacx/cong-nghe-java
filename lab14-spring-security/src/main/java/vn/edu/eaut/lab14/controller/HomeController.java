package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab14.service.StudentService;
import vn.edu.eaut.lab14.service.CourseService;

@Controller
public class HomeController {

    private final StudentService studentService;
    private final CourseService courseService;

    public HomeController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalStudents", studentService.count());
        model.addAttribute("totalCourses", courseService.count());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
