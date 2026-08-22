package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.repository.*;

import java.io.IOException;

/**
 * Dashboard controller - trang chủ hiển thị thống kê.
 */
@WebServlet(name = "DashboardController", urlPatterns = {"", "/dashboard"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        long totalSV = new SinhVienRepository().count();
        long totalLop = new LopHocRepository().count();
        long totalMH = new MonHocRepository().count();
        long totalDiem = new DiemRepository().count();
        long totalSP = new SanPhamRepository().count();
        long totalND = new NguoiDungRepository().count();

        req.setAttribute("totalSV", totalSV);
        req.setAttribute("totalLop", totalLop);
        req.setAttribute("totalMH", totalMH);
        req.setAttribute("totalDiem", totalDiem);
        req.setAttribute("totalSP", totalSP);
        req.setAttribute("totalND", totalND);

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}
