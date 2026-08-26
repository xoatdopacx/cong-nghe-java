package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.AuthService;
import vn.edu.eaut.lab10.repository.UserRepository;

import java.io.IOException;

/**
 * Bài 8, 9: ProfileController - xem/cập nhật hồ sơ cá nhân và đổi mật khẩu.
 * URL: /user/profile
 */
@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("currentUser");
        // Lấy dữ liệu mới nhất từ DB
        User freshUser = userRepo.findById(user.getId()).orElse(user);
        req.setAttribute("profileUser", freshUser);
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        String action = req.getParameter("action");

        if ("updateProfile".equals(action)) {
            handleUpdateProfile(req, resp, currentUser);
        } else if ("changePassword".equals(action)) {
            handleChangePassword(req, resp, currentUser);
        } else {
            doGet(req, resp);
        }
    }

    private void handleUpdateProfile(HttpServletRequest req, HttpServletResponse resp, User currentUser)
            throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");

        String error = authService.updateProfile(currentUser.getId(), fullName, email);

        if (error != null) {
            req.setAttribute("profileError", error);
        } else {
            // Cập nhật session
            User updatedUser = userRepo.findById(currentUser.getId()).orElse(currentUser);
            req.getSession().setAttribute("currentUser", updatedUser);
            req.setAttribute("profileSuccess", "Cập nhật hồ sơ thành công!");
        }

        User freshUser = userRepo.findById(currentUser.getId()).orElse(currentUser);
        req.setAttribute("profileUser", freshUser);
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    private void handleChangePassword(HttpServletRequest req, HttpServletResponse resp, User currentUser)
            throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        String error = authService.changePassword(currentUser.getId(), oldPassword, newPassword, confirmPassword);

        if (error != null) {
            req.setAttribute("passwordError", error);
        } else {
            // Cập nhật session
            User updatedUser = userRepo.findById(currentUser.getId()).orElse(currentUser);
            req.getSession().setAttribute("currentUser", updatedUser);
            req.setAttribute("passwordSuccess", "Đổi mật khẩu thành công!");
        }

        User freshUser = userRepo.findById(currentUser.getId()).orElse(currentUser);
        req.setAttribute("profileUser", freshUser);
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }
}
