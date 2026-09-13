package vn.edu.eaut.warehouse.ui;

import vn.edu.eaut.warehouse.dao.OrderDAO;
import vn.edu.eaut.warehouse.dao.UserDAO.LoginResult;
import vn.edu.eaut.warehouse.model.Order;
import vn.edu.eaut.warehouse.model.OrderItem;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Cửa sổ chính ứng dụng Kho - hiển thị danh sách đơn hàng, cho phép xử lý
 */
public class MainFrame extends JFrame {

    private static final Color BG       = new Color(15, 15, 26);
    private static final Color SURFACE  = new Color(26, 26, 46);
    private static final Color PRIMARY  = new Color(99, 102, 241);
    private static final Color MUTED    = new Color(100, 116, 139);
    private static final Color TEXT     = new Color(226, 232, 240);
    private static final Color SUCCESS  = new Color(16, 185, 129);
    private static final Color DANGER   = new Color(239, 68, 68);
    private static final Color AMBER    = new Color(245, 158, 11);

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final LoginResult currentUser;
    private final OrderDAO orderDAO = new OrderDAO();

    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbFilter;
    private JLabel lblStatus;
    private JLabel lblUser;
    private JLabel lblTotal;

    // Stat labels
    private JLabel lblPending, lblProcessing, lblReady;

    public MainFrame(LoginResult user) {
        this.currentUser = user;
        initUI();
        loadOrders();
    }

