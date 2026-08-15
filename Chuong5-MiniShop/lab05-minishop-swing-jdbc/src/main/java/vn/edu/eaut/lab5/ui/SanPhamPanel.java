package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SanPhamPanel extends JPanel {
    private JTextField txtMaSp;
    private JTextField txtTenSp;
    private JTextField txtDonGia;
    private JTextField txtSoLuong;
    private JComboBox<DanhMuc> cboDanhMuc;

    private JTextField txtSearch;
    private JComboBox<DanhMuc> cboFilterDanhMuc;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private DefaultTableModel tableModel;
    private JProgressBar progressBar;

    // Pagination
    private int currentPage = 1;
    private final int pageSize = 10;
    private int totalPages = 1;
    private JLabel lblPageInfo;
    private JButton btnFirstPage;
    private JButton btnPrevPage;
    private JButton btnNextPage;
    private JButton btnLastPage;

    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private final DanhMucBUS danhMucBUS = new DanhMucBUS();

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Input Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Sản phẩm (Mô hình 3 lớp & JDBC)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1;
        txtMaSp = new JTextField(8);
        txtMaSp.setEditable(false);
        formPanel.add(txtMaSp, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 3;
        txtTenSp = new JTextField(18);
        formPanel.add(txtTenSp, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Đơn giá (VNĐ):"), gbc);
        gbc.gridx = 1;
        txtDonGia = new JTextField(12);
        formPanel.add(txtDonGia, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Số lượng tồn:"), gbc);
        gbc.gridx = 3;
        txtSoLuong = new JTextField(8);
        formPanel.add(txtSoLuong, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Danh mục:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        cboDanhMuc = new JComboBox<>();
        cboDanhMuc.setPreferredSize(new Dimension(220, 24));
        formPanel.add(cboDanhMuc, gbc);
        gbc.gridwidth = 1;

        // CRUD Buttons
        JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAdd = new JButton("Thêm sản phẩm");
        btnEdit = new JButton("Sửa sản phẩm");
        btnDelete = new JButton("Xóa sản phẩm");
        btnClear = new JButton("Làm mới");

        crudPanel.add(btnAdd);
        crudPanel.add(btnEdit);
        crudPanel.add(btnDelete);
        crudPanel.add(btnClear);

        // Search & Filter Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm & Lọc danh mục (Bài 6 & 9)"));
        searchPanel.add(new JLabel("Từ khóa:"));
        txtSearch = new JTextField(12);
        searchPanel.add(txtSearch);

        searchPanel.add(new JLabel("Danh mục:"));
        cboFilterDanhMuc = new JComboBox<>();
        cboFilterDanhMuc.setPreferredSize(new Dimension(160, 24));
        searchPanel.add(cboFilterDanhMuc);

        btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(btnSearch);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(crudPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        // Table & Renderer (Custom renderer to highlight low stock < 5 in red)
        String[] columns = {"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "Tồn Kho", "Danh Mục", "Trạng Thái Tồn Kho"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Highlight low stock < 5
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, isSelected, hasFocus, row, col);
                try {
                    int stock = Integer.parseInt(tbl.getValueAt(row, 3).toString());
                    if (!isSelected) {
                        if (stock == 0) {
                            c.setBackground(new Color(255, 230, 230)); // Red tint for out of stock
                            c.setForeground(Color.RED);
                        } else if (stock < 5) {
                            c.setBackground(new Color(255, 250, 205)); // Yellow tint for low stock
                            c.setForeground(new Color(180, 100, 0));
                        } else {
                            c.setBackground(Color.WHITE);
                            c.setForeground(Color.BLACK);
                        }
                    }
                } catch (Exception ignored) {}
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Pagination Panel (Bài 9)
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnFirstPage = new JButton("<< Đầu");
        btnPrevPage = new JButton("< Trước");
        lblPageInfo = new JLabel("Trang 1 / 1");
        btnNextPage = new JButton("Sau >");
        btnLastPage = new JButton("Cuối >>");

        paginationPanel.add(btnFirstPage);
        paginationPanel.add(btnPrevPage);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNextPage);
        paginationPanel.add(btnLastPage);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(paginationPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        btnAdd.addActionListener(e -> addSanPham());
        btnEdit.addActionListener(e -> editSanPham());
        btnDelete.addActionListener(e -> deleteSanPham());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> { currentPage = 1; loadDataAsync(); });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selectTableRow();
        });

        btnFirstPage.addActionListener(e -> { currentPage = 1; loadDataAsync(); });
        btnPrevPage.addActionListener(e -> { if (currentPage > 1) { currentPage--; loadDataAsync(); } });
        btnNextPage.addActionListener(e -> { if (currentPage < totalPages) { currentPage++; loadDataAsync(); } });
        btnLastPage.addActionListener(e -> { currentPage = totalPages; loadDataAsync(); });

        loadCategories();
        loadDataAsync();
    }

    public void loadCategories() {
        try {
            List<DanhMuc> list = danhMucBUS.findAll();
            cboDanhMuc.removeAllItems();
            cboFilterDanhMuc.removeAllItems();

            cboFilterDanhMuc.addItem(new DanhMuc(0, "-- Tất cả danh mục --"));
            for (DanhMuc dm : list) {
                cboDanhMuc.addItem(dm);
                cboFilterDanhMuc.addItem(dm);
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi nạp danh mục: " + ex.getMessage());
        }
    }

    public void loadDataAsync() {
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        String keyword = txtSearch.getText().trim();
        DanhMuc selectedFilter = (DanhMuc) cboFilterDanhMuc.getSelectedItem();
        Integer maDm = (selectedFilter != null && selectedFilter.getMaDm() > 0) ? selectedFilter.getMaDm() : null;

        // SwingWorker async loading (Bài 9 & 10)
        SwingWorker<List<SanPham>, Void> worker = new SwingWorker<>() {
            private int totalCount = 0;

            @Override
            protected List<SanPham> doInBackground() throws Exception {
                totalCount = sanPhamBUS.countWithPagination(keyword, maDm);
                return sanPhamBUS.findWithPagination(currentPage, pageSize, keyword, maDm);
            }

            @Override
            protected void done() {
                try {
                    List<SanPham> list = get();
                    tableModel.setRowCount(0);
                    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

                    for (SanPham sp : list) {
                        String statusStr = "Bình thường";
                        if (sp.getSoLuong() == 0) statusStr = "HẾT HÀNG";
                        else if (sp.getSoLuong() < 5) statusStr = "CẢNH BÁO: Tồn < 5";

                        tableModel.addRow(new Object[]{
                            sp.getMaSp(),
                            sp.getTenSp(),
                            nf.format(sp.getDonGia()) + " đ",
                            sp.getSoLuong(),
                            sp.getTenDm() != null ? sp.getTenDm() : "Chưa phân loại",
                            statusStr
                        });
                    }

                    totalPages = (int) Math.ceil((double) totalCount / pageSize);
                    if (totalPages < 1) totalPages = 1;
                    lblPageInfo.setText("Trang " + currentPage + " / " + totalPages + " (Tổng " + totalCount + " SP)");

                    btnPrevPage.setEnabled(currentPage > 1);
                    btnFirstPage.setEnabled(currentPage > 1);
                    btnNextPage.setEnabled(currentPage < totalPages);
                    btnLastPage.setEnabled(currentPage < totalPages);

                } catch (Exception ex) {
                    MessageUtil.showError(SanPhamPanel.this, "Lỗi tải dữ liệu sản phẩm: " + ex.getMessage());
                } finally {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                }
            }
        };

        worker.execute();
    }

    private void addSanPham() {
        try {
            String ten = txtTenSp.getText().trim();
            BigDecimal gia = new BigDecimal(txtDonGia.getText().trim());
            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            DanhMuc dm = (DanhMuc) cboDanhMuc.getSelectedItem();

            SanPham sp = new SanPham(0, ten, gia, sl, dm != null ? dm.getMaDm() : null);
            if (sanPhamBUS.save(sp)) {
                MessageUtil.showInfo(this, "Thêm sản phẩm thành công!");
                clearForm();
                loadDataAsync();
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, ex.getMessage());
        }
    }

    private void editSanPham() {
        if (txtMaSp.getText().isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng chọn sản phẩm cần sửa từ bảng");
            return;
        }
        try {
            int ma = Integer.parseInt(txtMaSp.getText());
            String ten = txtTenSp.getText().trim();
            BigDecimal gia = new BigDecimal(txtDonGia.getText().trim().replace(".", "").replace("đ", "").trim());
            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            DanhMuc dm = (DanhMuc) cboDanhMuc.getSelectedItem();

            SanPham sp = new SanPham(ma, ten, gia, sl, dm != null ? dm.getMaDm() : null);
            if (sanPhamBUS.save(sp)) {
                MessageUtil.showInfo(this, "Cập nhật sản phẩm thành công!");
                clearForm();
                loadDataAsync();
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, ex.getMessage());
        }
    }

    private void deleteSanPham() {
        if (txtMaSp.getText().isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }
        int ma = Integer.parseInt(txtMaSp.getText());
        if (MessageUtil.showConfirm(this, "Bạn có chắc muốn xóa sản phẩm mã " + ma + "?", "Xác nhận xóa")) {
            try {
                if (sanPhamBUS.delete(ma)) {
                    MessageUtil.showInfo(this, "Xóa sản phẩm thành công!");
                    clearForm();
                    loadDataAsync();
                }
            } catch (Exception ex) {
                MessageUtil.showError(this, "Lỗi xóa sản phẩm: " + ex.getMessage());
            }
        }
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        if (cboDanhMuc.getItemCount() > 0) cboDanhMuc.setSelectedIndex(0);
        table.clearSelection();
    }

    private void selectTableRow() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            txtMaSp.setText(tableModel.getValueAt(r, 0).toString());
            txtTenSp.setText(tableModel.getValueAt(r, 1).toString());
            String rawGia = tableModel.getValueAt(r, 2).toString().replace(".", "").replace("đ", "").trim();
            txtDonGia.setText(rawGia);
            txtSoLuong.setText(tableModel.getValueAt(r, 3).toString());

            String dmName = tableModel.getValueAt(r, 4).toString();
            for (int i = 0; i < cboDanhMuc.getItemCount(); i++) {
                DanhMuc dm = cboDanhMuc.getItemAt(i);
                if (dm.getTenDm().equals(dmName)) {
                    cboDanhMuc.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
