package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductLoadDemoFrame extends JFrame {
    private JButton btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;
    private JProgressBar progressBar;
    private JLabel lblStatus;

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
        public String getTenSP() { return tenSP; }
        public double getDonGia() { return donGia; }
    }

    public ProductLoadDemoFrame() {
        setTitle("Bài 9 - Mô phỏng tải danh sách sản phẩm (Bất đồng bộ)");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLoad = new JButton("Tải sản phẩm");
        btnLoad.setFont(new Font("Arial", Font.BOLD, 14));

        String[] columns = {"Mã SP", "Tên sản phẩm", "Đơn giá (VNĐ)"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        lblStatus = new JLabel("Trạng thái: Chưa tải dữ liệu", SwingConstants.LEFT);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.add(btnLoad);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(progressBar, BorderLayout.SOUTH);
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(lblStatus, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadProductsAsync());
    }

    private void loadProductsAsync() {
        btnLoad.setEnabled(false);
        tableModel.setRowCount(0);
        lblStatus.setText("Đang tải dữ liệu sản phẩm từ hệ thống...");
        progressBar.setValue(0);

        SwingWorker<List<Product>, Product> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                List<Product> mockProducts = new ArrayList<>();
                mockProducts.add(new Product("SP01", "Bàn phím cơ RGB", 250000));
                mockProducts.add(new Product("SP02", "Chuột không dây", 150000));
                mockProducts.add(new Product("SP03", "Màn hình 27 inch 4K", 2500000));
                mockProducts.add(new Product("SP04", "Tai nghe Gaming Surround", 450000));
                mockProducts.add(new Product("SP05", "Webcam Full HD 1080p", 600000));
                mockProducts.add(new Product("SP06", "Loa Bluetooth Bass Boost", 350000));
                mockProducts.add(new Product("SP07", "Ổ cứng SSD 1TB NVMe", 1200000));
                mockProducts.add(new Product("SP08", "Bàn di chuột LED RGB", 180000));

                List<Product> loaded = new ArrayList<>();
                int total = mockProducts.size();
                NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

                for (int i = 0; i < total; i++) {
                    Thread.sleep(400); // Simulate network / database query latency
                    Product p = mockProducts.get(i);
                    loaded.add(p);
                    publish(p);

                    int progress = (int) (((i + 1) * 100.0) / total);
                    setProgress(progress);
                }
                return loaded;
            }

            @Override
            protected void process(List<Product> chunks) {
                NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                for (Product p : chunks) {
                    tableModel.addRow(new Object[]{p.getMaSP(), p.getTenSP(), currencyFormat.format(p.getDonGia()) + " đ"});
                }
            }

            @Override
            protected void done() {
                try {
                    List<Product> result = get();
                    lblStatus.setText("Trạng thái: Đã tải thành công " + result.size() + " sản phẩm.");
                } catch (Exception ex) {
                    lblStatus.setText("Trạng thái: Có lỗi xảy ra khi tải danh sách sản phẩm.");
                }
                progressBar.setValue(100);
                btnLoad.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new ProductLoadDemoFrame().setVisible(true));
    }
}
