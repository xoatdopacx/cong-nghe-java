package vn.edu.eaut.lab15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.lab15.entity.AppUser;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