    private void initUI() {
        setTitle("Warehouse Desktop - Hệ thống Kho | Lab 16");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setBackground(BG);

        // ===== Main layout =====
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(BG);

        // ===== TOP BAR =====
        mainPanel.add(buildTopBar(), BorderLayout.NORTH);

        // ===== CENTER: stats + table =====
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setBackground(BG);
        center.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        center.add(buildStatsPanel(), BorderLayout.NORTH);
        center.add(buildTablePanel(), BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);

        // ===== STATUS BAR =====
        mainPanel.add(buildStatusBar(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(SURFACE);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 20)),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));

        JLabel logo = new JLabel("🏭  Warehouse Desktop");
        logo.setFont(new Font("SansSerif", Font.BOLD, 16));
        logo.setForeground(PRIMARY);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightPanel.setBackground(SURFACE);

        lblUser = new JLabel("👤 " + currentUser.fullName() + "  [" + currentUser.role() + "]");
        lblUser.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblUser.setForeground(MUTED);

        JButton btnLogout = makeButton("🚪 Đăng xuất", DANGER);
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JButton btnRefresh = makeButton("🔄 Làm mới", PRIMARY);
        btnRefresh.addActionListener(e -> loadOrders());

        rightPanel.add(lblUser);
        rightPanel.add(btnRefresh);
        rightPanel.add(btnLogout);

        bar.add(logo, BorderLayout.WEST);
        bar.add(rightPanel, BorderLayout.EAST);
        return bar;
    }

    private JPanel buildStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 12, 0));
        panel.setBackground(BG);

        lblPending    = new JLabel("0", SwingConstants.CENTER);
        lblProcessing = new JLabel("0", SwingConstants.CENTER);
        lblReady      = new JLabel("0", SwingConstants.CENTER);
        JLabel lblAll = new JLabel("...", SwingConstants.CENTER);
        lblTotal = lblAll;

        panel.add(makeStatCard("⏳ Chờ xử lý",   lblPending,    AMBER));
        panel.add(makeStatCard("⚙️ Đang xử lý",  lblProcessing, PRIMARY));
        panel.add(makeStatCard("📦 Sẵn sàng giao", lblReady,     SUCCESS));
        panel.add(makeStatCard("📊 Tổng đơn",      lblAll,       MUTED));
        return panel;
    }

    private JPanel makeStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 15)),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(MUTED);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        valueLabel.setForeground(color);

        card.add(lbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BG);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolbar.setBackground(BG);

        JLabel filterLabel = new JLabel("Lọc trạng thái:");
        filterLabel.setForeground(MUTED);
        filterLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        cmbFilter = new JComboBox<>(new String[]{"ALL", "PENDING", "PROCESSING", "READY", "SHIPPING", "COMPLETED", "CANCELLED"});
        cmbFilter.setBackground(SURFACE);
        cmbFilter.setForeground(TEXT);
        cmbFilter.setPreferredSize(new Dimension(160, 32));
        cmbFilter.addActionListener(e -> loadOrders());

        JButton btnProcess = makeButton("⚙️ Tiếp nhận xử lý", new Color(99, 102, 241));
        JButton btnReady   = makeButton("📦 Đánh dấu sẵn sàng", SUCCESS);

        btnProcess.addActionListener(e -> updateSelectedOrder("PROCESSING",
                "Tiếp nhận và bắt đầu xử lý đơn - kho JAVA_SWING"));
        btnReady.addActionListener(e -> updateSelectedOrder("READY",
                "Đã đóng gói, sẵn sàng giao hàng - kho JAVA_SWING"));

        toolbar.add(filterLabel);
        toolbar.add(cmbFilter);
        toolbar.add(Box.createHorizontalStrut(16));
        toolbar.add(btnProcess);
        toolbar.add(btnReady);

        // Table
        String[] cols = {"#ID", "Khách hàng", "Thời gian đặt", "Tổng tiền (₫)", "Trạng thái"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        orderTable = new JTable(tableModel);
        orderTable.setBackground(SURFACE);
        orderTable.setForeground(TEXT);
        orderTable.setGridColor(new Color(255, 255, 255, 10));
        orderTable.setSelectionBackground(new Color(99, 102, 241, 80));
        orderTable.setSelectionForeground(TEXT);
        orderTable.setRowHeight(38);
        orderTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        orderTable.setShowVerticalLines(false);
        orderTable.getTableHeader().setBackground(new Color(22, 33, 62));
        orderTable.getTableHeader().setForeground(MUTED);
        orderTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        orderTable.getTableHeader().setReorderingAllowed(false);

        // Column widths
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(140);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(160);
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        // Status column renderer
        orderTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                String s = val == null ? "" : val.toString();
                lbl.setForeground(switch (s) {
                    case "PENDING"    -> AMBER;
                    case "PROCESSING" -> PRIMARY;
                    case "READY"      -> new Color(6, 182, 212);
                    case "SHIPPING"   -> new Color(168, 85, 247);
                    case "COMPLETED"  -> SUCCESS;
                    case "CANCELLED"  -> DANGER;
                    default -> TEXT;
                });
                lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });

        // Double click → chi tiết
        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) showOrderDetail();
            }
        });

        JScrollPane scroll = new JScrollPane(orderTable);
        scroll.setBackground(SURFACE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 15)));
        scroll.getViewport().setBackground(SURFACE);

        panel.add(toolbar, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        // Hint
        JLabel hint = new JLabel("💡 Double-click vào đơn để xem chi tiết sản phẩm");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hint.setForeground(MUTED);
        panel.add(hint, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(22, 33, 62));
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 15)),
                BorderFactory.createEmptyBorder(5, 16, 5, 16)));

        lblStatus = new JLabel("✅ Sẵn sàng");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStatus.setForeground(MUTED);

        JLabel info = new JLabel("Lab 16 · Warehouse Desktop · Java Swing + JDBC · DB: java_integrated_lab");
        info.setFont(new Font("SansSerif", Font.PLAIN, 11));
        info.setForeground(MUTED);

        bar.add(lblStatus, BorderLayout.WEST);
        bar.add(info, BorderLayout.EAST);
        return bar;
    }

    private void loadOrders() {
        String filter = (String) cmbFilter.getSelectedItem();
        lblStatus.setText("⏳ Đang tải...");
        lblStatus.setForeground(AMBER);

        SwingWorker<List<Order>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Order> doInBackground() throws Exception {
                return orderDAO.findByStatus(filter == null ? "ALL" : filter);
            }

            @Override
            protected void done() {
                try {
                    List<Order> orders = get();
                    tableModel.setRowCount(0);
                    for (Order o : orders) {
                        tableModel.addRow(new Object[]{
                                "#" + o.getId(),
                                o.getCustomerName(),
                                o.getCreatedAt() != null ? o.getCreatedAt().format(FMT) : "—",
                                formatMoney(o.getTotalAmount()),
                                o.getStatus()
                        });
                    }
                    // Cập nhật stats
                    updateStats();
                    lblStatus.setText("✅ Đã tải " + orders.size() + " đơn hàng");
                    lblStatus.setForeground(SUCCESS);
                } catch (Exception ex) {
                    lblStatus.setText("❌ Lỗi: " + ex.getMessage());
                    lblStatus.setForeground(DANGER);
                }
            }
        };
        worker.execute();
    }

    private void updateStats() {
        SwingWorker<int[], Void> w = new SwingWorker<>() {
            @Override
            protected int[] doInBackground() throws Exception {
                return new int[]{
                        orderDAO.countByStatus("PENDING"),
                        orderDAO.countByStatus("PROCESSING"),
                        orderDAO.countByStatus("READY"),
                        orderDAO.countByStatus("ALL")
                };
            }
            @Override
            protected void done() {
                try {
                    int[] c = get();
                    lblPending.setText(String.valueOf(c[0]));
                    lblProcessing.setText(String.valueOf(c[1]));
                    lblReady.setText(String.valueOf(c[2]));
                    lblTotal.setText(String.valueOf(c[3]));
                } catch (Exception ignored) {}
            }
        };
        w.execute();
    }

    private void updateSelectedOrder(String newStatus, String note) {
        int row = orderTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một đơn hàng từ danh sách!", "Chưa chọn đơn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idStr = tableModel.getValueAt(row, 0).toString().replace("#", "");
        long orderId = Long.parseLong(idStr);
        String curStatus = tableModel.getValueAt(row, 4).toString();

        // Kiểm tra logic trạng thái hợp lệ
        boolean valid = ("PROCESSING".equals(newStatus) && "PENDING".equals(curStatus))
                || ("READY".equals(newStatus) && "PROCESSING".equals(curStatus));
        if (!valid) {
            JOptionPane.showMessageDialog(this,
                    "Không thể chuyển từ '" + curStatus + "' sang '" + newStatus + "'.\n" +
                    "Kho chỉ có thể: PENDING→PROCESSING hoặc PROCESSING→READY",
                    "Trạng thái không hợp lệ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Xác nhận cập nhật đơn #" + orderId + "\n" + curStatus + " → " + newStatus + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        lblStatus.setText("⏳ Đang cập nhật...");
        lblStatus.setForeground(AMBER);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return orderDAO.updateStatus(orderId, newStatus, currentUser.id(), note);
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "✅ Đã cập nhật đơn #" + orderId + " → " + newStatus,
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadOrders();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "❌ Lỗi: " + ex.getMessage(),
                            "Lỗi cập nhật", JOptionPane.ERROR_MESSAGE);
                    lblStatus.setText("❌ Lỗi: " + ex.getMessage());
                    lblStatus.setForeground(DANGER);
                }
            }
        };
        worker.execute();
    }

    private void showOrderDetail() {
        int row = orderTable.getSelectedRow();
        if (row < 0) return;
        long orderId = Long.parseLong(
                tableModel.getValueAt(row, 0).toString().replace("#", ""));

        SwingWorker<List<OrderItem>, Void> w = new SwingWorker<>() {
            @Override
            protected List<OrderItem> doInBackground() throws Exception {
                return orderDAO.findItemsByOrderId(orderId);
            }
            @Override
            protected void done() {
                try {
                    List<OrderItem> items = get();
                    showDetailDialog(orderId, items);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Lỗi: " + ex.getMessage());
                }
            }
        };
        w.execute();
    }

    private void showDetailDialog(long orderId, List<OrderItem> items) {
        JDialog dlg = new JDialog(this, "Chi tiết đơn hàng #" + orderId, true);
        dlg.setSize(600, 380);
        dlg.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("📦 Sản phẩm trong đơn hàng #" + orderId);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(TEXT);

        String[] cols = {"Mã SP", "Tên sản phẩm", "SL", "Đơn giá (₫)", "Thành tiền (₫)"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem it : items) {
            m.addRow(new Object[]{
                    it.getProductCode(), it.getProductName(),
                    it.getQuantity(),
                    formatMoney(it.getUnitPrice()),
                    formatMoney(it.getSubtotal())
            });
            total = total.add(it.getSubtotal());
        }

        JTable t = new JTable(m);
        t.setBackground(new Color(22, 33, 62));
        t.setForeground(TEXT);
        t.setGridColor(new Color(255, 255, 255, 10));
        t.setRowHeight(32);
        t.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lblTotal = new JLabel("Tổng: " + formatMoney(total) + " ₫");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTotal.setForeground(PRIMARY);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(t), BorderLayout.CENTER);
        panel.add(lblTotal, BorderLayout.SOUTH);

        dlg.setContentPane(panel);
        dlg.setVisible(true);
    }

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 16, 32));
        return btn;
    }

    private String formatMoney(BigDecimal v) {
        if (v == null) return "0";
        return String.format("%,.0f", v.doubleValue());
    }
}
