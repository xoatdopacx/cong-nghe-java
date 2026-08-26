package vn.edu.eaut.lab10.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Tiện ích quản lý EntityManagerFactory singleton.
 * Đảm bảo chỉ tạo một EMF duy nhất cho toàn ứng dụng.
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT = "lab10PU";
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (Exception e) {
            System.err.println("Lỗi khởi tạo EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Lấy EntityManager mới từ factory.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Đóng EntityManagerFactory khi shutdown ứng dụng.
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
