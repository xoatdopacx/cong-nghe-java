package vn.edu.eaut.customer.dao;

import vn.edu.eaut.customer.db.DBUtil;
import vn.edu.eaut.customer.model.Order;
import vn.edu.eaut.customer.model.OrderItem;
import vn.edu.eaut.customer.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    /** Lấy danh sách sản phẩm đang bán */
    public List<Product> findActiveProducts() throws SQLException {
        String sql = "SELECT id, code, name, price, stock FROM products WHERE active = TRUE ORDER BY name";
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("id"));
                p.setCode(rs.getString("code"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setStock(rs.getInt("stock"));
                p.setActive(true);
                list.add(p);
            }
        }
        return list;
    }

    /** Tạo đơn hàng mới - ghi vào orders + order_items */
    public long createOrder(long customerId, Map<Long, Integer> cart, String note) throws SQLException {
        Connection conn = DBUtil.getConnection();
        conn.setAutoCommit(false);
        try {
            // Tính tổng tiền + kiểm tra tồn kho
            BigDecimal total = BigDecimal.ZERO;
            List<long[]> productIds = new ArrayList<>();

            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                long productId = entry.getKey();
                int qty = entry.getValue();
                if (qty <= 0) continue;

                String checkSql = "SELECT price, stock FROM products WHERE id = ? AND active = TRUE FOR SHARE";
                try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                    ps.setLong(1, productId);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) throw new SQLException("Sản phẩm #" + productId + " không tồn tại hoặc ngừng bán!");
                    int stock = rs.getInt("stock");
                    if (stock < qty) throw new SQLException("Sản phẩm #" + productId + " không đủ tồn kho! (còn " + stock + ")");
                    BigDecimal price = rs.getBigDecimal("price");
                    total = total.add(price.multiply(BigDecimal.valueOf(qty)));
                }
            }

            // Tạo đơn hàng
            String insertOrder = "INSERT INTO orders (customer_id, created_at, total_amount, status, note) VALUES (?,?,?,?,?)";
            long orderId;
            try (PreparedStatement ps = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, customerId);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setBigDecimal(3, total);
                ps.setString(4, "PENDING");
                ps.setString(5, note);
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                orderId = keys.getLong(1);
            }

            // Thêm từng sản phẩm vào order_items
            String insertItem = "INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal) VALUES (?,?,?,?,?)";
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                long productId = entry.getKey();
                int qty = entry.getValue();
                if (qty <= 0) continue;

                String priceSql = "SELECT price FROM products WHERE id = ?";
                BigDecimal price;
                try (PreparedStatement ps = conn.prepareStatement(priceSql)) {
                    ps.setLong(1, productId);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    price = rs.getBigDecimal("price");
                }

                try (PreparedStatement ps = conn.prepareStatement(insertItem)) {
                    ps.setLong(1, orderId);
                    ps.setLong(2, productId);
                    ps.setInt(3, qty);
                    ps.setBigDecimal(4, price);
                    ps.setBigDecimal(5, price.multiply(BigDecimal.valueOf(qty)));
                    ps.executeUpdate();
                }
            }

            // Ghi lịch sử
            String hist = "INSERT INTO order_status_history (order_id,old_status,new_status,changed_by,changed_at,platform,note) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(hist)) {
                ps.setLong(1, orderId);
                ps.setNull(2, Types.VARCHAR);
                ps.setString(3, "PENDING");
                ps.setLong(4, customerId);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "JAKARTA_EE");
                ps.setString(7, "Đơn hàng mới được tạo");
                ps.executeUpdate();
            }

            conn.commit();
            return orderId;
        } catch (Exception e) {
            conn.rollback();
            throw new SQLException(e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    /** Lấy lịch sử đơn hàng của khách */
    public List<Order> findByCustomer(long customerId) throws SQLException {
        String sql = "SELECT id, created_at, total_amount, status, note FROM orders WHERE customer_id = ? ORDER BY created_at DESC";
        List<Order> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getLong("id"));
                o.setCustomerId(customerId);
                o.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                o.setTotalAmount(rs.getBigDecimal("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setNote(rs.getString("note"));
                list.add(o);
            }
        }
        return list;
    }

    /** Lấy chi tiết 1 đơn */
    public Order findById(long orderId, long customerId) throws SQLException {
        String sql = "SELECT id, created_at, total_amount, status, note FROM orders WHERE id = ? AND customer_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.setLong(2, customerId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            Order o = new Order();
            o.setId(orderId);
            o.setCustomerId(customerId);
            o.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            o.setTotalAmount(rs.getBigDecimal("total_amount"));
            o.setStatus(rs.getString("status"));
            o.setNote(rs.getString("note"));

            // Load items
            String itemsSql = "SELECT oi.id, oi.product_id, p.name, p.code, oi.quantity, oi.unit_price, oi.subtotal " +
                    "FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
            List<OrderItem> items = new ArrayList<>();
            try (PreparedStatement ps2 = conn.prepareStatement(itemsSql)) {
                ps2.setLong(1, orderId);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    OrderItem it = new OrderItem();
                    it.setId(rs2.getLong("id"));
                    it.setProductId(rs2.getLong("product_id"));
                    it.setProductName(rs2.getString("name"));
                    it.setProductCode(rs2.getString("code"));
                    it.setQuantity(rs2.getInt("quantity"));
                    it.setUnitPrice(rs2.getBigDecimal("unit_price"));
                    it.setSubtotal(rs2.getBigDecimal("subtotal"));
                    items.add(it);
                }
            }
            o.setItems(items);
            return o;
        }
    }

    /** Khách hủy đơn (chỉ khi đang PENDING) */
    public void cancelOrder(long orderId, long customerId) throws SQLException {
        Connection conn = DBUtil.getConnection();
        conn.setAutoCommit(false);
        try {
            String check = "SELECT status FROM orders WHERE id = ? AND customer_id = ?";
            String oldStatus;
            try (PreparedStatement ps = conn.prepareStatement(check)) {
                ps.setLong(1, orderId);
                ps.setLong(2, customerId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) throw new SQLException("Đơn hàng không tồn tại!");
                oldStatus = rs.getString("status");
                if (!"PENDING".equals(oldStatus)) throw new SQLException("Chỉ có thể hủy đơn ở trạng thái PENDING!");
            }

            String upd = "UPDATE orders SET status = 'CANCELLED' WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(upd)) {
                ps.setLong(1, orderId);
                ps.executeUpdate();
            }

            String hist = "INSERT INTO order_status_history (order_id,old_status,new_status,changed_by,changed_at,platform,note) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(hist)) {
                ps.setLong(1, orderId);
                ps.setString(2, oldStatus);
                ps.setString(3, "CANCELLED");
                ps.setLong(4, customerId);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "JAKARTA_EE");
                ps.setString(7, "Khách hàng hủy đơn");
                ps.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw new SQLException(e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}
