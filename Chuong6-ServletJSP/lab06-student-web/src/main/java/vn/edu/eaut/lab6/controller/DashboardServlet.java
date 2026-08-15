package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.Map;

/**
 * Bài 10: Trang Dashboard sau đăng nhập.
 * Hiển thị: tên người dùng, tổng sinh viên, thống kê theo lớp, thời gian đăng nhập.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Dữ liệu cho Dashboard
        request.setAttribute("totalStudents", StudentStore.count());
        Map<String, Long> classStats = StudentStore.countByClassName();
        request.setAttribute("classStats", classStats);
        request.setAttribute("role", session.getAttribute("role"));

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
