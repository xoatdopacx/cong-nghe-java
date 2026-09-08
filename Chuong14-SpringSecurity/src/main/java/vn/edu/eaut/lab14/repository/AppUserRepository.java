package vn.edu.eaut.lab14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.lab14.entity.AppUser;
import java.util.Optional;

/**
 * Bài 10: Repository cho AppUser (user lưu trong CSDL).
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
