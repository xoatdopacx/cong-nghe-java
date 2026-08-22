package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.entity.SanPham;
import vn.edu.eaut.lab9.repository.SanPhamRepository;

import java.io.IOException;
import java.util.List;

/**
 * Bài 13: Controller cho module Sản Phẩm.
 */
@WebServlet(name = "SanPhamController", urlPatterns = "/san-pham")
public class SanPhamController extends HttpServlet {

    private final SanPhamRepository spRepo = new SanPhamRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                req.setAttribute("sanPham", new SanPham());
                req.setAttribute("isEdit", false);
                req.getRequestDispatcher("/WEB-INF/views/sanpham/form.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = Long.parseLong(req.getParameter("id"));
                spRepo.findById(id).ifPresentOrElse(
                    sp -> {
                        req.setAttribute("sanPham", sp);
                        req.setAttribute("isEdit", true);
                        try { req.getRequestDispatcher("/WEB-INF/views/sanpham/form.jsp").forward(req, resp); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    },
                    () -> {
                        try { resp.sendRedirect(req.getContextPath() + "/san-pham"); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    }
                );
                break;
            case "delete":
                Long delId = Long.parseLong(req.getParameter("id"));
                spRepo.delete(delId);
                req.setAttribute("success", "Đã xóa sản phẩm thành công!");
                listSanPham(req, resp);
                break;
            default:
                listSanPham(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String maSP = req.getParameter("maSP");
        String tenSP = req.getParameter("tenSP");
        String donGiaStr = req.getParameter("donGia");
        String soLuongStr = req.getParameter("soLuong");
        String moTa = req.getParameter("moTa");
        String danhMuc = req.getParameter("danhMuc");

        boolean isEdit = (idStr != null && !idStr.isEmpty());
        SanPham sp;
        if (isEdit) {
            sp = spRepo.findById(Long.parseLong(idStr)).orElse(new SanPham());
        } else {
            sp = new SanPham();
        }

        sp.setMaSP(maSP);
        sp.setTenSP(tenSP);
        sp.setDonGia(donGiaStr != null && !donGiaStr.isEmpty() ? Double.parseDouble(donGiaStr) : 0.0);
        sp.setSoLuong(soLuongStr != null && !soLuongStr.isEmpty() ? Integer.parseInt(soLuongStr) : 0);
        sp.setMoTa(moTa);
        sp.setDanhMuc(danhMuc);

        try {
            if (isEdit) {
                spRepo.update(sp);
                req.setAttribute("success", "Cập nhật sản phẩm thành công!");
            } else {
                spRepo.save(sp);
                req.setAttribute("success", "Thêm sản phẩm thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        listSanPham(req, resp);
    }

    private void listSanPham(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<SanPham> sanPhams;
        if (keyword != null && !keyword.trim().isEmpty()) {
            sanPhams = spRepo.search(keyword);
        } else {
            sanPhams = spRepo.findAll();
        }
        req.setAttribute("sanPhams", sanPhams);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/sanpham/list.jsp").forward(req, resp);
    }
}
