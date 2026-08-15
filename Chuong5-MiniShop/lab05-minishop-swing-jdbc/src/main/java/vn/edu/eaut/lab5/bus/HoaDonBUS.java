package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ChiTietHoaDonDAL;
import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.dal.SanPhamDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAL hoaDonDAL = new HoaDonDAL();
    private final ChiTietHoaDonDAL chiTietHoaDonDAL = new ChiTietHoaDonDAL();

    public int taoHoaDon(int maKh, String username, List<ChiTietHoaDon> chiTietList) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui lòng chọn khách hàng lập hóa đơn");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Hóa đơn phải có ít nhất 1 sản phẩm");
        }

        // Validate stock for each item
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Số lượng sản phẩm " + ct.getTenSp() + " phải lớn hơn 0");
            }
        }

        return hoaDonDAL.insertHoaDon(maKh, username, chiTietList);
    }

    public List<HoaDon> findAll() throws SQLException {
        return hoaDonDAL.findAll();
    }

    public HoaDon findById(int maHd) throws SQLException {
        HoaDon hd = hoaDonDAL.findById(maHd);
        if (hd != null) {
            hd.setChiTietList(chiTietHoaDonDAL.findByHoaDonId(maHd));
        }
        return hd;
    }

    public List<HoaDon> findByDateRange(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay == null || denNgay == null) {
            throw new IllegalArgumentException("Khoảng ngày lọc không được để trống");
        }
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Từ ngày phải trước hoặc bằng Đến ngày");
        }
        return hoaDonDAL.findByDateRange(tuNgay, denNgay);
    }
}
