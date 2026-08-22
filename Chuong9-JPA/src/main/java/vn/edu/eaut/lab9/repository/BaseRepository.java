package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.util.JPAUtil;

import java.util.List;
import java.util.Optional;

/**
 * Bài 3, 8: BaseRepository - Generic CRUD Repository.
 * Sử dụng EntityManager với pattern begin/commit/rollback.
 *
 * @param <T> Kiểu entity
 */
public abstract class BaseRepository<T> {

    private final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Tìm tất cả entity.
     */
    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Tìm theo ID.
     */
    public Optional<T> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            em.close();
        }
    }

    /**
     * Lưu entity mới (persist).
     */
    public T save(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi lưu: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Cập nhật entity (merge).
     */
    public T update(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Xóa entity theo ID.
     */
    public boolean delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Đếm tổng số bản ghi.
     */
    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Bài 9: Phân trang (JPQL với setFirstResult / setMaxResults).
     */
    public List<T> findPage(int page, int size) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Lấy class entity.
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
