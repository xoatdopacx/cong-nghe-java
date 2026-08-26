package vn.edu.eaut.lab10.listener;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.*;

/**
 * Bài 6: AppListener - khởi tạo JPA, seed dữ liệu mẫu khi app khởi động.
 * Tạo tài khoản ADMIN, STAFF, USER ban đầu + dữ liệu nghiệp vụ.
 */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Lab 10 Secured App - Khởi động ===");
        try {
            // Trigger JPAUtil static init
            JPAUtil.getEntityManager().close();
            System.out.println("✅ EntityManagerFactory đã khởi tạo.");

            // Seed dữ liệu mẫu
            seedData();
        } catch (Exception e) {
            System.err.println("❌ Lỗi khởi tạo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.close();
        System.out.println("=== Lab 10 Secured App - Đã shutdown ===");
    }

    /**
     * Seed dữ liệu mẫu: tài khoản + dữ liệu nghiệp vụ.
     */
    private void seedData() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Kiểm tra đã có dữ liệu chưa
            long count = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("Database đã có dữ liệu, bỏ qua seed.");
                return;
            }

            tx.begin();

            // === Tài khoản người dùng (Bài 6) ===
            User admin = new User("admin@eaut.edu.vn", "admin123", "Quản trị viên", Role.ADMIN);
            User staff = new User("staff@eaut.edu.vn", "staff123", "Nhân viên", Role.STAFF);
            User user = new User("user@eaut.edu.vn", "user123", "Nguyễn Văn Hùng", Role.USER);
            em.persist(admin);
            em.persist(staff);
            em.persist(user);

            // === Lớp Học ===
            LopHoc lop1 = new LopHoc("DCCNTT14.2", "DCCNTT 14.2", "Công nghệ thông tin");
            LopHoc lop2 = new LopHoc("DCCNTT14.1", "DCCNTT 14.1", "Công nghệ thông tin");
            LopHoc lop3 = new LopHoc("DCKTPM14.1", "DCKTPM 14.1", "Kỹ thuật phần mềm");
            em.persist(lop1);
            em.persist(lop2);
            em.persist(lop3);

            // === Sinh Viên ===
            SinhVien sv1 = new SinhVien("20230752", "Nguyễn Văn Hùng", "hung@eaut.edu.vn", "0912345678", "Hà Nội");
            sv1.setLopHoc(lop1);
            SinhVien sv2 = new SinhVien("20230753", "Trần Thị Mai", "mai@eaut.edu.vn", "0987654321", "Hải Phòng");
            sv2.setLopHoc(lop1);
            SinhVien sv3 = new SinhVien("20230754", "Lê Văn Nam", "nam@eaut.edu.vn", "0901234567", "Đà Nẵng");
            sv3.setLopHoc(lop2);
            SinhVien sv4 = new SinhVien("20230755", "Phạm Thị Lan", "lan@eaut.edu.vn", "0978123456", "Hà Nội");
            sv4.setLopHoc(lop2);
            SinhVien sv5 = new SinhVien("20230756", "Hoàng Minh Tuấn", "tuan@eaut.edu.vn", "0965432100", "Nghệ An");
            sv5.setLopHoc(lop3);
            SinhVien sv6 = new SinhVien("20230757", "Vũ Thị Hương", "huong@eaut.edu.vn", "0934567890", "Thanh Hóa");
            sv6.setLopHoc(lop1);
            em.persist(sv1); em.persist(sv2); em.persist(sv3);
            em.persist(sv4); em.persist(sv5); em.persist(sv6);

            // === Môn Học ===
            MonHoc mh1 = new MonHoc("IT3242", "Công nghệ Java", 3);
            MonHoc mh2 = new MonHoc("IT3220", "Lập trình Web", 3);
            MonHoc mh3 = new MonHoc("IT1010", "Nhập môn lập trình", 4);
            MonHoc mh4 = new MonHoc("IT2030", "Cơ sở dữ liệu", 3);
            em.persist(mh1); em.persist(mh2); em.persist(mh3); em.persist(mh4);

            // === Điểm ===
            em.persist(new Diem(sv1, mh1, 9.0));
            em.persist(new Diem(sv1, mh2, 8.5));
            em.persist(new Diem(sv2, mh1, 7.5));
            em.persist(new Diem(sv2, mh3, 6.0));
            em.persist(new Diem(sv3, mh1, 8.0));
            em.persist(new Diem(sv3, mh4, 7.0));
            em.persist(new Diem(sv4, mh2, 5.5));
            em.persist(new Diem(sv5, mh1, 9.5));

            // === Sản Phẩm ===
            em.persist(new SanPham("SP001", "Laptop Dell XPS 15", 32000000.0, 10, "Laptop"));
            em.persist(new SanPham("SP002", "iPhone 15 Pro Max", 29990000.0, 25, "Điện thoại"));
            em.persist(new SanPham("SP003", "Samsung Galaxy S24", 22990000.0, 15, "Điện thoại"));
            em.persist(new SanPham("SP004", "MacBook Air M3", 28990000.0, 8, "Laptop"));
            em.persist(new SanPham("SP005", "Tai nghe Sony WH-1000XM5", 7490000.0, 30, "Phụ kiện"));

            tx.commit();
            System.out.println("✅ Đã seed dữ liệu mẫu thành công!");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("❌ Lỗi seed dữ liệu: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
