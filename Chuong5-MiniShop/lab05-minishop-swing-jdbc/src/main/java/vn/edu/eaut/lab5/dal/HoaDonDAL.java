package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAL {

    public int insertHoaDon(int maKh, String username, List<ChiTietHoaDon> chiTietList) throws SQLException {
        String sqlHoaDon = "INSERT INTO hoa_don(ngay_lap, ma_kh, tong_tien, username) VALUES (?, ?, ?, ?)";
        String sqlChiTiet = "INSERT INTO chi_tiet_hoa_don(ma_hd, ma_sp, so_luong, don_gia, thanh_tien) VALUES (?, ?, ?, ?, ?)";
        String sqlTruKho = "UPDATE san_pham SET so_luong = so_luong - ? WHERE ma_sp = ? AND so_luong >= ?";

        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);

            BigDecimal tongTien = BigDecimal.ZERO;
            for (ChiTietHoaDon ct : chiTietList) {
                tongTien = tongTien.add(ct.getThanhTien());
            }

            int maHd;
            try (PreparedStatement ps = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setInt(2, maKh);
                ps.setBigDecimal(3, tongTien);
                if (username != null && !username.trim().isEmpty()) {
                    ps.setString(4, username);
                } else {
                    ps.setNull(4, Types.VARCHAR);
                }
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        maHd = rs.getInt(1);
                    } else {
                        throw new SQLException("Không lấy được mã hóa đơn tự sinh");
                    }
                }
            }

            // Insert chi_tiet_hoa_don + Decrement stock
            try (PreparedStatement psCt = conn.prepareStatement(sqlChiTiet);
                 PreparedStatement psKho = conn.prepareStatement(sqlTruKho)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    // Chi tiết
                    psCt.setInt(1, maHd);
                    psCt.setInt(2, ct.getMaSp());
                    psCt.setInt(3, ct.getSoLuong());
                    psCt.setBigDecimal(4, ct.getDonGia());
                    psCt.setBigDecimal(5, ct.getThanhTien());
                    psCt.addBatch();

                    // Trừ kho bài 7
                    psKho.setInt(1, ct.getSoLuong());
                    psKho.setInt(2, ct.getMaSp());
                    psKho.setInt(3, ct.getSoLuong());
                    int updatedRows = psKho.executeUpdate();
                    if (updatedRows == 0) {
                        throw new SQLException("Sản phẩm mã " + ct.getMaSp() + " không đủ số lượng trong kho!");
                    }
                }
                psCt.executeBatch();
            }

            conn.commit();
            return maHd;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<HoaDon> findAll() throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, hd.tong_tien, hd.username " +
                     "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                     "ORDER BY hd.ma_hd DESC";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("ma_hd"),
                    rs.getDate("ngay_lap").toLocalDate(),
                    rs.getInt("ma_kh"),
                    rs.getBigDecimal("tong_tien"),
                    rs.getString("username")
                );
                hd.setTenKhachHang(rs.getString("ten_kh"));
                list.add(hd);
            }
        }
        return list;
    }

    public HoaDon findById(int maHd) throws SQLException {
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, hd.tong_tien, hd.username " +
                     "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh WHERE hd.ma_hd = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHd);
            try (ResultSet rs = ps.executeQuery()) {
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
        }
        return null;
    }

    public List<HoaDon> findByDateRange(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, hd.tong_tien, hd.username " +
                     "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                     "WHERE hd.ngay_lap BETWEEN ? AND ? ORDER BY hd.ma_hd DESC";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon hd = new HoaDon(
                        rs.getInt("ma_hd"),
                        rs.getDate("ngay_lap").toLocalDate(),
                        rs.getInt("ma_kh"),
                        rs.getBigDecimal("tong_tien"),
                        rs.getString("username")
                    );
                    hd.setTenKhachHang(rs.getString("ten_kh"));
                    list.add(hd);
                }
            }
        }
        return list;
    }
}
