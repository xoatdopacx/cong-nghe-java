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
import java.sql.SQLException;

@WebServlet("/orders/cancel")
public class OrderCancelServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            long orderId = Long.parseLong(req.getParameter("id"));
            orderDAO.cancelOrder(orderId, user.id());
            resp.sendRedirect(req.getContextPath() + "/orders/detail?id=" + orderId + "&cancelled=true");
        } catch (SQLException e) {
            long orderId = Long.parseLong(req.getParameter("id"));
            resp.sendRedirect(req.getContextPath() + "/orders/detail?id=" + orderId + "&error=" +
                    java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
        }
    }
}
