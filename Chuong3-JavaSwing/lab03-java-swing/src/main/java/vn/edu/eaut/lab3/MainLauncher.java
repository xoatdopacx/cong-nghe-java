package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

/**
 * MainLauncher – Menu chọn bài Lab 3 Java Swing
 * Chạy: java -jar target/lab03-java-swing-1.0-SNAPSHOT.jar
 */
public class MainLauncher extends JFrame {

    public MainLauncher() {
        setTitle("Lab 3 – Java Swing – Nguyễn Văn Hùng (20230752)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("LAB 3 – JAVA SWING", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setForeground(new Color(31, 78, 121));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(header, BorderLayout.NORTH);

        JLabel sub = new JLabel("Nguyễn Văn Hùng – MSSV: 20230752", SwingConstants.CENTER);
        sub.setFont(new Font("SansSerif", Font.ITALIC, 12));
        sub.setForeground(Color.GRAY);

        // Button panel
        JPanel btnPanel = new JPanel(new GridLayout(8, 1, 8, 8));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        String[][] bais = {
            {"Bài 1 – Chào người dùng",              "Bai01HelloSwing"},
            {"Bài 2 – Tính tổng hai số",              "Bai02TongHaiSo"},
            {"Bài 3 – Giải phương trình bậc nhất",    "Bai03PhuongTrinhBacNhat"},
            {"Bài 4 – Kiểm tra và phân loại tam giác", "Bai04TamGiacSwing"},
            {"Bài 5 – Hiển thị dãy Fibonacci",        "Bai05FibonacciSwing"},
            {"Bài 6 – Form đăng nhập",                "Bai06LoginForm"},
            {"Bài 7 – Máy tính mini",                 "Bai07MayTinhMini"},
            {"Bài 8 – Quản lý sinh viên (JTable)",    "Bai08QuanLySinhVien"},
        };

        for (int i = 0; i < bais.length; i++) {
            String label    = bais[i][0];
            String className = bais[i][1];
            JButton btn = new JButton(label);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            // Màu xen kẽ
            btn.setBackground(i % 2 == 0 ? new Color(235, 244, 255) : new Color(255, 255, 255));
            btn.setFocusPainted(false);
            btn.addActionListener(e -> moaBai(className));
            btnPanel.add(btn);
        }

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(sub,      BorderLayout.NORTH);
        center.add(btnPanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("Nhấn vào bài để mở cửa sổ Swing tương ứng", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 11));
        footer.setForeground(Color.GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(footer, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(400, 480));
        setLocationRelativeTo(null);
    }

    private void moaBai(String className) {
        switch (className) {
            case "Bai01HelloSwing"          -> new Bai01HelloSwing().setVisible(true);
            case "Bai02TongHaiSo"           -> new Bai02TongHaiSo().setVisible(true);
            case "Bai03PhuongTrinhBacNhat"  -> new Bai03PhuongTrinhBacNhat().setVisible(true);
            case "Bai04TamGiacSwing"        -> new Bai04TamGiacSwing().setVisible(true);
            case "Bai05FibonacciSwing"      -> new Bai05FibonacciSwing().setVisible(true);
            case "Bai06LoginForm"           -> new Bai06LoginForm().setVisible(true);
            case "Bai07MayTinhMini"         -> new Bai07MayTinhMini().setVisible(true);
            case "Bai08QuanLySinhVien"      -> new Bai08QuanLySinhVien().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainLauncher().setVisible(true));
    }
}
