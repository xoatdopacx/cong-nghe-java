package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.repository.SinhVienRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer cho SinhVien - validate nghiệp vụ.
 */
public class SinhVienService {

    private final SinhVienRepository svRepo = new SinhVienRepository();

    /**
     * Validate dữ liệu SinhVien.
     * @return danh sách lỗi, rỗng nếu hợp lệ
     */
    public List<String> validate(SinhVien sv, boolean isNew) {
        List<String> errors = new ArrayList<>();

        if (sv.getMaSV() == null || sv.getMaSV().trim().isEmpty()) {
            errors.add("Mã sinh viên không được để trống");
        }
        if (sv.getHoTen() == null || sv.getHoTen().trim().isEmpty()) {
            errors.add("Họ tên không được để trống");
        }
        if (sv.getEmail() != null && !sv.getEmail().trim().isEmpty()) {
            if (!sv.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                errors.add("Email không đúng định dạng");
            }
        }

        // Kiểm tra mã SV trùng
        if (isNew && sv.getMaSV() != null) {
            SinhVien existing = svRepo.findByMaSV(sv.getMaSV().trim());
            if (existing != null) {
                errors.add("Mã sinh viên '" + sv.getMaSV() + "' đã tồn tại");
            }
        }

        return errors;
    }
}
