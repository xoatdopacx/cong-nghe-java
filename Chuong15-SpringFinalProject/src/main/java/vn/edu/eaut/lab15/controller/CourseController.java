package vn.edu.eaut.lab15.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        return "courses/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        return "courses/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Course course, BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "courses/form";
        }
        if (course.getId() == null && courseService.existsByCourseCode(course.getCourseCode())) {
            result.rejectValue("courseCode", "error.course", "Mã môn học đã tồn tại!");
            return "courses/form";
        }
        courseService.save(course);
        redirectAttributes.addFlashAttribute("success",
                course.getId() != null ? "Cập nhật khóa học thành công!" : "Thêm khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Xóa khóa học thành công!");
        return "redirect:/courses";
    }
}
