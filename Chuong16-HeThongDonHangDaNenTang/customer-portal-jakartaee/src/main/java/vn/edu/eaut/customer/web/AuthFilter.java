package vn.edu.eaut.customer.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.customer.dao.UserDAO.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/cart/*", "/checkout", "/orders/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null) {
            String target = req.getRequestURI();
            if (req.getQueryString() != null) {
                target += "?" + req.getQueryString();
            }
            req.getSession(true).setAttribute("redirectAfterLogin", target);
            res.sendRedirect(req.getContextPath() + "/login?error=" +
                    java.net.URLEncoder.encode("Vui lòng đăng nhập để tiếp tục!", java.nio.charset.StandardCharsets.UTF_8));
            return;
        }

        chain.doFilter(request, response);
    }
}
