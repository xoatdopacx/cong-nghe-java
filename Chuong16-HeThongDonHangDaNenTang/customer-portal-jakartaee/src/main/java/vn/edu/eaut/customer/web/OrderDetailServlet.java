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

@WebServlet("/orders/detail")
public class OrderDetailServlet extends HttpServlet {

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
            long id = Long.parseLong(req.getParameter("id"));
            Order order = orderDAO.findById(id, user.id());
            if (order == null) {
                resp.sendRedirect(req.getContextPath() + "/orders?error=not_found");
                return;
            }
            req.setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/views/order-detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (SQLException e) {
            req.setAttribute("errorMessage", "Lỗi tải chi tiết đơn hàng: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/order-detail.jsp").forward(req, resp);
        }
    }
}
