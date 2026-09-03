package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

import java.util.List;

/**
 * Controller chính xử lý các request CRUD sinh viên trong Spring MVC.
 * Bài 3, 4, 5, 6, 7, 8, 9, 10.
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Bài 3 & 9: Danh sách sinh viên và tìm kiếm theo từ khóa.
     */
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Student> students = studentService.search(keyword);
        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalStudents", studentService.count());
        return "students/list";
    }

    /**
     * Bài 4: Hiển thị form thêm sinh viên mới.
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    /**
     * Bài 6: Xem chi tiết sinh viên theo ID.
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    return "students/detail";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên với ID = " + id);
                    return "redirect:/students";
                });
    }

    /**
     * Bài 7: Hiển thị form sửa thông tin sinh viên theo ID.
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    model.addAttribute("isEdit", true);
                    return "students/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên để chỉnh sửa.");
                    return "redirect:/students";
                });
    }

    /**
     * Bài 4, 5 & 10: Xử lý lưu sinh viên (thêm mới hoặc cập nhật) có validation.
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       @RequestParam(name = "isEdit", defaultValue = "false") boolean isEdit,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        // Bài 10: Validation tùy chỉnh - Kiểm tra trùng mã sinh viên
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            if (studentService.existsByStudentCode(student.getStudentCode(), student.getId())) {
                result.rejectValue("studentCode", "error.student", "Mã sinh viên '" + student.getStudentCode() + "' đã tồn tại trên hệ thống!");
            }
        }

        // Bài 5: Nếu có lỗi validation, trả về form cùng danh sách thông báo lỗi
        if (result.hasErrors()) {
            model.addAttribute("isEdit", student.getId() != null);
            return "students/form";
        }

        boolean updating = (student.getId() != null);
        studentService.save(student);

        if (updating) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin sinh viên '" + student.getFullName() + "' thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới sinh viên '" + student.getFullName() + "' thành công!");
        }

        return "redirect:/students";
    }

    /**
     * Bài 8: Xóa sinh viên khỏi danh sách theo ID.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = studentService.deleteById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sinh viên thành công khỏi danh sách.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên để xóa.");
        }
        return "redirect:/students";
    }
}
