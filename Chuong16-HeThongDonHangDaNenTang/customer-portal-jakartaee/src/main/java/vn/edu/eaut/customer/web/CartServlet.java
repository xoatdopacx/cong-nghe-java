package vn.edu.eaut.customer.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.customer.dao.OrderDAO;
import vn.edu.eaut.customer.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@WebServlet(urlPatterns = {"/cart", "/cart/*"})
public class CartServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    public record CartItemDto(Product product, int quantity, BigDecimal subtotal) {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/clear")) {
            clearCart(req);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        renderCartPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            renderCartPage(req, resp);
            return;
        }

        switch (pathInfo) {
            case "/add" -> handleAddToCart(req, resp);
            case "/update" -> handleUpdateCart(req, resp);
            case "/remove" -> handleRemoveFromCart(req, resp);
            case "/clear" -> {
                clearCart(req);
                resp.sendRedirect(req.getContextPath() + "/cart");
            }
            default -> resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            int qty = Integer.parseInt(req.getParameter("quantity"));
            if (qty <= 0) qty = 1;

            HttpSession session = req.getSession(true);
            @SuppressWarnings("unchecked")
            Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
            if (cart == null) {
                cart = new LinkedHashMap<>();
                session.setAttribute("cart", cart);
            }

            cart.put(productId, cart.getOrDefault(productId, 0) + qty);
            updateCartCount(session, cart);

            String redirect = req.getParameter("redirect");
            if ("catalog".equals(redirect)) {
                resp.sendRedirect(req.getContextPath() + "/products?added=true");
            } else {
                resp.sendRedirect(req.getContextPath() + "/cart?added=true");
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/products?error=invalid_product");
        }
    }

    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            int qty = Integer.parseInt(req.getParameter("quantity"));

            HttpSession session = req.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
                if (cart != null) {
                    if (qty <= 0) {
                        cart.remove(productId);
                    } else {
                        cart.put(productId, qty);
                    }
                    updateCartCount(session, cart);
                }
            }
        } catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void handleRemoveFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            HttpSession session = req.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
                if (cart != null) {
                    cart.remove(productId);
                    updateCartCount(session, cart);
                }
            }
        } catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void clearCart(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("cart");
            session.removeAttribute("cartCount");
        }
    }

    private void updateCartCount(HttpSession session, Map<Long, Integer> cart) {
        int count = 0;
        if (cart != null) {
            for (int q : cart.values()) {
                count += q;
            }
        }
        session.setAttribute("cartCount", count);
    }

    private void renderCartPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        List<CartItemDto> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        if (cart != null && !cart.isEmpty()) {
            try {
                List<Product> products = orderDAO.findActiveProducts();
                Map<Long, Product> productMap = new HashMap<>();
                for (Product p : products) {
                    productMap.put(p.getId(), p);
                }

                for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                    Product p = productMap.get(entry.getKey());
                    if (p != null) {
                        int qty = entry.getValue();
                        BigDecimal subtotal = p.getPrice().multiply(BigDecimal.valueOf(qty));
                        items.add(new CartItemDto(p, qty, subtotal));
                        totalAmount = totalAmount.add(subtotal);
                    }
                }
            } catch (SQLException e) {
                req.setAttribute("errorMessage", "Không thể lấy thông tin sản phẩm: " + e.getMessage());
            }
        }

        req.setAttribute("cartItems", items);
        req.setAttribute("totalAmount", totalAmount);
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
