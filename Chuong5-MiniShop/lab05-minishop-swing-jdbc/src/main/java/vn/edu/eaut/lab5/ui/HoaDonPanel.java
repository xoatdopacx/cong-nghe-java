package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.ExporterUtil;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HoaDonPanel extends JPanel {
    private JComboBox<KhachHang> cboKhachHang;
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JButton btnAddDetail;
    private JButton btnRemoveDetail;

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien;

    private JButton btnSaveHoaDon;
    private JButton btnExportTxt;
    private JButton btnExportCsv;
    private JButton btnClear;

    private final List<ChiTietHoaDon> chiTietList = new ArrayList<>();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();

    private final TaiKhoan currentUser;
    private int currentSavedHoaDonId = 0;
    private HoaDon lastSavedHoaDon = null;

    public HoaDonPanel(TaiKhoan currentUser) {
        this.currentUser = currentUser;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Section (Customer + Product choice)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Lập Hóa Đơn & Chi Tiết (Bài 4, 7, 8)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Khách hàng:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        cboKhachHang = new JComboBox<>();
        cboKhachHang.setPreferredSize(new Dimension(320, 24));
        formPanel.add(cboKhachHang, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Sản phẩm:"), gbc);
        gbc.gridx = 1;
        cboSanPham = new JComboBox<>();
        cboSanPham.setPreferredSize(new Dimension(240, 24));
        formPanel.add(cboSanPham, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Số lượng bán:"), gbc);
        gbc.gridx = 3;
        txtSoLuong = new JTextField(6);
        txtSoLuong.setText("1");
        formPanel.add(txtSoLuong, gbc);

        // Add / Remove Detail Line Buttons
        JPanel detailBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAddDetail = new JButton("Thêm vào hóa đơn");
        btnRemoveDetail = new JButton("Xóa dòng chọn");
        detailBtnPanel.add(btnAddDetail);
        detailBtnPanel.add(btnRemoveDetail);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.add(formPanel, BorderLayout.NORTH);
        northPanel.add(detailBtnPanel, BorderLayout.SOUTH);

        // Table for line items
        String[] columns = {"Mã SP", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bottom Action Bar (Total sum + Save + Export TXT/CSV)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        lblTongTien = new JLabel("TỔNG TIỀN HÓA ĐƠN: 0 VNĐ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(new Color(192, 57, 43));
        bottomPanel.add(lblTongTien, BorderLayout.NORTH);

        JPanel actionBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnSaveHoaDon = new JButton("Lưu Hóa Đơn (Transaction)");
        btnSaveHoaDon.setFont(new Font("Arial", Font.BOLD, 13));
        btnExportTxt = new JButton("Xuất file TXT (Bài 8)");
        btnExportCsv = new JButton("Xuất file CSV (Bài 8)");
        btnClear = new JButton("Làm mới hóa đơn");

        btnExportTxt.setEnabled(false);
        btnExportCsv.setEnabled(false);

        actionBtnPanel.add(btnSaveHoaDon);
        actionBtnPanel.add(btnExportTxt);
        actionBtnPanel.add(btnExportCsv);
        actionBtnPanel.add(btnClear);
        bottomPanel.add(actionBtnPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        btnAddDetail.addActionListener(e -> addDetailRow());
        btnRemoveDetail.addActionListener(e -> removeDetailRow());
        btnSaveHoaDon.addActionListener(e -> saveHoaDon());
        btnExportTxt.addActionListener(e -> exportFile("txt"));
        btnExportCsv.addActionListener(e -> exportFile("csv"));
        btnClear.addActionListener(e -> resetForm());

        loadDropdownData();
    }

    public void loadDropdownData() {
        try {
            cboKhachHang.removeAllItems();
            List<KhachHang> khList = khachHangBUS.findAll();
            for (KhachHang kh : khList) cboKhachHang.addItem(kh);

            cboSanPham.removeAllItems();
            List<SanPham> spList = sanPhamBUS.findAll();
            for (SanPham sp : spList) cboSanPham.addItem(sp);
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi nạp dữ liệu khách hàng/sản phẩm: " + ex.getMessage());
        }
    }

    private void addDetailRow() {
        SanPham selectedSp = (SanPham) cboSanPham.getSelectedItem();
        if (selectedSp == null) {
            MessageUtil.showWarning(this, "Vui lòng chọn sản phẩm");
            return;
        }

        int sl;
        try {
            sl = Integer.parseInt(txtSoLuong.getText().trim());
            if (sl <= 0) {
                MessageUtil.showWarning(this, "Số lượng bán phải lớn hơn 0");
                return;
            }
        } catch (NumberFormatException ex) {
            MessageUtil.showWarning(this, "Số lượng phải là số nguyên hợp lệ");
            return;
        }

        // Bài 7: Kiểm tra tồn kho trước khi bán
        int currentInCart = 0;
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getMaSp() == selectedSp.getMaSp()) {
                currentInCart += ct.getSoLuong();
            }
        }

        if (currentInCart + sl > selectedSp.getSoLuong()) {
            MessageUtil.showError(this, "CẢNH BÁO TỒN KHO: Sản phẩm '" + selectedSp.getTenSp() + 
                    "' chỉ còn tồn kho " + selectedSp.getSoLuong() + " (Đã có trong giỏ: " + currentInCart + ")!");
            return;
        }

        // Add or merge into cart
        boolean merged = false;
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getMaSp() == selectedSp.getMaSp()) {
                ct.setSoLuong(ct.getSoLuong() + sl);
                merged = true;
                break;
            }
        }

        if (!merged) {
            ChiTietHoaDon newCt = new ChiTietHoaDon(selectedSp.getMaSp(), selectedSp.getTenSp(), sl, selectedSp.getDonGia());
            chiTietList.add(newCt);
        }

        refreshTableAndTotal();
    }

    private void removeDetailRow() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            chiTietList.remove(r);
            refreshTableAndTotal();
        } else {
            MessageUtil.showWarning(this, "Vui lòng chọn dòng cần xóa trong bảng chi tiết");
        }
    }

    private void refreshTableAndTotal() {
        tableModel.setRowCount(0);
        BigDecimal total = BigDecimal.ZERO;
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (ChiTietHoaDon ct : chiTietList) {
            total = total.add(ct.getThanhTien());
            tableModel.addRow(new Object[]{
                ct.getMaSp(),
                ct.getTenSp(),
                ct.getSoLuong(),
                nf.format(ct.getDonGia()) + " đ",
                nf.format(ct.getThanhTien()) + " đ"
            });
        }

        lblTongTien.setText("TỔNG TIỀN HÓA ĐƠN: " + nf.format(total) + " VNĐ");
    }

    private void saveHoaDon() {
        KhachHang selectedKh = (KhachHang) cboKhachHang.getSelectedItem();
        if (selectedKh == null) {
            MessageUtil.showWarning(this, "Vui lòng chọn khách hàng lập hóa đơn");
            return;
        }

        if (chiTietList.isEmpty()) {
            MessageUtil.showWarning(this, "Hóa đơn chưa có sản phẩm nào!");
            return;
        }

        String usernameStr = currentUser != null ? currentUser.getUsername() : "admin";

        try {
            int maHd = hoaDonBUS.taoHoaDon(selectedKh.getMaKh(), usernameStr, chiTietList);
            currentSavedHoaDonId = maHd;
            lastSavedHoaDon = hoaDonBUS.findById(maHd);

            MessageUtil.showInfo(this, "LẬP HÓA ĐƠN THÀNH CÔNG!\nMã hóa đơn vừa tạo: HD" + maHd + 
                    "\nĐã tự động trừ số lượng tồn kho theo Transaction JDBC.");

            btnSaveHoaDon.setEnabled(false);
            btnExportTxt.setEnabled(true);
            btnExportCsv.setEnabled(true);

            loadDropdownData(); // Refresh product stock dropdown
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage());
        }
    }

    private void exportFile(String format) {
        if (lastSavedHoaDon == null) {
            MessageUtil.showWarning(this, "Chưa có hóa đơn nào vừa được lưu để xuất file!");
            return;
        }

        try {
            File exportedFile;
            if ("txt".equalsIgnoreCase(format)) {
                exportedFile = ExporterUtil.exportHoaDonTxt(lastSavedHoaDon, "exports");
            } else {
                exportedFile = ExporterUtil.exportHoaDonCsv(lastSavedHoaDon, "exports");
            }
            MessageUtil.showInfo(this, "Đã xuất hóa đơn ra file thành công:\n" + exportedFile.getAbsolutePath());
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi khi xuất file hóa đơn: " + ex.getMessage());
        }
    }

    private void resetForm() {
        chiTietList.clear();
        refreshTableAndTotal();
        btnSaveHoaDon.setEnabled(true);
        btnExportTxt.setEnabled(false);
        btnExportCsv.setEnabled(false);
        lastSavedHoaDon = null;
        txtSoLuong.setText("1");
    }
}
