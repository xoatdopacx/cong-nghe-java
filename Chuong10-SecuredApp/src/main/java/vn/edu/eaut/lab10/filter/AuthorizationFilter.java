package vn.edu.eaut.lab10.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;

import java.io.IOException;

/**
 * Bài 5: AuthorizationFilter - phân quyền theo role.
 * /admin/* → chỉ ADMIN
 * /staff/* → ADMIN hoặc STAFF
 * Sai role → redirect về /error/403.jsp
 */
@WebFilter(urlPatterns = {"/admin/*", "/staff/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Lấy user từ session (đã qua AuthenticationFilter nên chắc chắn có)
        User user = (User) request.getSession().getAttribute("currentUser");

        // Nếu user null (trường hợp hiếm), redirect về login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String path = request.getRequestURI();

        // Kiểm tra quyền /admin/*
        if (path.contains("/admin/") && user.getRole() != Role.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/error/403.jsp");
            return;
        }

        // Kiểm tra quyền /staff/*
        if (path.contains("/staff/") &&
            !(user.getRole() == Role.ADMIN || user.getRole() == Role.STAFF)) {
            response.sendRedirect(request.getContextPath() + "/error/403.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}
