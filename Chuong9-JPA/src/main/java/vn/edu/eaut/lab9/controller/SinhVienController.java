package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.entity.LopHoc;
import vn.edu.eaut.lab9.entity.SinhVien;
import vn.edu.eaut.lab9.repository.LopHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;
import vn.edu.eaut.lab9.service.SinhVienService;

import java.io.IOException;
import java.util.List;

/**
 * Bài 5: Servlet Controller cho module Sinh Viên.
 * Xử lý CRUD + search + phân trang.
 */
@WebServlet(name = "SinhVienController", urlPatterns = "/sinh-vien")
public class SinhVienController extends HttpServlet {

    private final SinhVienRepository svRepo = new SinhVienRepository();
    private final LopHocRepository lopRepo = new LopHocRepository();
    private final SinhVienService svService = new SinhVienService();

    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                showForm(req, resp, null);
                break;
            case "edit":
                Long editId = Long.parseLong(req.getParameter("id"));
                svRepo.findById(editId).ifPresentOrElse(
                    sv -> {
                        try { showForm(req, resp, sv); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    },
                    () -> {
                        try {
                            req.setAttribute("error", "Không tìm thấy sinh viên ID=" + editId);
                            listSinhVien(req, resp);
                        } catch (Exception e) { throw new RuntimeException(e); }
                    }
                );
                break;
            case "delete":
                Long delId = Long.parseLong(req.getParameter("id"));
                svRepo.delete(delId);
                req.setAttribute("success", "Đã xóa sinh viên thành công!");
                listSinhVien(req, resp);
                break;
            default:
                listSinhVien(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            saveSinhVien(req, resp);
        } else {
            listSinhVien(req, resp);
        }
    }

    private void listSinhVien(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        String pageStr = req.getParameter("page");
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;

        List<SinhVien> sinhViens;
        long total;

        if (keyword != null && !keyword.trim().isEmpty()) {
            sinhViens = svRepo.searchPage(keyword, page, PAGE_SIZE);
            total = svRepo.countSearch(keyword);
        } else {
            sinhViens = svRepo.findPage(page, PAGE_SIZE);
            total = svRepo.count();
        }

        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

        req.setAttribute("sinhViens", sinhViens);
        req.setAttribute("keyword", keyword);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalRecords", total);
        req.getRequestDispatcher("/WEB-INF/views/sinhvien/list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, SinhVien sv)
            throws ServletException, IOException {
        List<LopHoc> lopHocs = lopRepo.findAll();
        req.setAttribute("lopHocs", lopHocs);
        req.setAttribute("sinhVien", sv != null ? sv : new SinhVien());
        req.setAttribute("isEdit", sv != null && sv.getId() != null);
        req.getRequestDispatcher("/WEB-INF/views/sinhvien/form.jsp").forward(req, resp);
    }

    private void saveSinhVien(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String maSV = req.getParameter("maSV");
        String hoTen = req.getParameter("hoTen");
        String email = req.getParameter("email");
        String dienThoai = req.getParameter("dienThoai");
        String diaChi = req.getParameter("diaChi");
        String lopHocIdStr = req.getParameter("lopHocId");

        boolean isEdit = (idStr != null && !idStr.isEmpty());

        SinhVien sv;
        if (isEdit) {
            sv = svRepo.findById(Long.parseLong(idStr)).orElse(new SinhVien());
        } else {
            sv = new SinhVien();
        }

        sv.setMaSV(maSV);
        sv.setHoTen(hoTen);
        sv.setEmail(email);
        sv.setDienThoai(dienThoai);
        sv.setDiaChi(diaChi);

        if (lopHocIdStr != null && !lopHocIdStr.isEmpty()) {
            lopRepo.findById(Long.parseLong(lopHocIdStr)).ifPresent(sv::setLopHoc);
        } else {
            sv.setLopHoc(null);
        }

        // Validate
        List<String> errors = svService.validate(sv, !isEdit);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            showForm(req, resp, sv);
            return;
        }

        try {
            if (isEdit) {
                svRepo.update(sv);
                req.setAttribute("success", "Cập nhật sinh viên thành công!");
            } else {
                svRepo.save(sv);
                req.setAttribute("success", "Thêm sinh viên thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        listSinhVien(req, resp);
    }
}
