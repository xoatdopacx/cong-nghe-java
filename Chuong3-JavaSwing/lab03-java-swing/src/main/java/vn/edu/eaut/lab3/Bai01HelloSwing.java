package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai01HelloSwing extends JFrame {

    private final JTextField txtName = new JTextField(20);

    public Bai01HelloSwing() {
        setTitle("Bài 1 - Chào người dùng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        add(new JLabel("Nhập tên:"));
        add(txtName);

        JButton btnHello = new JButton("Hiển thị lời chào");
        add(btnHello);
        btnHello.addActionListener(e -> hienThiLoiChao());

        // Enter key cũng kích hoạt
        txtName.addActionListener(e -> hienThiLoiChao());

        pack();
        setLocationRelativeTo(null);
    }

    private void hienThiLoiChao() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return;
        }
        JOptionPane.showMessageDialog(this, "Xin chào, " + name + "!", "Lời chào", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai01HelloSwing().setVisible(true));
    }
}
