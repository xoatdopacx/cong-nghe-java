package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.TaiKhoanBUS;
import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private final TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

    public LoginFrame() {
        setTitle("Đăng nhập Hệ thống MiniShop - EAUT Lab 5");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Header Title
        JLabel lblHeader = new JLabel("ĐĂNG NHẬP MINISHOP", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeader.setForeground(new Color(41, 128, 185));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Form Fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(16);
        txtUsername.setText("admin"); // Default test account
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(16);
        txtPassword.setText("123456");
        formPanel.add(txtPassword, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));

        btnPanel.add(btnLogin);
        btnPanel.add(btnExit);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Actions
        btnLogin.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));
        getRootPane().setDefaultButton(btnLogin);
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        try {
            TaiKhoan tk = taiKhoanBUS.login(username, password);
            if (tk != null) {
                MessageUtil.showInfo(this, "Đăng nhập thành công với vai trò: " + tk.getVaiTro() + "\nXin chào " + tk.getHoTen() + "!");
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    MainFrame mainFrame = new MainFrame(tk);
                    mainFrame.setVisible(true);
                });
            } else {
                MessageUtil.showError(this, "Sai tên đăng nhập hoặc mật khẩu!");
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi đăng nhập: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
