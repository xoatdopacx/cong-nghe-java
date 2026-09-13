package vn.edu.eaut.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.management.entity.AppUser;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
