package vn.edu.eaut.customer.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.customer.dao.OrderDAO;
import vn.edu.eaut.customer.dao.UserDAO.User;
import vn.edu.eaut.customer.model.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrderListServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<Order> orders = orderDAO.findByCustomer(user.id());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("errorMessage", "Không thể tải danh sách đơn hàng: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
        }
    }
}
