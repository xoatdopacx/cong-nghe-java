package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai03PhuongTrinhBacNhat extends JFrame {

    private final JTextField txtA      = new JTextField();
    private final JTextField txtB      = new JTextField();
    private final JLabel     lblResult = new JLabel("Nghiệm: ");

    public Bai03PhuongTrinhBacNhat() {
        setTitle("Bài 3 - Giải phương trình ax + b = 0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        inputPanel.add(new JLabel("Hệ số a:")); inputPanel.add(txtA);
        inputPanel.add(new JLabel("Hệ số b:")); inputPanel.add(txtB);

        JButton btnSolve = new JButton("Giải phương trình");
        btnSolve.addActionListener(e -> giaiPhuongTrinh());

        JPanel southPanel = new JPanel(new BorderLayout(5, 5));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        southPanel.add(btnSolve, BorderLayout.CENTER);
        southPanel.add(lblResult, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(420, 200);
        setLocationRelativeTo(null);
    }

    private void giaiPhuongTrinh() {
        try {
            double a   = Double.parseDouble(txtA.getText().trim());
            double b   = Double.parseDouble(txtB.getText().trim());
            final double EPS = 1e-9;
            if (Math.abs(a) < EPS && Math.abs(b) < EPS) {
                lblResult.setText("Nghiệm: Phương trình có vô số nghiệm");
            } else if (Math.abs(a) < EPS) {
                lblResult.setText("Nghiệm: Phương trình vô nghiệm");
            } else {
                double x = -b / a;
                lblResult.setText(String.format("Nghiệm: x = %.4f", x));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập a, b là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai03PhuongTrinhBacNhat().setVisible(true));
    }
}
