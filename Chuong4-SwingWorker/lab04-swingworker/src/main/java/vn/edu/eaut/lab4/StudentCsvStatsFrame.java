package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StudentCsvStatsFrame extends JFrame {
    private JButton btnChoose;
    private JButton btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblFile;
    private JLabel lblStats;
    private JProgressBar progressBar;
    private File selectedFile;

    public static class Student {
        private String maSV;
        private String hoTen;
        private double diem;

        public Student(String maSV, String hoTen, double diem) {
            this.maSV = maSV;
            this.hoTen = hoTen;
            this.diem = diem;
        }

        public String getMaSV() { return maSV; }
        public String getHoTen() { return hoTen; }
        public double getDiem() { return diem; }
    }

    public StudentCsvStatsFrame() {
        setTitle("Bài 8 - Đọc file CSV điểm sinh viên và thống kê");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChoose = new JButton("Chọn file CSV");
        btnLoad = new JButton("Tải dữ liệu");
        lblFile = new JLabel("File: Chưa chọn", SwingConstants.LEFT);
        lblStats = new JLabel("Thống kê: Chưa có dữ liệu", SwingConstants.LEFT);
        lblStats.setFont(new Font("Arial", Font.BOLD, 13));

        String[] columns = {"Mã SV", "Họ và Tên", "Điểm số"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.add(btnChoose);
        topPanel.add(btnLoad);
        topPanel.add(lblFile);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(progressBar, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(lblStats, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnChoose.addActionListener(e -> chooseFile());
        btnLoad.addActionListener(e -> loadStudentData());

        // Default auto-select data/students.csv
        File defaultFile = new File("data/students.csv");
        if (defaultFile.exists()) {
            selectedFile = defaultFile;
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
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

    private void loadStudentData() {
        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file CSV trước", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnLoad.setEnabled(false);
        btnChoose.setEnabled(false);
        tableModel.setRowCount(0);
        lblStats.setText("Đang đọc file và tính toán thống kê...");
        progressBar.setValue(0);

        SwingWorker<List<Student>, Student> worker = new SwingWorker<>() {
            @Override
            protected List<Student> doInBackground() throws Exception {
                List<Student> list = new ArrayList<>();
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line = reader.readLine(); // Skip header row
                    if (line != null) {
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                    }

                    while ((line = reader.readLine()) != null) {
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        if (!line.trim().isEmpty()) {
                            String[] parts = line.split(",");
                            if (parts.length >= 3) {
                                String ma = parts[0].trim();
                                String name = parts[1].trim();
                                double diem = Double.parseDouble(parts[2].trim());
                                Student st = new Student(ma, name, diem);
                                list.add(st);
                                publish(st);
                            }
                        }

                        int progress = totalBytes == 0 ? 100 : (int) Math.min(100, (readBytes * 100 / totalBytes));
                        setProgress(progress);
                        Thread.sleep(150); // Small delay to observe row stream & progress
                    }
                }
                return list;
            }

            @Override
            protected void process(List<Student> chunks) {
                for (Student st : chunks) {
                    tableModel.addRow(new Object[]{st.getMaSV(), st.getHoTen(), st.getDiem()});
                }
            }

            @Override
            protected void done() {
                try {
                    List<Student> list = get();
                    if (list.isEmpty()) {
                        lblStats.setText("Không có dữ liệu sinh viên trong file.");
                    } else {
                        double sum = 0;
                        Student maxSt = list.get(0);
                        for (Student st : list) {
                            sum += st.getDiem();
                            if (st.getDiem() > maxSt.getDiem()) {
                                maxSt = st;
                            }
                        }
                        double avg = sum / list.size();
                        lblStats.setText(String.format("Thống kê: Đã nạp %d sinh viên | Điểm TB: %.2f | Cao nhất: %s (%.1f điểm)",
                                list.size(), avg, maxSt.getHoTen(), maxSt.getDiem()));
                    }
                } catch (Exception ex) {
                    lblStats.setText("Có lỗi khi nạp file CSV: " + ex.getMessage());
                }
                progressBar.setValue(100);
                btnLoad.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new StudentCsvStatsFrame().setVisible(true));
    }
}
