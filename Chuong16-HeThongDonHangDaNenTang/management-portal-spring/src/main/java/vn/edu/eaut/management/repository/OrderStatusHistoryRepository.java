package vn.edu.eaut.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.management.entity.OrderStatusHistory;
import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrderIdOrderByChangedAtDesc(Long orderId);
}
