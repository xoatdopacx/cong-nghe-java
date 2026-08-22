package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.entity.NguoiDung;
import vn.edu.eaut.lab9.util.JPAUtil;

import java.util.List;

/**
 * Bài 13: Repository cho NguoiDung.
 */
public class NguoiDungRepository extends BaseRepository<NguoiDung> {

    public NguoiDungRepository() {
        super(NguoiDung.class);
    }

    /**
     * Tìm theo tên đăng nhập.
     */
    public NguoiDung findByTenDangNhap(String tenDangNhap) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT nd FROM NguoiDung nd WHERE nd.tenDangNhap = :tdn";
            TypedQuery<NguoiDung> query = em.createQuery(jpql, NguoiDung.class);
            query.setParameter("tdn", tenDangNhap);
            List<NguoiDung> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Xác thực đăng nhập.
     */
    public NguoiDung authenticate(String tenDangNhap, String matKhau) {
        NguoiDung nd = findByTenDangNhap(tenDangNhap);
        if (nd != null && nd.getMatKhau().equals(matKhau) && nd.isTrangThai()) {
            return nd;
        }
        return null;
    }
}
