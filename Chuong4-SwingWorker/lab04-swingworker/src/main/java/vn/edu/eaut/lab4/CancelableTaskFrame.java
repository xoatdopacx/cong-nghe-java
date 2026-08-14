package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class CancelableTaskFrame extends JFrame {
    private JButton btnStart;
    private JButton btnCancel;
    private JProgressBar progressBar;
    private JLabel lblStatus;
    private SwingWorker<Void, Integer> worker;

    public CancelableTaskFrame() {
        setTitle("Bài 6 - Bổ sung chức năng hủy tác vụ (Cancelable Task)");
        setSize(480, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnStart = new JButton("Bắt đầu tác vụ");
        btnCancel = new JButton("Hủy tác vụ");
        btnCancel.setEnabled(false);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        lblStatus = new JLabel("Trạng thái: Sẵn sàng", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnStart);
        btnPanel.add(btnCancel);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(btnPanel);
        mainPanel.add(progressBar);
        mainPanel.add(lblStatus);
        add(mainPanel);

        btnStart.addActionListener(e -> startTask());
        btnCancel.addActionListener(e -> cancelTask());
    }

    private void startTask() {
        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        progressBar.setValue(0);
        lblStatus.setText("Trạng thái: Đang xử lý tác vụ...");

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 1; i <= 100; i++) {
                    if (isCancelled()) {
                        return null;
                    }
                    Thread.sleep(100); // Simulate 10s task step by step
                    setProgress(i);
                }
                return null;
            }

            @Override
            protected void done() {
                if (isCancelled()) {
                    lblStatus.setText("Trạng thái: Đã hủy tác vụ!");
                    lblStatus.setForeground(Color.RED);
                    progressBar.setValue(0);
                } else {
                    lblStatus.setText("Trạng thái: Hoàn thành tác vụ!");
                    lblStatus.setForeground(new Color(0, 128, 0));
                    progressBar.setValue(100);
                }
                btnStart.setEnabled(true);
                btnCancel.setEnabled(false);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private void cancelTask() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancelableTaskFrame().setVisible(true));
    }
}
