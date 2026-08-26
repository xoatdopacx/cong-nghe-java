package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

/**
 * Bài 2: AuthService - xử lý logic xác thực và quản lý tài khoản.
 */
public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    /**
     * Xác thực đăng nhập bằng email và mật khẩu.
     * @return User nếu đăng nhập thành công, null nếu thất bại
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.isActive()) return null;
        if (!user.getPassword().equals(password)) return null;
        return user;
    }

    /**
     * Bài 9: Đổi mật khẩu.
     * @return thông báo lỗi hoặc null nếu thành công
     */
    public String changePassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return "Mật khẩu mới không được để trống";
        }
        if (newPassword.length() < 4) {
            return "Mật khẩu mới phải có ít nhất 4 ký tự";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "Xác nhận mật khẩu không khớp";
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "Không tìm thấy tài khoản";
        }
        if (!user.getPassword().equals(oldPassword)) {
            return "Mật khẩu cũ không đúng";
        }

        user.setPassword(newPassword);
        userRepository.update(user);
        return null; // thành công
    }

    /**
     * Bài 8: Cập nhật hồ sơ cá nhân.
     * @return thông báo lỗi hoặc null nếu thành công
     */
    public String updateProfile(Integer userId, String fullName, String email) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ tên không được để trống";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống";
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Email không đúng định dạng";
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "Không tìm thấy tài khoản";
        }

        // Kiểm tra email trùng với user khác
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null && !existingUser.getId().equals(userId)) {
            return "Email đã được sử dụng bởi tài khoản khác";
        }

        user.setFullName(fullName.trim());
        user.setEmail(email.trim());
        userRepository.update(user);
        return null; // thành công
    }
}
