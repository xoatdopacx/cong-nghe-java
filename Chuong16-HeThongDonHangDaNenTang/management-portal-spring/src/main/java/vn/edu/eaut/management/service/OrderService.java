package vn.edu.eaut.management.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.management.entity.*;
import vn.edu.eaut.management.repository.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderStatusHistoryRepository historyRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    public Page<Order> findAll(int page, int size) {
        return orderRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    public Page<Order> findByStatus(OrderStatus status, int page, int size) {
        return orderRepository.findByStatusOrderByCreatedAtDesc(status, PageRequest.of(page, size));
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderStatusHistory> getHistory(Long orderId) {
        return historyRepository.findByOrderIdOrderByChangedAtDesc(orderId);
    }

    @Transactional
    public void updateStatus(Long orderId, OrderStatus newStatus, Long managerId, String note) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng #" + orderId));

        String oldStatus = order.getStatus().name();
        order.setStatus(newStatus);
        orderRepository.save(order);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(orderId);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus.name());
        history.setChangedBy(managerId);
        history.setChangedAt(LocalDateTime.now());
        history.setPlatform("SPRING_BOOT");
        history.setNote(note);
        historyRepository.save(history);
    }

    public long countByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    public long countTotal() {
        return orderRepository.count();
    }
}
