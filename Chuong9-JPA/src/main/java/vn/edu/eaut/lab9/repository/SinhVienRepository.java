package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.entity.SinhVien;
import vn.edu.eaut.lab9.util.JPAUtil;

import java.util.List;

/**
 * Bài 4, 9: Repository cho SinhVien với JPQL search và phân trang.
 */
public class SinhVienRepository extends BaseRepository<SinhVien> {

    public SinhVienRepository() {
        super(SinhVien.class);
    }

    /**
     * Tìm kiếm theo tên hoặc mã SV (JPQL LIKE).
     */
    public List<SinhVien> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT sv FROM SinhVien sv WHERE " +
                           "LOWER(sv.hoTen) LIKE LOWER(:kw) OR " +
                           "LOWER(sv.maSV) LIKE LOWER(:kw) OR " +
                           "LOWER(sv.email) LIKE LOWER(:kw)";
            TypedQuery<SinhVien> query = em.createQuery(jpql, SinhVien.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm SV theo lớp học.
     */
    public List<SinhVien> findByLopHoc(Long lopHocId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT sv FROM SinhVien sv WHERE sv.lopHoc.id = :lopId";
            TypedQuery<SinhVien> query = em.createQuery(jpql, SinhVien.class);
            query.setParameter("lopId", lopHocId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm SV theo mã SV (unique).
     */
    public SinhVien findByMaSV(String maSV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT sv FROM SinhVien sv WHERE sv.maSV = :maSV";
            TypedQuery<SinhVien> query = em.createQuery(jpql, SinhVien.class);
            query.setParameter("maSV", maSV);
            List<SinhVien> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Phân trang với tìm kiếm.
     */
    public List<SinhVien> searchPage(String keyword, int page, int size) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql;
            TypedQuery<SinhVien> query;
            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql = "SELECT sv FROM SinhVien sv WHERE " +
                       "LOWER(sv.hoTen) LIKE LOWER(:kw) OR LOWER(sv.maSV) LIKE LOWER(:kw)";
                query = em.createQuery(jpql, SinhVien.class);
                query.setParameter("kw", "%" + keyword + "%");
            } else {
                jpql = "SELECT sv FROM SinhVien sv";
                query = em.createQuery(jpql, SinhVien.class);
            }
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Đếm kết quả tìm kiếm.
     */
    public long countSearch(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql;
            TypedQuery<Long> query;
            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql = "SELECT COUNT(sv) FROM SinhVien sv WHERE " +
                       "LOWER(sv.hoTen) LIKE LOWER(:kw) OR LOWER(sv.maSV) LIKE LOWER(:kw)";
                query = em.createQuery(jpql, Long.class);
                query.setParameter("kw", "%" + keyword + "%");
            } else {
                jpql = "SELECT COUNT(sv) FROM SinhVien sv";
                query = em.createQuery(jpql, Long.class);
            }
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
