package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.entity.SanPham;
import vn.edu.eaut.lab9.util.JPAUtil;

import java.util.List;

/**
 * Bài 13: Repository cho SanPham.
 */
public class SanPhamRepository extends BaseRepository<SanPham> {

    public SanPhamRepository() {
        super(SanPham.class);
    }

    /**
     * Tìm kiếm sản phẩm theo tên hoặc mã.
     */
    public List<SanPham> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT sp FROM SanPham sp WHERE " +
                           "LOWER(sp.tenSP) LIKE LOWER(:kw) OR LOWER(sp.maSP) LIKE LOWER(:kw)";
            TypedQuery<SanPham> query = em.createQuery(jpql, SanPham.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm theo danh mục.
     */
    public List<SanPham> findByDanhMuc(String danhMuc) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT sp FROM SanPham sp WHERE sp.danhMuc = :dm";
            TypedQuery<SanPham> query = em.createQuery(jpql, SanPham.class);
            query.setParameter("dm", danhMuc);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
