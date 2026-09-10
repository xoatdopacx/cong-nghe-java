package vn.edu.eaut.lab15.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.eaut.lab15.entity.AppUser;
import vn.edu.eaut.lab15.repository.AppUserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (appUserRepository.count() == 0) {
            appUserRepository.save(new AppUser("admin", passwordEncoder.encode("123456"), "ADMIN"));
            appUserRepository.save(new AppUser("user", passwordEncoder.encode("123456"), "USER"));
            appUserRepository.save(new AppUser("hung", passwordEncoder.encode("123456"), "ADMIN"));
            System.out.println(">>> Đã tạo 3 tài khoản: admin/123456 (ADMIN), user/123456 (USER), hung/123456 (ADMIN)");
        }
    }
}
