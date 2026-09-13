package vn.edu.eaut.customer.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tiện ích kết nối JDBC - Customer Portal (Jakarta EE)
 * Kết nối trực tiếp đến cơ sở dữ liệu dùng chung java_integrated_lab
 */
public class DBUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/java_integrated_lab" +
            "?useSSL=false&allowPublicKeyRetrieval=true" +
            "&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(URL, USER, PASS);
    }
}
