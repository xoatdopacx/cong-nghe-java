package vn.edu.eaut.lab15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.service.*;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                StudentService studentService,
                                CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // Bài 6: Danh sách đăng ký học phần
    @GetMapping
    public String list(Model model) {
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "enrollments/list";
    }

    // Bài 5: Form đăng ký
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "enrollments/form";
    }

    // Bài 5: Xử lý đăng ký
    @PostMapping("/save")
    public String save(@RequestParam Long studentId,
                       @RequestParam Long courseId,
                       RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.enroll(studentId, courseId);
            redirectAttributes.addFlashAttribute("success", "Đăng ký học phần thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/enrollments";
    }

    // Bài 7: Hủy đăng ký
    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        enrollmentService.cancelEnrollment(id);
        redirectAttributes.addFlashAttribute("success", "Hủy đăng ký học phần thành công!");
        return "redirect:/enrollments";
    }
}
