package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAL {

    public List<ChiTietHoaDon> findByHoaDonId(int maHd) throws SQLException {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT ct.ma_hd, ct.ma_sp, sp.ten_sp, ct.so_luong, ct.don_gia, ct.thanh_tien " +
                     "FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                     "WHERE ct.ma_hd = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setMaHd(rs.getInt("ma_hd"));
                    ct.setMaSp(rs.getInt("ma_sp"));
                    ct.setTenSp(rs.getString("ten_sp"));
                    ct.setSoLuong(rs.getInt("so_luong"));
                    ct.setDonGia(rs.getBigDecimal("don_gia"));
                    ct.setThanhTien(rs.getBigDecimal("thanh_tien"));
                    list.add(ct);
                }
            }
        }
        return list;
    }
}
