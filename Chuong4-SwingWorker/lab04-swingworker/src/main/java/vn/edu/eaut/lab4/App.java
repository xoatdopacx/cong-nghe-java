package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // Mặc định khởi chạy MainDashboardFrame chứa toàn bộ 10 bài tập
            new MainDashboardFrame().setVisible(true);
        });
    }
}
