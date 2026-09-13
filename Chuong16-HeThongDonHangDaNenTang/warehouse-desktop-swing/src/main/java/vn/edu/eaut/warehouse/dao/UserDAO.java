package vn.edu.eaut.warehouse.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.eaut.warehouse.db.DatabaseConnection;

import java.sql.*;

/**
 * UserDAO: xác thực đăng nhập và phân quyền WAREHOUSE
 */
public class UserDAO {

    public record LoginResult(long id, String username, String fullName, String role, boolean success, String errorMsg) {}

    public LoginResult authenticate(String username, String password) {
        String sql = "SELECT id, username, full_name, role, password_hash, enabled FROM users WHERE username = ?";
        try (PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return new LoginResult(0, "", "", "", false, "Tên đăng nhập không tồn tại!");
            }
            String hash    = rs.getString("password_hash");
            boolean enabled = rs.getBoolean("enabled");
            String role    = rs.getString("role");

            if (!enabled) {
                return new LoginResult(0, "", "", "", false, "Tài khoản đã bị khóa!");
            }
            if (!BCrypt.checkpw(password, hash)) {
                return new LoginResult(0, "", "", "", false, "Mật khẩu không đúng!");
            }
            if (!role.equals("WAREHOUSE") && !role.equals("ADMIN")) {
                return new LoginResult(0, "", "", "", false, "Ứng dụng này chỉ dành cho nhân viên kho (WAREHOUSE)!");
            }
            return new LoginResult(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("full_name"),
                    role, true, null);
        } catch (SQLException e) {
            return new LoginResult(0, "", "", "", false, "Lỗi kết nối CSDL: " + e.getMessage());
        }
    }
}
