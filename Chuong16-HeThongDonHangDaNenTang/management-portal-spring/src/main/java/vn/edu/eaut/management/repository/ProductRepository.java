package vn.edu.eaut.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.management.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String name, String code);
}
