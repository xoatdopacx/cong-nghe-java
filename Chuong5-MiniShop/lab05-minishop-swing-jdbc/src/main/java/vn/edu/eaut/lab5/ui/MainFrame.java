package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private SanPhamPanel sanPhamPanel;
    private KhachHangPanel khachHangPanel;
    private HoaDonPanel hoaDonPanel;
    private ThongKePanel thongKePanel;
    private DanhMucPanel danhMucPanel;

    private final TaiKhoan currentUser;

    public MainFrame() {
        this(new TaiKhoan("admin", "123456", "Quản Trị Viên (Mặc định)", "ADMIN"));
    }

    public MainFrame(TaiKhoan user) {
        this.currentUser = user;

        setTitle("MiniShop Management System - Java Swing & JDBC (Lab 5 - EAUT)");
        setSize(980, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header User Info Bar
        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(new Color(41, 128, 185));
        topHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ BÁN HÀNG MINISHOP", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        String roleBadge = user != null ? user.getVaiTro() : "GUEST";
        String userName = user != null ? user.getHoTen() : "Khách";
        JLabel lblUser = new JLabel("Xin chào: " + userName + " [" + roleBadge + "]  ", SwingConstants.RIGHT);
        lblUser.setFont(new Font("Arial", Font.BOLD, 13));
        lblUser.setForeground(new Color(241, 196, 15));

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            this.dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });

        JPanel rightUserPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightUserPanel.setOpaque(false);
        rightUserPanel.add(lblUser);
        rightUserPanel.add(btnLogout);

        topHeader.add(lblTitle, BorderLayout.WEST);
        topHeader.add(rightUserPanel, BorderLayout.EAST);

        // JTabbedPane Section (Section 10 of prompt)
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));

        sanPhamPanel = new SanPhamPanel();
        khachHangPanel = new KhachHangPanel();
        hoaDonPanel = new HoaDonPanel(user);
        thongKePanel = new ThongKePanel();
        danhMucPanel = new DanhMucPanel();

        // Role-based Access Control (Bài 10)
        String role = user != null ? user.getVaiTro().toUpperCase() : "ADMIN";

        if ("ADMIN".equals(role)) {
            tabbedPane.addTab("📦 Quản Lý Sản Phẩm", sanPhamPanel);
            tabbedPane.addTab("👥 Quản Lý Khách Hàng", khachHangPanel);
            tabbedPane.addTab("🧾 Lập Hóa Đơn & Bán Hàng", hoaDonPanel);
            tabbedPane.addTab("📊 Thống Kê Doanh Thu", thongKePanel);
            tabbedPane.addTab("🏷️ Quản Lý Danh Mục", danhMucPanel);
        } else if ("NHANVIEN".equals(role)) {
            tabbedPane.addTab("📦 Quản Lý Sản Phẩm", sanPhamPanel);
            tabbedPane.addTab("👥 Quản Lý Khách Hàng", khachHangPanel);
            tabbedPane.addTab("🧾 Lập Hóa Đơn & Bán Hàng", hoaDonPanel);
        } else if ("KETOAN".equals(role)) {
            tabbedPane.addTab("🧾 Lập Hóa Đơn & Xem Hóa Đơn", hoaDonPanel);
            tabbedPane.addTab("📊 Thống Kê Doanh Thu", thongKePanel);
        } else {
            tabbedPane.addTab("📦 Quản Lý Sản Phẩm", sanPhamPanel);
            tabbedPane.addTab("👥 Quản Lý Khách Hàng", khachHangPanel);
            tabbedPane.addTab("🧾 Lập Hóa Đơn & Bán Hàng", hoaDonPanel);
            tabbedPane.addTab("📊 Thống Kê Doanh Thu", thongKePanel);
        }

        add(topHeader, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
