package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Student;

import java.util.List;

/**
 * Bài 4: StudentController - hiển thị danh sách sinh viên mẫu.
 */
@Controller
public class StudentController {

    /**
     * URL /students - truyền danh sách sinh viên sang Thymeleaf.
     */
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = List.of(
                new Student("20230752", "Nguyễn Văn Hùng", "hung@eaut.edu.vn", "DCCNTT 14.2"),
                new Student("20230753", "Trần Thị Mai", "mai@eaut.edu.vn", "DCCNTT 14.2"),
                new Student("20230754", "Lê Văn Nam", "nam@eaut.edu.vn", "DCCNTT 14.1"),
                new Student("20230755", "Phạm Thị Lan", "lan@eaut.edu.vn", "DCCNTT 14.1"),
                new Student("20230756", "Hoàng Minh Tuấn", "tuan@eaut.edu.vn", "DCKTPM 14.1"),
                new Student("20230757", "Vũ Thị Hương", "huong@eaut.edu.vn", "DCCNTT 14.2")
        );
        model.addAttribute("students", students);
        return "students";
    }
}
