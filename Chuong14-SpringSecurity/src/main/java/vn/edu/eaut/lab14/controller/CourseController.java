package vn.edu.eaut.lab14.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab14.entity.Course;
import vn.edu.eaut.lab14.service.CourseService;

/**
 * Bài 6: CourseController - chỉ ADMIN truy cập (đã cấu hình trong SecurityConfig).
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    public CourseController(CourseService courseService) { this.courseService = courseService; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("totalCourses", courseService.count());
        return "courses/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("isEdit", false);
        return "courses/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("isEdit", true);
        return "courses/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("course") Course course, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) { model.addAttribute("isEdit", course.getId() != null); return "courses/form"; }
        courseService.save(course);
        ra.addFlashAttribute("successMessage", "Lưu môn học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        courseService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Đã xóa môn học.");
        return "redirect:/courses";
    }
}
