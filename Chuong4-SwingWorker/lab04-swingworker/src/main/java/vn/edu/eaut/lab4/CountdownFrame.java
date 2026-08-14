package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CountdownFrame extends JFrame {
    private JTextField txtSeconds;
    private JButton btnStart;
    private JLabel lblTime;

    public CountdownFrame() {
        setTitle("Bài 1 - Đồng hồ đếm ngược");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtSeconds = new JTextField(10);
        txtSeconds.setText("10");
        btnStart = new JButton("Bắt đầu");
        lblTime = new JLabel("Thời gian còn lại: ", SwingConstants.CENTER);
        lblTime.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Nhập số giây:"));
        inputPanel.add(txtSeconds);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(inputPanel);
        panel.add(btnStart);
        panel.add(lblTime);
        add(panel);

        btnStart.addActionListener(e -> startCountdown());
    }

    private void startCountdown() {
        int seconds;
        try {
            seconds = Integer.parseInt(txtSeconds.getText().trim());
            if (seconds <= 0) {
                JOptionPane.showMessageDialog(this, "Số giây phải lớn hơn 0", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        btnStart.setEnabled(false);
        lblTime.setText("Thời gian còn lại: " + seconds + " giây");

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = seconds; i >= 0; i--) {
                    publish(i);
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                lblTime.setText("Thời gian còn lại: " + value + " giây");
            }

            @Override
            protected void done() {
                btnStart.setEnabled(true);
                JOptionPane.showMessageDialog(CountdownFrame.this, "Hoàn thành!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CountdownFrame().setVisible(true));
    }
}
