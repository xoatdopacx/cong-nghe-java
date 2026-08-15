package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.SanPham;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {

    public List<SanPham> findAll() throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm " +
                     "FROM san_pham sp LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm " +
                     "ORDER BY sp.ma_sp DESC";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = mapResultSet(rs);
                list.add(sp);
            }
        }
        return list;
    }

    public List<SanPham> findWithPagination(int page, int pageSize, String keyword, Integer maDm) throws SQLException {
        List<SanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm " +
            "FROM san_pham sp LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND sp.ten_sp LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }
        if (maDm != null && maDm > 0) {
            sql.append("AND sp.ma_dm = ? ");
            params.add(maDm);
        }

        sql.append("ORDER BY sp.ma_sp DESC LIMIT ? OFFSET ?");
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        }
        return list;
    }

    public int countWithPagination(String keyword, Integer maDm) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM san_pham sp WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND sp.ten_sp LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }
        if (maDm != null && maDm > 0) {
            sql.append("AND sp.ma_dm = ? ");
            params.add(maDm);
        }

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean insert(SanPham sp) throws SQLException {
        String sql = "INSERT INTO san_pham(ten_sp, don_gia, so_luong, ma_dm) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sp.getTenSp());
            ps.setBigDecimal(2, sp.getDonGia());
            ps.setInt(3, sp.getSoLuong());
            if (sp.getMaDm() != null && sp.getMaDm() > 0) {
                ps.setInt(4, sp.getMaDm());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            int res = ps.executeUpdate();
            if (res > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) sp.setMaSp(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    public boolean update(SanPham sp) throws SQLException {
        String sql = "UPDATE san_pham SET ten_sp = ?, don_gia = ?, so_luong = ?, ma_dm = ? WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSp());
            ps.setBigDecimal(2, sp.getDonGia());
            ps.setInt(3, sp.getSoLuong());
            if (sp.getMaDm() != null && sp.getMaDm() > 0) {
                ps.setInt(4, sp.getMaDm());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, sp.getMaSp());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int maSp) throws SQLException {
        String sql = "DELETE FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSp);
            return ps.executeUpdate() > 0;
        }
    }

    public List<SanPham> searchByName(String keyword) throws SQLException {
        return findWithPagination(1, 1000, keyword, null);
    }

    private SanPham mapResultSet(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setMaSp(rs.getInt("ma_sp"));
        sp.setTenSp(rs.getString("ten_sp"));
        sp.setDonGia(rs.getBigDecimal("don_gia"));
        sp.setSoLuong(rs.getInt("so_luong"));
        int dm = rs.getInt("ma_dm");
        if (!rs.wasNull()) {
            sp.setMaDm(dm);
            sp.setTenDm(rs.getString("ten_dm"));
        }
        return sp;
    }
}
