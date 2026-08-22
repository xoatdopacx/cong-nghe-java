package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.entity.LopHoc;
import vn.edu.eaut.lab9.util.JPAUtil;

import java.util.List;

/**
 * Repository cho LopHoc.
 */
public class LopHocRepository extends BaseRepository<LopHoc> {

    public LopHocRepository() {
        super(LopHoc.class);
    }

    @Override
    public List<LopHoc> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT l FROM LopHoc l LEFT JOIN FETCH l.sinhViens ORDER BY l.id ASC";
            return em.createQuery(jpql, LopHoc.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm lớp theo mã lớp.
     */
    public LopHoc findByMaLop(String maLop) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT l FROM LopHoc l WHERE l.maLop = :maLop";
            TypedQuery<LopHoc> query = em.createQuery(jpql, LopHoc.class);
            query.setParameter("maLop", maLop);
            List<LopHoc> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Tìm kiếm lớp theo tên hoặc mã.
     */
    public List<LopHoc> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT l FROM LopHoc l LEFT JOIN FETCH l.sinhViens WHERE " +
                           "LOWER(l.tenLop) LIKE LOWER(:kw) OR LOWER(l.maLop) LIKE LOWER(:kw) ORDER BY l.id ASC";
            TypedQuery<LopHoc> query = em.createQuery(jpql, LopHoc.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
