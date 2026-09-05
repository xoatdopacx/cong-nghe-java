package vn.edu.eaut.lab13.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.service.StudentService;

/**
 * Bài 5, 6, 7: Controller CRUD sinh viên kết nối cơ sở dữ liệu.
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /** Bài 5 & 7: Danh sách + Tìm kiếm */
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.search(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalStudents", studentService.count());
        return "students/list";
    }

    /** Bài 5: Form thêm mới */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    /** Bài 6: Form sửa */
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

    /** Bài 5: Lưu (Thêm mới / Cập nhật) với Validation */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       Model model,
                       RedirectAttributes ra) {
        // Kiểm tra trùng mã sinh viên
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            boolean duplicate = (student.getId() == null)
                    ? studentService.existsByStudentCode(student.getStudentCode())
                    : studentService.existsByStudentCodeExcludeId(student.getStudentCode(), student.getId());
            if (duplicate) {
                result.rejectValue("studentCode", "error.student", "Mã sinh viên '" + student.getStudentCode() + "' đã tồn tại!");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", student.getId() != null);
            return "students/form";
        }

        boolean isUpdate = (student.getId() != null);
        studentService.save(student);
        ra.addFlashAttribute("successMessage",
                isUpdate ? "Cập nhật sinh viên '" + student.getFullName() + "' thành công!"
                         : "Thêm mới sinh viên '" + student.getFullName() + "' vào CSDL thành công!");
        return "redirect:/students";
    }

    /** Bài 5: Xóa */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            studentService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Đã xóa sinh viên khỏi cơ sở dữ liệu.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Lỗi khi xóa sinh viên: " + e.getMessage());
        }
        return "redirect:/students";
    }
}
