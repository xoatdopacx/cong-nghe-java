package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ThongKePanel extends JPanel {
    private JTextField txtTuNgay;
    private JTextField txtDenNgay;
    private JButton btnThongKe;

    private JLabel lblDoanhThu;
    private JLabel lblHighestInvoice;
    private JLabel lblTopSellingProduct;
    private JProgressBar progressBar;

    private final ThongKeBUS thongKeBUS = new ThongKeBUS();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Date Range Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Khoảng thời gian thống kê (Bài 5 - SwingWorker Async)"));

        LocalDate now = LocalDate.now();
        txtTuNgay = new JTextField(10);
        txtTuNgay.setText(now.minusDays(30).format(dtf));

        txtDenNgay = new JTextField(10);
        txtDenNgay.setText(now.format(dtf));

        btnThongKe = new JButton("Thống kê ngay");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 13));

        filterPanel.add(new JLabel("Từ ngày (dd/MM/yyyy):"));
        filterPanel.add(txtTuNgay);
        filterPanel.add(new JLabel("Đến ngày (dd/MM/yyyy):"));
        filterPanel.add(txtDenNgay);
        filterPanel.add(btnThongKe);

        // Stats Display Panel (Cards layout)
        JPanel statsContainer = new JPanel(new GridLayout(3, 1, 15, 15));
        statsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Card 1: Doanh thu
        JPanel card1 = createStatCard("TỔNG DOANH THU THEO KHOẢNG NGÀY", "0 VNĐ", new Color(41, 128, 185));
        lblDoanhThu = (JLabel) ((JPanel) card1.getComponent(1)).getComponent(0);

        // Card 2: Hóa đơn cao nhất
        JPanel card2 = createStatCard("HÓA ĐƠN CÓ GIÁ TRỊ CAO NHẤT", "Chưa có dữ liệu", new Color(39, 174, 96));
        lblHighestInvoice = (JLabel) ((JPanel) card2.getComponent(1)).getComponent(0);

        // Card 3: Sản phẩm bán chạy nhất
        JPanel card3 = createStatCard("SẢN PHẨM BÁN CHẠY NHẤT", "Chưa có dữ liệu", new Color(142, 68, 173));
        lblTopSellingProduct = (JLabel) ((JPanel) card3.getComponent(1)).getComponent(0);

        statsContainer.add(card1);
        statsContainer.add(card2);
        statsContainer.add(card3);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(filterPanel, BorderLayout.NORTH);
        topPanel.add(progressBar, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(statsContainer, BorderLayout.CENTER);

        btnThongKe.addActionListener(e -> runThongKeWorker());

        // Auto run initial stat
        runThongKeWorker();
    }

    private JPanel createStatCard(String title, String defaultValue, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitle.setForeground(color);

        JPanel valPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valPanel.setBackground(Color.WHITE);
        JLabel lblValue = new JLabel(defaultValue);
        lblValue.setFont(new Font("Arial", Font.BOLD, 18));
        lblValue.setForeground(Color.DARK_GRAY);
        valPanel.add(lblValue);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(valPanel, BorderLayout.CENTER);
        return card;
    }

    private void runThongKeWorker() {
        LocalDate tuNgay, denNgay;
        try {
            tuNgay = LocalDate.parse(txtTuNgay.getText().trim(), dtf);
            denNgay = LocalDate.parse(txtDenNgay.getText().trim(), dtf);
        } catch (Exception ex) {
            MessageUtil.showError(this, "Định dạng ngày không hợp lệ (Định dạng đúng: dd/MM/yyyy)");
            return;
        }

        btnThongKe.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);
        lblDoanhThu.setText("Đang tính toán...");

        // Gợi ý DoanhThuWorker từ đề bài Bài 5 (Dùng SwingWorker bất đồng bộ)
        SwingWorker<StatResult, Void> worker = new SwingWorker<>() {
            @Override
            protected StatResult doInBackground() throws Exception {
                BigDecimal revenue = thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
                HoaDon highestInvoice = thongKeBUS.findHighestValueInvoice();
                String topProduct = thongKeBUS.findTopSellingProduct();
                return new StatResult(revenue, highestInvoice, topProduct);
            }

            @Override
            protected void done() {
                try {
                    StatResult result = get();
                    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

                    // Revenue
                    lblDoanhThu.setText(nf.format(result.revenue) + " VNĐ");

                    // Highest Invoice
                    if (result.highestInvoice != null) {
                        lblHighestInvoice.setText("Mã HD: " + result.highestInvoice.getMaHd() + 
                                " | Khách hàng: " + result.highestInvoice.getTenKhachHang() + 
                                " | Giá trị: " + nf.format(result.highestInvoice.getTongTien()) + " VNĐ");
                    } else {
                        lblHighestInvoice.setText("Chưa có hóa đơn nào");
                    }

                    // Top Selling
                    lblTopSellingProduct.setText(result.topProduct);

                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Lỗi khi chạy thống kê: " + ex.getMessage());
                } finally {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                    btnThongKe.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private static class StatResult {
        BigDecimal revenue;
        HoaDon highestInvoice;
        String topProduct;

        public StatResult(BigDecimal revenue, HoaDon highestInvoice, String topProduct) {
            this.revenue = revenue;
            this.highestInvoice = highestInvoice;
            this.topProduct = topProduct;
        }
    }
}
