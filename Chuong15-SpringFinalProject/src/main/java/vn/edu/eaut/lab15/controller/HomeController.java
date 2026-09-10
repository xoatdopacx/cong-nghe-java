package vn.edu.eaut.lab15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab15.service.*;

@Controller
public class HomeController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public HomeController(StudentService studentService, CourseService courseService,
                          EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalStudents", studentService.count());
        model.addAttribute("totalCourses", courseService.count());
        model.addAttribute("totalEnrollments", enrollmentService.count());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Bài 9: Dashboard thống kê
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalStudents", studentService.count());
        model.addAttribute("totalCourses", courseService.count());
        model.addAttribute("totalEnrollments", enrollmentService.count());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "dashboard";
    }
}
