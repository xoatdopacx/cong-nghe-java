package vn.edu.eaut.management.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.management.entity.OrderStatus;
import vn.edu.eaut.management.service.OrderService;
import vn.edu.eaut.management.service.ProductService;

@Controller
public class HomeController {

    private final OrderService orderService;
    private final ProductService productService;

    public HomeController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("currentUser", userDetails.getUsername());
        model.addAttribute("totalOrders", orderService.countTotal());
        model.addAttribute("pendingOrders", orderService.countByStatus(OrderStatus.PENDING));
        model.addAttribute("processingOrders", orderService.countByStatus(OrderStatus.PROCESSING));
        model.addAttribute("shippingOrders", orderService.countByStatus(OrderStatus.SHIPPING));
        model.addAttribute("completedOrders", orderService.countByStatus(OrderStatus.COMPLETED));
        model.addAttribute("totalProducts", productService.findAll().size());
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
