package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class KeywordSearchFrame extends JFrame {
    private JButton btnChooseFile;
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JTextArea txtResults;
    private JLabel lblFile;
    private JLabel lblStatus;
    private JProgressBar progressBar;
    private File selectedFile;

    public KeywordSearchFrame() {
        setTitle("Bài 7 - Tìm kiếm từ khóa trong file văn bản lớn");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChooseFile = new JButton("Chọn file .txt");
        txtKeyword = new JTextField(15);
        btnSearch = new JButton("Tìm kiếm");
        lblFile = new JLabel("File: Chưa chọn", SwingConstants.LEFT);
        lblStatus = new JLabel("Kết quả tìm kiếm: 0 dòng", SwingConstants.LEFT);

        txtResults = new JTextArea();
        txtResults.setEditable(false);
        txtResults.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(txtResults);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.add(btnChooseFile);
        topPanel.add(new JLabel("Từ khóa:"));
        topPanel.add(txtKeyword);
        topPanel.add(btnSearch);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(lblFile, BorderLayout.NORTH);
        infoPanel.add(progressBar, BorderLayout.SOUTH);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(infoPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(lblStatus, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnChooseFile.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> searchKeyword());

        // Default test file if present
        File defaultFile = new File("data/sample_large.txt");
        if (defaultFile.exists()) {
            selectedFile = defaultFile;
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
            txtKeyword.setText("SwingWorker");
        }
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser(new File("."));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file văn bản trước", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa cần tìm", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnSearch.setEnabled(false);
        btnChooseFile.setEnabled(false);
        txtResults.setText("");
        lblStatus.setText("Đang tìm kiếm từ khóa '" + keyword + "'...");
        progressBar.setValue(0);

        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                int lineNum = 0;
                int matchCount = 0;

                String lowerKeyword = keyword.toLowerCase();

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNum++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;

                        if (line.toLowerCase().contains(lowerKeyword)) {
                            matchCount++;
                            publish("Dòng " + lineNum + ": " + line);
                        }

                        int progress = totalBytes == 0 ? 100 : (int) Math.min(100, (readBytes * 100 / totalBytes));
                        setProgress(progress);
                        if (totalBytes < 100000) {
                            Thread.sleep(10);
                        }
                    }
                }
                return matchCount;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String text : chunks) {
                    txtResults.append(text + "\n");
                }
            }

            @Override
            protected void done() {
                try {
                    int count = get();
                    lblStatus.setText("Tìm thấy " + count + " dòng chứa từ khóa '" + keyword + "' (không phân biệt hoa/thường).");
                } catch (Exception ex) {
                    lblStatus.setText("Có lỗi xảy ra khi đọc và tìm kiếm file: " + ex.getMessage());
                }
                progressBar.setValue(100);
                btnSearch.setEnabled(true);
                btnChooseFile.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new KeywordSearchFrame().setVisible(true));
    }
}
