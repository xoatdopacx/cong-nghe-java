package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.DanhMucDAL;
import vn.edu.eaut.lab5.model.DanhMuc;

import java.sql.SQLException;
import java.util.List;

public class DanhMucBUS {
    private final DanhMucDAL danhMucDAL = new DanhMucDAL();

    public List<DanhMuc> findAll() throws SQLException {
        return danhMucDAL.findAll();
    }

    public boolean save(DanhMuc dm) throws SQLException {
        validate(dm);
        if (dm.getMaDm() == 0) {
            return danhMucDAL.insert(dm);
        }
        return danhMucDAL.update(dm);
    }

    public boolean delete(int maDm) throws SQLException {
        if (maDm <= 0) {
            throw new IllegalArgumentException("Mã danh mục không hợp lệ");
        }
        // Check Bài 6: Không cho xóa nếu có sản phẩm thuộc danh mục
        int count = danhMucDAL.countProductsInCategory(maDm);
        if (count > 0) {
            throw new IllegalStateException("Không thể xóa danh mục vì đang chứa " + count + " sản phẩm!");
        }
        return danhMucDAL.delete(maDm);
    }

    private void validate(DanhMuc dm) {
        if (dm.getTenDm() == null || dm.getTenDm().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không được để trống");
        }
    }
}
