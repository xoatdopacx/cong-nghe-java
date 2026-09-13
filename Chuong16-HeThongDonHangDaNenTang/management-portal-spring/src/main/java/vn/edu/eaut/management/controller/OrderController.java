package vn.edu.eaut.management.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.management.entity.*;
import vn.edu.eaut.management.repository.UserRepository;
import vn.edu.eaut.management.service.OrderService;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listOrders(@RequestParam(defaultValue = "") String status,
                             @RequestParam(defaultValue = "0") int page,
                             Model model) {
        Page<Order> ordersPage;
        if (status.isBlank()) {
            ordersPage = orderService.findAll(page, 10);
        } else {
            ordersPage = orderService.findByStatus(OrderStatus.valueOf(status), page, 10);
        }
        model.addAttribute("ordersPage", ordersPage);
        model.addAttribute("orders", ordersPage.getContent());
        model.addAttribute("currentStatus", status);
        model.addAttribute("statuses", OrderStatus.values());
        return "orders/list";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        List<OrderStatusHistory> history = orderService.getHistory(id);
        model.addAttribute("order", order);
        model.addAttribute("history", history);
        model.addAttribute("statuses", OrderStatus.values());
        return "orders/detail";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String newStatus,
                               @RequestParam(required = false) String note,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        try {
            AppUser manager = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            orderService.updateStatus(id, OrderStatus.valueOf(newStatus), manager.getId(), note);
            redirectAttributes.addFlashAttribute("successMsg", "Cập nhật trạng thái đơn #" + id + " thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Lỗi: " + e.getMessage());
        }
        return "redirect:/orders/" + id;
    }
}
