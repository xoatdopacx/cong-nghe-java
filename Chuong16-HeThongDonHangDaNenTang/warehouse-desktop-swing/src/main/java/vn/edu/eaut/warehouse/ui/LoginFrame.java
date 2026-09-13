package vn.edu.eaut.warehouse.ui;

import vn.edu.eaut.warehouse.dao.UserDAO;
import vn.edu.eaut.warehouse.dao.UserDAO.LoginResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Màn hình đăng nhập của ứng dụng kho (Java Swing)
 */
public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Đăng nhập - Hệ thống Kho | Lab 16");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background panel
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(15, 15, 26));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(26, 26, 46));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 20), 1),
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));
        card.setPreferredSize(new Dimension(360, 360));

        // Icon
        JLabel icon = new JLabel("🖥️", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Warehouse Desktop");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(99, 102, 241));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Lab 16 · Java Swing · JDBC");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(100, 116, 139));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username
        JLabel lblUser = makeLabel("Tên đăng nhập");
        txtUsername = makeTextField("warehouse01");
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        // Password
        JLabel lblPass = makeLabel("Mật khẩu");
        txtPassword = new JPasswordField("123456");
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtPassword.setBackground(new Color(15, 15, 26));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 30)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        // Error label
        lblError = new JLabel(" ");
        lblError.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblError.setForeground(new Color(239, 68, 68));
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login button
        JButton btnLogin = new JButton("Đăng nhập →");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setBackground(new Color(99, 102, 241));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> doLogin());
        txtPassword.addActionListener(e -> doLogin());

        // Hover effect
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnLogin.setBackground(new Color(79, 70, 229)); }
            @Override public void mouseExited(MouseEvent e)  { btnLogin.setBackground(new Color(99, 102, 241)); }
        });

        // Assemble card
        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));
        card.add(lblUser);
        card.add(Box.createVerticalStrut(4));
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(12));
        card.add(lblPass);
        card.add(Box.createVerticalStrut(4));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(8));
        card.add(lblError);
        card.add(Box.createVerticalStrut(16));
        card.add(btnLogin);

        bg.add(card);
        setContentPane(bg);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Vui lòng nhập đủ thông tin!");
            return;
        }

        lblError.setText("Đang xác thực...");
        lblError.setForeground(new Color(99, 102, 241));

        // Chạy trong background thread
        SwingWorker<LoginResult, Void> worker = new SwingWorker<>() {
            @Override
            protected LoginResult doInBackground() {
                return new UserDAO().authenticate(username, password);
            }

            @Override
            protected void done() {
                try {
                    LoginResult result = get();
                    if (result.success()) {
                        dispose();
                        new MainFrame(result).setVisible(true);
                    } else {
                        lblError.setForeground(new Color(239, 68, 68));
                        lblError.setText(result.errorMsg());
                    }
                } catch (Exception ex) {
                    lblError.setForeground(new Color(239, 68, 68));
                    lblError.setText("Lỗi: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 116, 139));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField tf = new JTextField(placeholder);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setBackground(new Color(15, 15, 26));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 30)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return tf;
    }
}
