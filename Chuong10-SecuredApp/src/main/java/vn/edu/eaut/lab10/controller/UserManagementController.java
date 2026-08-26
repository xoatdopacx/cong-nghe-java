package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

import java.io.IOException;
import java.util.List;

/**
 * Bài 7: UserManagementController - CRUD tài khoản cho ADMIN.
 * URL: /admin/users
 */
@WebServlet(name = "UserManagementController", urlPatterns = "/admin/users")
public class UserManagementController extends HttpServlet {

    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                req.setAttribute("editUser", new User());
                req.setAttribute("roles", Role.values());
                req.setAttribute("isEdit", false);
                req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
                break;
            case "edit":
                Integer id = Integer.parseInt(req.getParameter("id"));
                userRepo.findById(id).ifPresentOrElse(
                    user -> {
                        req.setAttribute("editUser", user);
                        req.setAttribute("roles", Role.values());
                        req.setAttribute("isEdit", true);
                        try { req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    },
                    () -> {
                        try { resp.sendRedirect(req.getContextPath() + "/admin/users"); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    }
                );
                break;
            case "toggle":
                Integer toggleId = Integer.parseInt(req.getParameter("id"));
                userRepo.findById(toggleId).ifPresent(user -> {
                    user.setActive(!user.isActive());
                    userRepo.update(user);
                });
                req.setAttribute("success", "Đã cập nhật trạng thái tài khoản!");
                listUsers(req, resp);
                break;
            case "delete":
                Integer delId = Integer.parseInt(req.getParameter("id"));
                try {
                    userRepo.deleteById(delId);
                    req.setAttribute("success", "Đã xóa tài khoản!");
                } catch (Exception e) {
                    req.setAttribute("error", "Không thể xóa tài khoản: " + e.getMessage());
                }
                listUsers(req, resp);
                break;
            default:
                listUsers(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String roleStr = req.getParameter("role");

        boolean isEdit = (idStr != null && !idStr.isEmpty());
        User user;

        if (isEdit) {
            user = userRepo.findById(Integer.parseInt(idStr)).orElse(new User());
        } else {
            user = new User();
        }

        // Validate
        if (email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "Email không được để trống");
            req.setAttribute("editUser", user);
            req.setAttribute("roles", Role.values());
            req.setAttribute("isEdit", isEdit);
            req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
            return;
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            req.setAttribute("error", "Họ tên không được để trống");
            req.setAttribute("editUser", user);
            req.setAttribute("roles", Role.values());
            req.setAttribute("isEdit", isEdit);
            req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra email trùng
        User existingUser = userRepo.findByEmail(email.trim());
        if (existingUser != null && (!isEdit || !existingUser.getId().equals(user.getId()))) {
            req.setAttribute("error", "Email đã được sử dụng");
            req.setAttribute("editUser", user);
            req.setAttribute("roles", Role.values());
            req.setAttribute("isEdit", isEdit);
            req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
            return;
        }

        user.setEmail(email.trim());
        user.setFullName(fullName.trim());
        user.setRole(Role.valueOf(roleStr));

        if (!isEdit) {
            // Tài khoản mới cần mật khẩu
            if (password == null || password.trim().isEmpty()) {
                req.setAttribute("error", "Mật khẩu không được để trống");
                req.setAttribute("editUser", user);
                req.setAttribute("roles", Role.values());
                req.setAttribute("isEdit", false);
                req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
                return;
            }
            user.setPassword(password);
        } else if (password != null && !password.trim().isEmpty()) {
            // Nếu admin nhập mật khẩu mới khi sửa
            user.setPassword(password);
        }

        try {
            if (isEdit) {
                userRepo.update(user);
                req.setAttribute("success", "Cập nhật tài khoản thành công!");
            } else {
                userRepo.save(user);
                req.setAttribute("success", "Thêm tài khoản thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        listUsers(req, resp);
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<User> users;
        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userRepo.search(keyword);
        } else {
            users = userRepo.findAll();
        }
        req.setAttribute("users", users);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/users/list.jsp").forward(req, resp);
    }
}
