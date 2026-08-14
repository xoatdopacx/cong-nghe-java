package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class MainDashboardFrame extends JFrame {

    public MainDashboardFrame() {
        setTitle("Lab 4 - SwingWorker & Event Handling - Đại học Công nghệ Đông Á");
        setSize(720, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("HỌC PHẦN CÔNG NGHỆ JAVA - BÀI THỰC HÀNH LAB 4", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Chương 2: Xử lý sự kiện, Event Dispatch Thread (EDT) & SwingWorker", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(new Color(236, 240, 241));

        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(lblSub, BorderLayout.SOUTH);

        // Grid of 10 exercise launcher buttons
        JPanel gridPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        gridPanel.setBorder(BorderFactory.createTitledBorder("Danh sách 10 bài tập thực hành Lab 4"));

        JButton b1 = createExerciseButton("Bài 1: Đồng hồ đếm ngược", () -> new CountdownFrame().setVisible(true));
        JButton b2 = createExerciseButton("Bài 2: Mô phỏng tải dữ liệu", () -> new ProgressDemoFrame().setVisible(true));
        JButton b3 = createExerciseButton("Bài 3: Tính tổng số nguyên tố < N", () -> new PrimeSumFrame().setVisible(true));
        JButton b4 = createExerciseButton("Bài 4: Tìm Fibonacci thứ N (Memoization)", () -> new FibonacciFrame().setVisible(true));
        JButton b5 = createExerciseButton("Bài 5: Đọc file lớn & đếm số dòng", () -> new FileLineCounterFrame().setVisible(true));

        JButton b6 = createExerciseButton("Bài 6 (Tự làm): Hủy tác vụ (Cancelable)", () -> new CancelableTaskFrame().setVisible(true));
        JButton b7 = createExerciseButton("Bài 7 (Tự làm): Tìm từ khóa trong file lớn", () -> new KeywordSearchFrame().setVisible(true));
        JButton b8 = createExerciseButton("Bài 8 (Tự làm): Thống kê file CSV sinh viên", () -> new StudentCsvStatsFrame().setVisible(true));
        JButton b9 = createExerciseButton("Bài 9 (Tự làm): Mô phỏng tải sản phẩm", () -> new ProductLoadDemoFrame().setVisible(true));
        JButton b10 = createExerciseButton("Bài 10 (Tự làm): Quản lý sản phẩm CSV", () -> new ProductManagerFrame().setVisible(true));

        gridPanel.add(b1); gridPanel.add(b6);
        gridPanel.add(b2); gridPanel.add(b7);
        gridPanel.add(b3); gridPanel.add(b8);
        gridPanel.add(b4); gridPanel.add(b9);
        gridPanel.add(b5); gridPanel.add(b10);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createExerciseButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> SwingUtilities.invokeLater(action));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainDashboardFrame().setVisible(true));
    }
}
