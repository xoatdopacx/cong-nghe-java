package vn.edu.eaut.customer.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.eaut.customer.db.DBUtil;

import java.sql.*;

public class UserDAO {

    public record User(long id, String username, String fullName, String role) {}

    /**
     * Xác thực đăng nhập, chỉ cho phép CUSTOMER đăng nhập
     * @return User nếu thành công, null nếu thất bại
     * @throws RuntimeException với thông báo lỗi cụ thể
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT id, username, full_name, role, password_hash, enabled FROM users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new RuntimeException("Tên đăng nhập không tồn tại!");
            if (!rs.getBoolean("enabled")) throw new RuntimeException("Tài khoản đã bị khóa!");
            if (!BCrypt.checkpw(password, rs.getString("password_hash"))) {
                throw new RuntimeException("Mật khẩu không đúng!");
            }
            String role = rs.getString("role");
            if (!"CUSTOMER".equals(role)) {
                throw new RuntimeException("Cổng này chỉ dành cho khách hàng (CUSTOMER)!");
            }
            return new User(rs.getLong("id"), rs.getString("username"), rs.getString("full_name"), role);
        } catch (RuntimeException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kết nối CSDL: " + e.getMessage(), e);
        }
    }

    public User register(String username, String fullName, String password) {
        String check = "SELECT id FROM users WHERE username = ?";
        String insert = "INSERT INTO users (username, password_hash, full_name, role) VALUES (?,?,?,?)";
        try (Connection conn = DBUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(check)) {
                ps.setString(1, username);
                if (ps.executeQuery().next()) throw new RuntimeException("Tên đăng nhập đã tồn tại!");
            }
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, hash);
                ps.setString(3, fullName);
                ps.setString(4, "CUSTOMER");
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                return new User(keys.getLong(1), username, fullName, "CUSTOMER");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi CSDL: " + e.getMessage(), e);
        }
    }
}
