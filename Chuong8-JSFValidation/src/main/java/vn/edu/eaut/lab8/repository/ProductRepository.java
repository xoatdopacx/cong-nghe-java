package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.Product;
import java.util.*;

/**
 * Repository sản phẩm - lưu trữ trong bộ nhớ.
 */
public class ProductRepository {
    private static final List<Product> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new Product(1, "Laptop Dell XPS 15", 25000000, 10, "Laptop cao cấp"));
        data.add(new Product(2, "Chuột Logitech G502", 1500000, 50, "Chuột gaming"));
    }

    public List<Product> findAll() {
        return data;
    }

    public void add(Product product) {
        product.setId(autoId++);
        data.add(product);
    }

    public void delete(int id) {
        data.removeIf(p -> p.getId() == id);
    }
}
