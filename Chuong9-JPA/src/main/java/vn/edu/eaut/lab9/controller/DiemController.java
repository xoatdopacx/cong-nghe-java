package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.entity.Diem;
import vn.edu.eaut.lab9.entity.MonHoc;
import vn.edu.eaut.lab9.entity.SinhVien;
import vn.edu.eaut.lab9.repository.DiemRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.io.IOException;
import java.util.List;

/**
 * Controller cho module Điểm.
 */
@WebServlet(name = "DiemController", urlPatterns = "/diem")
public class DiemController extends HttpServlet {

    private final DiemRepository diemRepo = new DiemRepository();
    private final SinhVienRepository svRepo = new SinhVienRepository();
    private final MonHocRepository mhRepo = new MonHocRepository();

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
                Long id = Long.parseLong(req.getParameter("id"));
                diemRepo.findById(id).ifPresentOrElse(
                    d -> {
                        try { showForm(req, resp, d); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    },
                    () -> {
                        try { resp.sendRedirect(req.getContextPath() + "/diem"); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    }
                );
                break;
            case "delete":
                Long delId = Long.parseLong(req.getParameter("id"));
                diemRepo.delete(delId);
                req.setAttribute("success", "Đã xóa điểm thành công!");
                listDiem(req, resp);
                break;
            default:
                listDiem(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String svIdStr = req.getParameter("sinhVienId");
        String mhIdStr = req.getParameter("monHocId");
        String diemSoStr = req.getParameter("diemSo");
        String ghiChu = req.getParameter("ghiChu");

        boolean isEdit = (idStr != null && !idStr.isEmpty());
        Diem diem;
        if (isEdit) {
            diem = diemRepo.findById(Long.parseLong(idStr)).orElse(new Diem());
        } else {
            diem = new Diem();
        }

        // Set sinh viên
        if (svIdStr != null && !svIdStr.isEmpty()) {
            svRepo.findById(Long.parseLong(svIdStr)).ifPresent(diem::setSinhVien);
        }
        // Set môn học
        if (mhIdStr != null && !mhIdStr.isEmpty()) {
            mhRepo.findById(Long.parseLong(mhIdStr)).ifPresent(diem::setMonHoc);
        }
        // Set điểm
        if (diemSoStr != null && !diemSoStr.isEmpty()) {
            diem.setDiemSo(Double.parseDouble(diemSoStr));
        }
        diem.setGhiChu(ghiChu);

        try {
            // Kiểm tra trùng SV + Môn (chỉ khi thêm mới)
            if (!isEdit && diem.getSinhVien() != null && diem.getMonHoc() != null) {
                Diem existing = diemRepo.findBySinhVienAndMonHoc(
                    diem.getSinhVien().getId(), diem.getMonHoc().getId());
                if (existing != null) {
                    req.setAttribute("error", "Sinh viên đã có điểm môn này!");
                    showForm(req, resp, diem);
                    return;
                }
            }

            if (isEdit) {
                diemRepo.update(diem);
                req.setAttribute("success", "Cập nhật điểm thành công!");
            } else {
                diemRepo.save(diem);
                req.setAttribute("success", "Thêm điểm thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        listDiem(req, resp);
    }

    private void listDiem(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Diem> diems = diemRepo.findAll();
        req.setAttribute("diems", diems);
        req.getRequestDispatcher("/WEB-INF/views/diem/list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Diem diem)
            throws ServletException, IOException {
        List<SinhVien> sinhViens = svRepo.findAll();
        List<MonHoc> monHocs = mhRepo.findAll();
        req.setAttribute("sinhViens", sinhViens);
        req.setAttribute("monHocs", monHocs);
        req.setAttribute("diem", diem != null ? diem : new Diem());
        req.setAttribute("isEdit", diem != null && diem.getId() != null);
        req.getRequestDispatcher("/WEB-INF/views/diem/form.jsp").forward(req, resp);
    }
}
