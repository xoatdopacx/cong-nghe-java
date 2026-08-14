package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductManagerFrame extends JFrame {
    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnReadCsv;
    private JButton btnSaveCsv;

    private JTable table;
    private DefaultTableModel tableModel;
    private JProgressBar progressBar;
    private JLabel lblStatus;

    private File currentCsvFile;
    private List<Product> productList = new ArrayList<>();

    public static class Product {
        private String maSP;
        private String tenSP;
        private double donGia;

        public Product(String maSP, String tenSP, double donGia) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.donGia = donGia;
        }

        public String getMaSP() { return maSP; }
        public void setMaSP(String maSP) { this.maSP = maSP; }
        public String getTenSP() { return tenSP; }
        public void setTenSP(String tenSP) { this.tenSP = tenSP; }
        public double getDonGia() { return donGia; }
        public void setDonGia(double donGia) { this.donGia = donGia; }
    }

    public ProductManagerFrame() {
        setTitle("Bài 10 - Mini Project: Quản lý sản phẩm bằng file CSV (SwingWorker)");
        setSize(780, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inputs
        txtMaSP = new JTextField(12);
        txtTenSP = new JTextField(20);
        txtDonGia = new JTextField(12);

        // Buttons
        btnAdd = new JButton("Thêm sản phẩm");
        btnEdit = new JButton("Sửa sản phẩm");
        btnDelete = new JButton("Xóa sản phẩm");
        btnClear = new JButton("Làm mới");
        btnReadCsv = new JButton("Đọc file CSV");
        btnSaveCsv = new JButton("Lưu file CSV");

        // Table
        String[] columns = {"Mã SP", "Tên sản phẩm", "Đơn giá (VNĐ)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        lblStatus = new JLabel("Trạng thái: Sẵn sàng", SwingConstants.LEFT);

        // Input Form Layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMaSP, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtTenSP, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDonGia, gbc);

        // CRUD Button Panel
        JPanel crudBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        crudBtnPanel.add(btnAdd);
        crudBtnPanel.add(btnEdit);
        crudBtnPanel.add(btnDelete);
        crudBtnPanel.add(btnClear);

        // File IO Button Panel
        JPanel fileBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        fileBtnPanel.add(btnReadCsv);
        fileBtnPanel.add(btnSaveCsv);

        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.add(crudBtnPanel, BorderLayout.WEST);
        actionPanel.add(fileBtnPanel, BorderLayout.EAST);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        northPanel.add(formPanel, BorderLayout.NORTH);
        northPanel.add(actionPanel, BorderLayout.SOUTH);

        JPanel southPanel = new JPanel(new BorderLayout(5, 5));
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        southPanel.add(progressBar, BorderLayout.NORTH);
        southPanel.add(lblStatus, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnAdd.addActionListener(e -> addProduct());
        btnEdit.addActionListener(e -> editProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearForm());
        btnReadCsv.addActionListener(e -> readCsvAsync());
        btnSaveCsv.addActionListener(e -> saveCsvAsync());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectProductFromTable();
            }
        });

        // Default CSV file path
        File defaultFile = new File("data/products.csv");
        if (defaultFile.exists()) {
            currentCsvFile = defaultFile;
            readCsvAsync();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Product p : productList) {
            tableModel.addRow(new Object[]{p.getMaSP(), p.getTenSP(), currencyFormat.format(p.getDonGia())});
        }
    }

    private void addProduct() {
        String ma = txtMaSP.getText().trim();
        String ten = txtTenSP.getText().trim();
        String giaStr = txtDonGia.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Product p : productList) {
            if (p.getMaSP().equalsIgnoreCase(ma)) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            double gia = Double.parseDouble(giaStr);
            if (gia < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải >= 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            productList.add(new Product(ma, ten, gia));
            refreshTable();
            clearForm();
            lblStatus.setText("Trạng thái: Đã thêm sản phẩm " + ma);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa trong bảng", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ma = txtMaSP.getText().trim();
        String ten = txtTenSP.getText().trim();
        String giaStr = txtDonGia.getText().trim();

        try {
            double gia = Double.parseDouble(giaStr);
            Product p = productList.get(selectedRow);
            p.setMaSP(ma);
            p.setTenSP(ten);
            p.setDonGia(gia);

            refreshTable();
            lblStatus.setText("Trạng thái: Đã cập nhật thông tin sản phẩm " + ma);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product p = productList.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm " + p.getMaSP() + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productList.remove(selectedRow);
            refreshTable();
            clearForm();
            lblStatus.setText("Trạng thái: Đã xóa sản phẩm thành công.");
        }
    }

    private void clearForm() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        table.clearSelection();
    }

    private void selectProductFromTable() {
        int row = table.getSelectedRow();
        if (row >= 0 && row < productList.size()) {
            Product p = productList.get(row);
            txtMaSP.setText(p.getMaSP());
            txtTenSP.setText(p.getTenSP());
            txtDonGia.setText(String.valueOf((long) p.getDonGia()));
        }
    }

    private void readCsvAsync() {
        if (currentCsvFile == null) {
            JFileChooser chooser = new JFileChooser(new File("."));
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                currentCsvFile = chooser.getSelectedFile();
            } else {
                return;
            }
        }

        btnReadCsv.setEnabled(false);
        btnSaveCsv.setEnabled(false);
        lblStatus.setText("Đang đọc dữ liệu từ file CSV...");
        progressBar.setValue(0);

        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                List<Product> list = new ArrayList<>();
                long totalBytes = Files.size(currentCsvFile.toPath());
                long readBytes = 0;

                try (BufferedReader reader = Files.newBufferedReader(currentCsvFile.toPath(), StandardCharsets.UTF_8)) {
                    String line = reader.readLine(); // Header
                    if (line != null) readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;

                    while ((line = reader.readLine()) != null) {
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        if (!line.trim().isEmpty()) {
                            String[] parts = line.split(",");
                            if (parts.length >= 3) {
                                String ma = parts[0].trim();
                                String ten = parts[1].trim();
                                double gia = Double.parseDouble(parts[2].trim());
                                list.add(new Product(ma, ten, gia));
                            }
                        }
                        int progress = totalBytes == 0 ? 100 : (int) Math.min(100, (readBytes * 100 / totalBytes));
                        setProgress(progress);
                        Thread.sleep(100);
                    }
                }
                return list;
            }

            @Override
            protected void done() {
                try {
                    productList = get();
                    refreshTable();
                    lblStatus.setText("Đã nạp " + productList.size() + " sản phẩm từ file: " + currentCsvFile.getName());
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi khi đọc file CSV: " + ex.getMessage());
                }
                progressBar.setValue(100);
                btnReadCsv.setEnabled(true);
                btnSaveCsv.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private void saveCsvAsync() {
        if (currentCsvFile == null) {
            JFileChooser chooser = new JFileChooser(new File("."));
            int res = chooser.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                currentCsvFile = chooser.getSelectedFile();
            } else {
                return;
            }
        }

        btnReadCsv.setEnabled(false);
        btnSaveCsv.setEnabled(false);
        lblStatus.setText("Đang ghi dữ liệu ra file CSV...");
        progressBar.setValue(0);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                int total = productList.size();
                try (BufferedWriter writer = Files.newBufferedWriter(currentCsvFile.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("MaSP,TenSP,DonGia\n");
                    for (int i = 0; i < total; i++) {
                        Product p = productList.get(i);
                        writer.write(String.format("%s,%s,%.0f\n", p.getMaSP(), p.getTenSP(), p.getDonGia()));
                        int progress = (int) (((i + 1) * 100.0) / total);
                        setProgress(progress);
                        Thread.sleep(100);
                    }
                }
                return true;
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        lblStatus.setText("Đã lưu thành công " + productList.size() + " sản phẩm vào file: " + currentCsvFile.getName());
                        JOptionPane.showMessageDialog(ProductManagerFrame.this, "Lưu file CSV thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi khi ghi file CSV: " + ex.getMessage());
                }
                progressBar.setValue(100);
                btnReadCsv.setEnabled(true);
                btnSaveCsv.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new ProductManagerFrame().setVisible(true));
    }
}
