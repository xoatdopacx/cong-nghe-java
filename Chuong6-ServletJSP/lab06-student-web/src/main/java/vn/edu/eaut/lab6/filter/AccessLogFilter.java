package vn.edu.eaut.lab6.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bài 11: Filter ghi log truy cập ra console.
 * Ghi lại: URI, HTTP method, username, thời gian truy cập.
 */
@WebFilter(urlPatterns = "/*")
public class AccessLogFilter implements Filter {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("[AccessLogFilter] Initialized - Ghi log truy cập tất cả request");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI();
        String method = req.getMethod();
        String time = LocalDateTime.now().format(FMT);

        // Lấy username từ session (nếu đã đăng nhập)
        HttpSession session = req.getSession(false);
        String user = (session != null && session.getAttribute("username") != null)
                ? (String) session.getAttribute("username") : "anonymous";

        System.out.printf("[AccessLog] %s | %s %s | User: %s%n", time, method, uri, user);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[AccessLogFilter] Destroyed");
    }
}
