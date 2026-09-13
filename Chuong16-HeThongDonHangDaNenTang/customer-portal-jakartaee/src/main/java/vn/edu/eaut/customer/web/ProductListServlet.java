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
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"", "/products"})
public class ProductListServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        try {
            List<Product> products = orderDAO.findActiveProducts();
            if (query != null && !query.trim().isEmpty()) {
                String qLower = query.trim().toLowerCase();
                products = products.stream()
                        .filter(p -> p.getName().toLowerCase().contains(qLower) ||
                                     p.getCode().toLowerCase().contains(qLower))
                        .collect(Collectors.toList());
            }

            req.setAttribute("products", products);
            req.setAttribute("searchQuery", query);

            // Calculate cart item count
            HttpSession session = req.getSession(false);
            int cartCount = 0;
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
                if (cart != null) {
                    cartCount = cart.values().stream().mapToInt(Integer::intValue).sum();
                }
            }
            req.setAttribute("cartCount", cartCount);

            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("errorMessage", "Không thể tải danh sách sản phẩm: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
        }
    }
}
