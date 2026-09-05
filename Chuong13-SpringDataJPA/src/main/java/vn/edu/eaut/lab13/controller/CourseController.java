package vn.edu.eaut.lab13.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.service.CourseService;

/**
 * Bài 9: Controller CRUD cho Course (Khóa học / Môn học).
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("courses", courseService.search(keyword));
        model.addAttribute("keyword", keyword);
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
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("course", courseService.findById(id));
            model.addAttribute("isEdit", true);
            return "courses/form";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/courses";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("course") Course course,
                       BindingResult result,
                       Model model,
                       RedirectAttributes ra) {
        if (course.getCourseCode() != null && !course.getCourseCode().trim().isEmpty()) {
            boolean dup = (course.getId() == null)
                    ? courseService.existsByCourseCode(course.getCourseCode())
                    : courseService.existsByCourseCodeExcludeId(course.getCourseCode(), course.getId());
            if (dup) {
                result.rejectValue("courseCode", "error.course", "Mã môn học đã tồn tại!");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", course.getId() != null);
            return "courses/form";
        }

        courseService.save(course);
        ra.addFlashAttribute("successMessage", "Lưu môn học '" + course.getCourseName() + "' thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            courseService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Đã xóa môn học khỏi cơ sở dữ liệu.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi xóa: " + e.getMessage());
        }
        return "redirect:/courses";
    }
}
