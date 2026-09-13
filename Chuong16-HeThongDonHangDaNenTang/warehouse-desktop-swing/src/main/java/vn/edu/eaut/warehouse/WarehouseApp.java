package vn.edu.eaut.warehouse;

import vn.edu.eaut.warehouse.ui.LoginFrame;

import javax.swing.*;

/**
 * Entry point ứng dụng Kho hàng - Lab 16
 * Vai trò: Nhân viên kho (WAREHOUSE) tiếp nhận và xử lý đơn hàng
 */
public class WarehouseApp {
    public static void main(String[] args) {
        // Dùng FlatLaF hoặc System look
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
