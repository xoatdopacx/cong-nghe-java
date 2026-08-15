package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class ThongKeDAL {

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) AS doanh_thu FROM hoa_don WHERE ngay_lap BETWEEN ? AND ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("doanh_thu");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public HoaDon findHighestValueInvoice() throws SQLException {
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, hd.tong_tien, hd.username " +
                     "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                     "ORDER BY hd.tong_tien DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("ma_hd"),
                    rs.getDate("ngay_lap").toLocalDate(),
                    rs.getInt("ma_kh"),
                    rs.getBigDecimal("tong_tien"),
                    rs.getString("username")
                );
                hd.setTenKhachHang(rs.getString("ten_kh"));
                return hd;
            }
        }
        return null;
    }

    public String findTopSellingProduct() throws SQLException {
        String sql = "SELECT sp.ma_sp, sp.ten_sp, SUM(ct.so_luong) AS tong_so_luong " +
                     "FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                     "GROUP BY sp.ma_sp, sp.ten_sp ORDER BY tong_so_luong DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("ten_sp") + " (Đã bán: " + rs.getInt("tong_so_luong") + " sản phẩm)";
            }
        }
        return "Chưa có dữ liệu bán hàng";
    }
}
