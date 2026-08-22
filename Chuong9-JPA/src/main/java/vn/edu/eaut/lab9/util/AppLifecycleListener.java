package vn.edu.eaut.lab9.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Lifecycle Listener: khởi tạo JPA và seed data khi app khởi động,
 * đóng EntityManagerFactory khi app shutdown.
 */
@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Lab 09 JPA Repository - Khởi động ===");
        try {
            // Trigger JPAUtil static init
            JPAUtil.getEntityManager().close();
            System.out.println("✅ EntityManagerFactory đã khởi tạo.");

            // Seed dữ liệu mẫu
            DataSeeder.seed();
        } catch (Exception e) {
            System.err.println("❌ Lỗi khởi tạo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.close();
        System.out.println("=== Lab 09 JPA Repository - Đã shutdown ===");
    }
}
