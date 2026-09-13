package vn.edu.eaut.warehouse.dao;

import vn.edu.eaut.warehouse.db.DatabaseConnection;
import vn.edu.eaut.warehouse.model.Order;
import vn.edu.eaut.warehouse.model.OrderItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object: xử lý truy vấn JDBC cho Orders
 */
public class OrderDAO {

    private Connection getConn() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    /** Lấy danh sách đơn hàng theo trạng thái */
    public List<Order> findByStatus(String status) throws SQLException {
        String sql = "SELECT o.id, o.customer_id, u.full_name, o.created_at, " +
                     "o.total_amount, o.status, o.note, o.version " +
                     "FROM orders o JOIN users u ON o.customer_id = u.id " +
                     (status.equals("ALL") ? "" : "WHERE o.status = ? ") +
                     "ORDER BY o.created_at DESC";
        List<Order> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            if (!status.equals("ALL")) ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getLong("id"));
                o.setCustomerId(rs.getLong("customer_id"));
                o.setCustomerName(rs.getString("full_name"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) o.setCreatedAt(ts.toLocalDateTime());
                o.setTotalAmount(rs.getBigDecimal("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setNote(rs.getString("note"));
                o.setVersion(rs.getInt("version"));
                list.add(o);
            }
        }
        return list;
    }

    /** Lấy chi tiết sản phẩm trong đơn */
    public List<OrderItem> findItemsByOrderId(long orderId) throws SQLException {
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, p.name, p.code, " +
                     "oi.quantity, oi.unit_price, oi.subtotal " +
                     "FROM order_items oi JOIN products p ON oi.product_id = p.id " +
                     "WHERE oi.order_id = ?";
        List<OrderItem> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getLong("id"));
                item.setOrderId(orderId);
                item.setProductId(rs.getLong("product_id"));
                item.setProductName(rs.getString("name"));
                item.setProductCode(rs.getString("code"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getBigDecimal("unit_price"));
                item.setSubtotal(rs.getBigDecimal("subtotal"));
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Cập nhật trạng thái đơn (kho chuyển PENDING→PROCESSING hoặc PROCESSING→READY)
     * Trừ tồn kho khi chuyển sang PROCESSING
     */
    public boolean updateStatus(long orderId, String newStatus, long warehouseUserId, String note)
            throws SQLException {
        Connection conn = getConn();
        conn.setAutoCommit(false);
        try {
            // Lấy trạng thái hiện tại + version
            String sel = "SELECT status, version FROM orders WHERE id = ? FOR UPDATE";
            String oldStatus;
            int version;
            try (PreparedStatement ps = conn.prepareStatement(sel)) {
                ps.setLong(1, orderId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) throw new SQLException("Không tìm thấy đơn hàng #" + orderId);
                oldStatus = rs.getString("status");
                version   = rs.getInt("version");
            }

            // Trừ tồn kho khi kho bắt đầu xử lý
            if ("PROCESSING".equals(newStatus) && "PENDING".equals(oldStatus)) {
                String stockSql = "UPDATE products p " +
                        "JOIN order_items oi ON p.id = oi.product_id " +
                        "SET p.stock = p.stock - oi.quantity " +
                        "WHERE oi.order_id = ? AND p.stock >= oi.quantity";
                try (PreparedStatement ps = conn.prepareStatement(stockSql)) {
                    ps.setLong(1, orderId);
                    ps.executeUpdate();
                }
            }

            // Cập nhật trạng thái đơn
            String upd = "UPDATE orders SET status = ?, version = version + 1 WHERE id = ? AND version = ?";
            int rows;
            try (PreparedStatement ps = conn.prepareStatement(upd)) {
                ps.setString(1, newStatus);
                ps.setLong(2, orderId);
                ps.setInt(3, version);
                rows = ps.executeUpdate();
            }
            if (rows == 0) throw new SQLException("Xung đột dữ liệu! Đơn hàng đã bị thay đổi bởi nền tảng khác.");

            // Ghi lịch sử
            String hist = "INSERT INTO order_status_history (order_id,old_status,new_status,changed_by,changed_at,platform,note) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(hist)) {
                ps.setLong(1, orderId);
                ps.setString(2, oldStatus);
                ps.setString(3, newStatus);
                ps.setLong(4, warehouseUserId);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "JAVA_SWING");
                ps.setString(7, note);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw new SQLException(e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /** Đếm đơn theo trạng thái */
    public int countByStatus(String status) throws SQLException {
        String sql = status.equals("ALL")
                ? "SELECT COUNT(*) FROM orders"
                : "SELECT COUNT(*) FROM orders WHERE status = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            if (!status.equals("ALL")) ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
