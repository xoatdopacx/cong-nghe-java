package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.KhachHangDAL;
import vn.edu.eaut.lab5.model.KhachHang;

import java.sql.SQLException;
import java.util.List;

public class KhachHangBUS {
    private final KhachHangDAL khachHangDAL = new KhachHangDAL();

    public List<KhachHang> findAll() throws SQLException {
        return khachHangDAL.findAll();
    }

    public List<KhachHang> findWithPagination(int page, int pageSize, String keyword) throws SQLException {
        return khachHangDAL.findWithPagination(page, pageSize, keyword);
    }

    public int countWithPagination(String keyword) throws SQLException {
        return khachHangDAL.countWithPagination(keyword);
    }

    public boolean save(KhachHang kh) throws SQLException {
        validate(kh);
        if (kh.getMaKh() == 0) {
            return khachHangDAL.insert(kh);
        }
        return khachHangDAL.update(kh);
    }

    public boolean delete(int maKh) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Mã khách hàng không hợp lệ");
        }
        return khachHangDAL.delete(maKh);
    }

    private void validate(KhachHang kh) {
        if (kh.getTenKh() == null || kh.getTenKh().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (kh.getSdt() == null || !kh.getSdt().matches("\\d{1,10}")) {
            throw new IllegalArgumentException("Số điện thoại chỉ gồm các chữ số và tối đa 10 ký tự");
        }
    }
}
