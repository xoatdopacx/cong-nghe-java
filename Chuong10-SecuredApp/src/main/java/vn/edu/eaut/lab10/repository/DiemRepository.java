package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.Diem;

import java.util.List;

/**
 * Repository cho Diem - JPQL join SinhVien và MonHoc.
 */
public class DiemRepository extends BaseRepository<Diem> {

    public DiemRepository() {
        super(Diem.class);
    }

    /**
     * Tìm điểm theo sinh viên.
     */
    public List<Diem> findBySinhVien(Long sinhVienId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d WHERE d.sinhVien.id = :svId";
            TypedQuery<Diem> query = em.createQuery(jpql, Diem.class);
            query.setParameter("svId", sinhVienId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm điểm theo môn học.
     */
    public List<Diem> findByMonHoc(Long monHocId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d WHERE d.monHoc.id = :mhId";
            TypedQuery<Diem> query = em.createQuery(jpql, Diem.class);
            query.setParameter("mhId", monHocId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Kiểm tra đã có điểm cho SV + Môn chưa.
     */
    public Diem findBySinhVienAndMonHoc(Long sinhVienId, Long monHocId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d WHERE d.sinhVien.id = :svId AND d.monHoc.id = :mhId";
            TypedQuery<Diem> query = em.createQuery(jpql, Diem.class);
            query.setParameter("svId", sinhVienId);
            query.setParameter("mhId", monHocId);
            List<Diem> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}
