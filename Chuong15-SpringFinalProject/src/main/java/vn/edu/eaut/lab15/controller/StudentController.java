package vn.edu.eaut.lab15.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.service.StudentService;
import vn.edu.eaut.lab15.service.EnrollmentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public StudentController(StudentService studentService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "students/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Student student, BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/form";
        }
        // Kiểm tra trùng mã sinh viên khi thêm mới
        if (student.getId() == null && studentService.existsByStudentCode(student.getStudentCode())) {
            result.rejectValue("studentCode", "error.student", "Mã sinh viên đã tồn tại!");
            return "students/form";
        }
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success",
                student.getId() != null ? "Cập nhật sinh viên thành công!" : "Thêm sinh viên thành công!");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Xóa sinh viên thành công!");
        return "redirect:/students";
    }

    // Bài 8: Xem danh sách môn học mà một sinh viên đã đăng ký
    @GetMapping("/{id}/enrollments")
    public String studentEnrollments(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("enrollments", enrollmentService.findByStudentId(id));
        return "students/enrollments";
    }
}
