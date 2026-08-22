package vn.edu.eaut.lab9.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.eaut.lab9.entity.Diem;
import vn.edu.eaut.lab9.entity.MonHoc;
import vn.edu.eaut.lab9.entity.SinhVien;
import vn.edu.eaut.lab9.repository.DiemRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;
import vn.edu.eaut.lab9.util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Bài 10, 11: Service layer cho SinhVien.
 * - Validate nghiệp vụ (mã SV trùng, required fields)
 * - Transaction nhiều thao tác (thêm SV + tạo điểm mặc định)
 * - Rollback khi lỗi
 */
public class SinhVienService {

    private final SinhVienRepository svRepo = new SinhVienRepository();
    private final MonHocRepository mhRepo = new MonHocRepository();
    private final DiemRepository diemRepo = new DiemRepository();

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

    /**
     * Bài 11: Thêm SV mới trong transaction.
     * Nếu có monHocs, tạo điểm mặc định (0.0) cho mỗi môn.
     * Rollback nếu bất kỳ thao tác nào lỗi.
     */
    public SinhVien createWithDefaultScores(SinhVien sv) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Persist sinh viên
            em.persist(sv);

            // Tạo điểm mặc định cho tất cả môn học
            List<MonHoc> allMonHoc = em.createQuery("SELECT m FROM MonHoc m", MonHoc.class)
                                       .getResultList();
            for (MonHoc mh : allMonHoc) {
                Diem diem = new Diem(sv, mh, 0.0);
                diem.setGhiChu("Điểm mặc định");
                em.persist(diem);
            }

            tx.commit();
            return sv;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi tạo sinh viên với điểm mặc định: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Bài 11: Demo rollback - thêm nhiều SV, nếu 1 lỗi thì rollback tất cả.
     */
    public int batchCreate(List<SinhVien> sinhViens) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int count = 0;
            for (SinhVien sv : sinhViens) {
                em.persist(sv);
                count++;
            }
            tx.commit();
            return count;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Batch create thất bại, đã rollback: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
