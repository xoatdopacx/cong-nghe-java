package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileLineCounterFrame extends JFrame {
    private JButton btnChoose;
    private JButton btnCount;
    private JLabel lblFile;
    private JLabel lblResult;
    private JProgressBar progressBar;
    private File selectedFile;

    public FileLineCounterFrame() {
        setTitle("Bài 5 - Đọc file lớn và đếm số dòng");
        setSize(550, 240);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChoose = new JButton("Chọn file");
        btnCount = new JButton("Đếm dòng");
        btnCount.setEnabled(false);
        lblFile = new JLabel("File: Chưa chọn file", SwingConstants.LEFT);
        lblResult = new JLabel("Kết quả: ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Arial", Font.BOLD, 14));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnChoose);
        topPanel.add(btnCount);
        topPanel.add(lblFile);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(topPanel);
        mainPanel.add(progressBar);
        mainPanel.add(lblResult);
        add(mainPanel);

        btnChoose.addActionListener(e -> chooseFile());
        btnCount.addActionListener(e -> countLines());

        // Default auto-select sample file if present
        File defaultFile = new File("data/sample_large.txt");
        if (defaultFile.exists()) {
            selectedFile = defaultFile;
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
            btnCount.setEnabled(true);
        }
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser(new File("."));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
            btnCount.setEnabled(true);
        }
    }

    private void countLines() {
        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file hợp lệ trước", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnCount.setEnabled(false);
        btnChoose.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Đang đọc file...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                long lines = 0;

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1; // +1 for newline character
                        int progress = totalBytes == 0 ? 100 : (int) Math.min(100, (readBytes * 100 / totalBytes));
                        setProgress(progress);
                        // Brief delay to visualize progress for small files
                        if (totalBytes < 100000) {
                            Thread.sleep(10);
                        }
                    }
                }
                return lines;
            }

            @Override
            protected void done() {
                try {
                    long lineCount = get();
                    lblResult.setText("Số dòng trong file: " + lineCount);
                } catch (Exception ex) {
                    lblResult.setText("Lỗi khi đọc file: " + ex.getMessage());
                }
                progressBar.setValue(100);
                btnCount.setEnabled(true);
                btnChoose.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileLineCounterFrame().setVisible(true));
    }
}
