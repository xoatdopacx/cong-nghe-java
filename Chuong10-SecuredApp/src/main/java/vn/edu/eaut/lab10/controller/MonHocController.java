package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.MonHoc;
import vn.edu.eaut.lab10.repository.MonHocRepository;

import java.io.IOException;
import java.util.List;

/**
 * Controller cho module Môn Học.
 * URL: /admin/mon-hoc (chỉ ADMIN truy cập)
 */
@WebServlet(name = "MonHocController", urlPatterns = "/admin/mon-hoc")
public class MonHocController extends HttpServlet {

    private final MonHocRepository mhRepo = new MonHocRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                req.setAttribute("monHoc", new MonHoc());
                req.setAttribute("isEdit", false);
                req.getRequestDispatcher("/WEB-INF/views/admin/monhoc/form.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                mhRepo.findById(id).ifPresentOrElse(
                    mh -> {
                        req.setAttribute("monHoc", mh);
                        req.setAttribute("isEdit", true);
                        try { req.getRequestDispatcher("/WEB-INF/views/admin/monhoc/form.jsp").forward(req, resp); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    },
                    () -> {
                        try { resp.sendRedirect(req.getContextPath() + "/admin/mon-hoc"); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    }
                );
                break;
            case "delete":
                Long delId = Long.parseLong(req.getParameter("id"));
                try {
                    mhRepo.delete(delId);
                    req.setAttribute("success", "Đã xóa môn học thành công!");
                } catch (Exception e) {
                    req.setAttribute("error", "Không thể xóa: môn còn điểm!");
                }
                listMonHoc(req, resp);
                break;
            default:
                listMonHoc(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String maMon = req.getParameter("maMon");
        String tenMon = req.getParameter("tenMon");
        String soTinChiStr = req.getParameter("soTinChi");

        boolean isEdit = (idStr != null && !idStr.isEmpty());
        MonHoc mh;
        if (isEdit) {
            mh = mhRepo.findById(Long.parseLong(idStr)).orElse(new MonHoc());
        } else {
            mh = new MonHoc();
        }

        mh.setMaMon(maMon);
        mh.setTenMon(tenMon);
        mh.setSoTinChi(soTinChiStr != null ? Integer.parseInt(soTinChiStr) : 0);

        try {
            if (isEdit) {
                mhRepo.update(mh);
                req.setAttribute("success", "Cập nhật môn học thành công!");
            } else {
                mhRepo.save(mh);
                req.setAttribute("success", "Thêm môn học thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        listMonHoc(req, resp);
    }

    private void listMonHoc(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<MonHoc> monHocs = mhRepo.findAll();
        req.setAttribute("monHocs", monHocs);
        req.getRequestDispatcher("/WEB-INF/views/admin/monhoc/list.jsp").forward(req, resp);
    }
}
