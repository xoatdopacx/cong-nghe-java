package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.User;

import java.util.List;

/**
 * Bài 2: Repository cho User - xác thực và quản lý tài khoản.
 */
public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    /**
     * Tìm theo email (dùng cho đăng nhập).
     */
    public User findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            List<User> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Tìm kiếm user theo email hoặc họ tên (cho ADMIN quản lý).
     */
    public List<User> search(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE " +
                           "LOWER(u.email) LIKE LOWER(:kw) OR " +
                           "LOWER(u.fullName) LIKE LOWER(:kw)";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
