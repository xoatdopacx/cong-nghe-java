package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Bài 2, 5, 6: HomeController - trang chủ, giới thiệu, liên hệ.
 */
@Controller
public class HomeController {

    /**
     * Bài 2: Trang chủ - URL /
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hệ thống quản lý sinh viên");
        model.addAttribute("message", "Chào mừng đến với Spring Boot");
        return "index";
    }

    /**
     * Bài 5: Trang giới thiệu - URL /about
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("course", "Công nghệ Java");
        model.addAttribute("chapter", "Chương 4 - Spring Framework");
        return "about";
    }

    /**
     * Bài 6: Trang liên hệ - URL /contact
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("department", "Bộ môn Công nghệ Phần mềm");
        model.addAttribute("faculty", "Khoa Công nghệ Thông tin");
        model.addAttribute("university", "Trường Đại học Công nghệ Đông Á (EAUT)");
        model.addAttribute("address", "Trịnh Văn Bô, Phương Canh, Nam Từ Liêm, Hà Nội");
        model.addAttribute("phone", "(024) 6291 7666");
        model.addAttribute("email", "cntt@eaut.edu.vn");
        model.addAttribute("website", "https://eaut.edu.vn");
        return "contact";
    }
}
