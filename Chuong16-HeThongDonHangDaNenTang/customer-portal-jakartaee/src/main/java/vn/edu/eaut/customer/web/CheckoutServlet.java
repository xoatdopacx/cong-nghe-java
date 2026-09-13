package vn.edu.eaut.customer.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.customer.dao.OrderDAO;
import vn.edu.eaut.customer.dao.UserDAO.User;

import java.io.IOException;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?error=empty_cart");
            return;
        }

        String note = req.getParameter("note");
        if (note == null) note = "";

        try {
            long orderId = orderDAO.createOrder(user.id(), cart, note.trim());
            session.removeAttribute("cart");
            session.removeAttribute("cartCount");
            resp.sendRedirect(req.getContextPath() + "/orders/detail?id=" + orderId + "&created=true");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/cart?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
    }
}
