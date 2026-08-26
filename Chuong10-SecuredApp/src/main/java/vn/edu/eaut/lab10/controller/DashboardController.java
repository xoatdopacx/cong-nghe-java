package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.*;

import java.io.IOException;

/**
 * Dashboard controller - trang chủ hiển thị thống kê.
 * Yêu cầu đăng nhập (bảo vệ bởi AuthenticationFilter).
 */
@WebServlet(name = "DashboardController", urlPatterns = {"/dashboard"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Lấy thông tin user hiện tại
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        req.setAttribute("currentUser", currentUser);

        // Thống kê
        long totalSV = new SinhVienRepository().count();
        long totalLop = new LopHocRepository().count();
        long totalMH = new MonHocRepository().count();
        long totalDiem = new DiemRepository().count();
        long totalSP = new SanPhamRepository().count();
        long totalND = new UserRepository().count();

        req.setAttribute("totalSV", totalSV);
        req.setAttribute("totalLop", totalLop);
        req.setAttribute("totalMH", totalMH);
        req.setAttribute("totalDiem", totalDiem);
        req.setAttribute("totalSP", totalSP);
        req.setAttribute("totalND", totalND);

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}
