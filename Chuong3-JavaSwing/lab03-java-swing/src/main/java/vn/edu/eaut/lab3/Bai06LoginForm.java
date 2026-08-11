package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bai06LoginForm extends JFrame {

    private final JTextField     txtUser  = new JTextField(15);
    private final JPasswordField txtPass  = new JPasswordField(15);
    private final JComboBox<String> cmbRole = new JComboBox<>(new String[]{"Admin", "User"});
    private final JCheckBox      chkShow  = new JCheckBox("Hiển thị mật khẩu");

    // Tài khoản hợp lệ
    private static final String[][] ACCOUNTS = {
        {"admin", "123456", "Admin"},
        {"user",  "123456", "User"},
    };

    public Bai06LoginForm() {
        setTitle("Bài 6 - Form Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel title = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(new Color(31, 78, 121));
        main.add(title, BorderLayout.NORTH);

        // Form fields
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.add(new JLabel("Tài khoản:")); form.add(txtUser);
        form.add(new JLabel("Mật khẩu:")); form.add(txtPass);
        form.add(new JLabel("Vai trò:"));   form.add(cmbRole);
        form.add(new JLabel(""));           form.add(chkShow);
        main.add(form, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnReset = new JButton("Xóa");
        btnPanel.add(btnLogin);
        btnPanel.add(btnReset);
        main.add(btnPanel, BorderLayout.SOUTH);

        // Events
        btnLogin.addActionListener(e -> dangNhap());
        btnReset.addActionListener(e -> reset());
        chkShow.addActionListener(e ->
            txtPass.setEchoChar(chkShow.isSelected() ? (char) 0 : '●'));
        getRootPane().setDefaultButton(btnLogin);

        setContentPane(main);
        pack();
        setSize(370, 260);
        setLocationRelativeTo(null);
    }

    private void dangNhap() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        String role = (String) cmbRole.getSelectedItem();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (String[] acc : ACCOUNTS) {
            if (acc[0].equals(user) && acc[1].equals(pass) && acc[2].equals(role)) {
                JOptionPane.showMessageDialog(this,
                    "Chào mừng " + role + " " + user + "!\nĐăng nhập thành công.",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(this,
            "Tài khoản, mật khẩu hoặc vai trò không đúng!\nVui lòng thử lại.",
            "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
        txtPass.setText("");
        txtPass.requestFocus();
    }

    private void reset() {
        txtUser.setText(""); txtPass.setText("");
        cmbRole.setSelectedIndex(0);
        chkShow.setSelected(false);
        txtPass.setEchoChar('●');
        txtUser.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai06LoginForm().setVisible(true));
    }
}
