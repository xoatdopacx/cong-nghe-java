package vn.edu.eaut.management.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.management.entity.Product;
import vn.edu.eaut.management.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return productRepository.findAll();
        return productRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(keyword, keyword);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
