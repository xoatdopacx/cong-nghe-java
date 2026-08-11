package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai07MayTinhMini extends JFrame {

    private final JTextField txtA       = new JTextField();
    private final JTextField txtB       = new JTextField();
    private final JTextField txtResult  = new JTextField();
    private final JTextArea  txtHistory = new JTextArea(8, 30);

    public Bai07MayTinhMini() {
        setTitle("Bài 7 - Máy tính mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập số"));
        inputPanel.add(new JLabel("Số thứ nhất (A):")); inputPanel.add(txtA);
        inputPanel.add(new JLabel("Số thứ hai (B):"));  inputPanel.add(txtB);
        inputPanel.add(new JLabel("Kết quả:"));
        txtResult.setEditable(false);
        txtResult.setBackground(new Color(240, 248, 255));
        txtResult.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        inputPanel.add(txtResult);

        // Buttons panel
        JPanel btnPanel = new JPanel(new GridLayout(1, 5, 6, 6));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        String[] ops = {"+", "-", "×", "÷", "Clear"};
        for (String op : ops) {
            JButton btn = new JButton(op);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            if (op.equals("Clear")) {
                btn.setBackground(new Color(220, 80, 60));
                btn.setForeground(Color.WHITE);
                btn.addActionListener(e -> clear());
            } else {
                btn.addActionListener(e -> calculate(op));
            }
            btnPanel.add(btn);
        }

        // History panel
        txtHistory.setEditable(false);
        txtHistory.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtHistory);
        scroll.setBorder(BorderFactory.createTitledBorder("Lịch sử tính toán"));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel,   BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scroll,   BorderLayout.CENTER);

        setSize(420, 430);
        setLocationRelativeTo(null);
    }

    private void calculate(String op) {
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            double result;
            String symbol;

            switch (op) {
                case "+"  -> { result = a + b; symbol = "+"; }
                case "-"  -> { result = a - b; symbol = "-"; }
                case "×"  -> { result = a * b; symbol = "×"; }
                case "÷"  -> {
                    if (Math.abs(b) < 1e-12) {
                        JOptionPane.showMessageDialog(this, "Lỗi: Không thể chia cho 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    result = a / b; symbol = "÷";
                }
                default   -> { return; }
            }
            String line = String.format("%.4f %s %.4f = %.4f", a, symbol, b, result);
            txtResult.setText(String.format("%.4f", result));
            txtHistory.append(line + "\n");
            txtHistory.setCaretPosition(txtHistory.getDocument().getLength());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clear() {
        txtA.setText(""); txtB.setText(""); txtResult.setText("");
        txtA.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai07MayTinhMini().setVisible(true));
    }
}
