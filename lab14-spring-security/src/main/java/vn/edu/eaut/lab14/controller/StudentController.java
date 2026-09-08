package vn.edu.eaut.lab14.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab14.entity.Student;
import vn.edu.eaut.lab14.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) { this.studentService = studentService; }

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.search(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalStudents", studentService.count());
        return "students/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("student", studentService.findById(id));
            model.addAttribute("isEdit", true);
            return "students/form";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/students";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model, RedirectAttributes ra) {
        if (student.getStudentCode() != null && !student.getStudentCode().isBlank()) {
            boolean dup = (student.getId() == null) ? studentService.existsByStudentCode(student.getStudentCode()) : studentService.existsByStudentCodeExcludeId(student.getStudentCode(), student.getId());
            if (dup) result.rejectValue("studentCode", "error.student", "Mã sinh viên đã tồn tại!");
        }
        if (result.hasErrors()) { model.addAttribute("isEdit", student.getId() != null); return "students/form"; }
        studentService.save(student);
        ra.addFlashAttribute("successMessage", "Lưu sinh viên '" + student.getFullName() + "' thành công!");
        return "redirect:/students";
    }

    /** Bài 9: Chỉ ADMIN được xóa (đã bảo vệ trong SecurityConfig) */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        studentService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Đã xóa sinh viên.");
        return "redirect:/students";
    }
}
