package vn.edu.eaut.lab10.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Bài 4: AuthenticationFilter - bảo vệ URL yêu cầu đăng nhập.
 * Kiểm tra session có currentUser hay chưa.
 * Nếu chưa đăng nhập → redirect về login.jsp.
 */
@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/user/*", "/dashboard"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("currentUser") != null);

        if (!loggedIn) {
            // Lưu URL đang cố truy cập để redirect lại sau khi login
            String requestedURL = request.getRequestURI();
            if (request.getQueryString() != null) {
                requestedURL += "?" + request.getQueryString();
            }
            // Có thể lưu vào session nếu cần redirect sau login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Đã đăng nhập, cho phép tiếp tục
        // Không cache để nút Back không hiển thị trang cũ sau logout
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        chain.doFilter(request, response);
    }
}
