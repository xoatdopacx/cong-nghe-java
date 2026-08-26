package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.LopHoc;
import vn.edu.eaut.lab10.repository.LopHocRepository;

import java.io.IOException;
import java.util.List;

/**
 * Controller cho module Lớp Học.
 * URL: /admin/lop-hoc (chỉ ADMIN truy cập)
 */
@WebServlet(name = "LopHocController", urlPatterns = "/admin/lop-hoc")
public class LopHocController extends HttpServlet {

    private final LopHocRepository lopRepo = new LopHocRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                req.setAttribute("lopHoc", new LopHoc());
                req.setAttribute("isEdit", false);
                req.getRequestDispatcher("/WEB-INF/views/admin/lophoc/form.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                lopRepo.findById(id).ifPresentOrElse(
                    lop -> {
                        req.setAttribute("lopHoc", lop);
                        req.setAttribute("isEdit", true);
                        try { req.getRequestDispatcher("/WEB-INF/views/admin/lophoc/form.jsp").forward(req, resp); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    },
                    () -> {
                        try { resp.sendRedirect(req.getContextPath() + "/admin/lop-hoc"); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    }
                );
                break;
            case "delete":
                Long delId = Long.parseLong(req.getParameter("id"));
                try {
                    lopRepo.delete(delId);
                    req.setAttribute("success", "Đã xóa lớp học thành công!");
                } catch (Exception e) {
                    req.setAttribute("error", "Không thể xóa: lớp còn sinh viên!");
                }
                listLopHoc(req, resp);
                break;
            default:
                listLopHoc(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String maLop = req.getParameter("maLop");
        String tenLop = req.getParameter("tenLop");
        String khoa = req.getParameter("khoa");

        boolean isEdit = (idStr != null && !idStr.isEmpty());
        LopHoc lop;
        if (isEdit) {
            lop = lopRepo.findById(Long.parseLong(idStr)).orElse(new LopHoc());
        } else {
            lop = new LopHoc();
        }

        lop.setMaLop(maLop);
        lop.setTenLop(tenLop);
        lop.setKhoa(khoa);

        try {
            if (isEdit) {
                lopRepo.update(lop);
                req.setAttribute("success", "Cập nhật lớp học thành công!");
            } else {
                lopRepo.save(lop);
                req.setAttribute("success", "Thêm lớp học thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        listLopHoc(req, resp);
    }

    private void listLopHoc(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<LopHoc> lopHocs;
        if (keyword != null && !keyword.trim().isEmpty()) {
            lopHocs = lopRepo.search(keyword);
        } else {
            lopHocs = lopRepo.findAll();
        }
        req.setAttribute("lopHocs", lopHocs);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/lophoc/list.jsp").forward(req, resp);
    }
}
