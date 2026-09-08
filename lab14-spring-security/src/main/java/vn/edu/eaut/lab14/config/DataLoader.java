package vn.edu.eaut.lab14.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.eaut.lab14.entity.AppUser;
import vn.edu.eaut.lab14.repository.AppUserRepository;

/**
 * Bài 10: Tạo các tài khoản mẫu trong CSDL khi ứng dụng khởi động.
 * Mật khẩu được mã hóa bằng BCryptPasswordEncoder tại runtime.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataLoader(AppUserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepo.existsByUsername("admin")) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("123456"));
            admin.setFullName("Quản trị viên");
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepo.save(admin);
        }
        if (!userRepo.existsByUsername("user")) {
            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword(encoder.encode("123456"));
            user.setFullName("Người dùng");
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            userRepo.save(user);
        }
        if (!userRepo.existsByUsername("hung")) {
            AppUser hung = new AppUser();
            hung.setUsername("hung");
            hung.setPassword(encoder.encode("123456"));
            hung.setFullName("Nguyễn Văn Hùng");
            hung.setRole("ROLE_ADMIN");
            hung.setEnabled(true);
            userRepo.save(hung);
        }
        System.out.println("=== DataLoader: Đã tạo 3 tài khoản (admin/user/hung) với mật khẩu BCrypt ===");
    }
}
