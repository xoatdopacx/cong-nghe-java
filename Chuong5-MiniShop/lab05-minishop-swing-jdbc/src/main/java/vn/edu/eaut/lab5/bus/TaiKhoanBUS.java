package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.TaiKhoanDAL;
import vn.edu.eaut.lab5.model.TaiKhoan;

import java.sql.SQLException;
import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAL taiKhoanDAL = new TaiKhoanDAL();

    public TaiKhoan login(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập tên đăng nhập");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập mật khẩu");
        }
        return taiKhoanDAL.login(username.trim(), password.trim());
    }

    public List<TaiKhoan> findAll() throws SQLException {
        return taiKhoanDAL.findAll();
    }

    public boolean save(TaiKhoan tk, boolean isNew) throws SQLException {
        if (tk.getUsername() == null || tk.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được rỗng");
        }
        if (tk.getPassword() == null || tk.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        if (tk.getHoTen() == null || tk.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được rỗng");
        }
        if (isNew) {
            return taiKhoanDAL.insert(tk);
        }
        return taiKhoanDAL.update(tk);
    }

    public boolean delete(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tài khoản không hợp lệ");
        }
        return taiKhoanDAL.delete(username);
    }
}
