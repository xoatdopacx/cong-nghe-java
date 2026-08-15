package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;
import vn.edu.eaut.lab5.util.PhoneDocumentFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {
    private JTextField txtMaKh;
    private JTextField txtTenKh;
    private JTextField txtSdt;
    private JTextField txtDiaChi;

    private JTextField txtSearch;
    private JButton btnSearch;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable table;
    private DefaultTableModel tableModel;

    private final KhachHangBUS khachHangBUS = new KhachHangBUS();

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Inputs
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Khách hàng (Bài 3 - Validate SĐT)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã khách hàng:"), gbc);
        gbc.gridx = 1;
        txtMaKh = new JTextField(8);
        txtMaKh.setEditable(false);
        formPanel.add(txtMaKh, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Họ và Tên:"), gbc);
        gbc.gridx = 3;
        txtTenKh = new JTextField(18);
        formPanel.add(txtTenKh, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        txtSdt = new JTextField(12);
        // Gợi ý từ đề bài Bài 3: Áp dụng PhoneDocumentFilter cho txtSdt
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());
        formPanel.add(txtSdt, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 3;
        txtDiaChi = new JTextField(22);
        formPanel.add(txtDiaChi, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAdd = new JButton("Thêm khách hàng");
        btnEdit = new JButton("Sửa thông tin");
        btnDelete = new JButton("Xóa khách hàng");
        btnClear = new JButton("Làm mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm Khách hàng"));
        searchPanel.add(new JLabel("Từ khóa (Tên/SĐT):"));
        txtSearch = new JTextField(16);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(btnSearch);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(btnPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        // Table
        String[] columns = {"Mã KH", "Họ và Tên", "Số Điện Thoại", "Địa Chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Event listeners
        btnAdd.addActionListener(e -> addKhachHang());
        btnEdit.addActionListener(e -> editKhachHang());
        btnDelete.addActionListener(e -> deleteKhachHang());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> loadData());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selectTableRow();
        });

        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        String keyword = txtSearch.getText().trim();
        try {
            List<KhachHang> list = khachHangBUS.findWithPagination(1, 1000, keyword);
            for (KhachHang kh : list) {
                tableModel.addRow(new Object[]{kh.getMaKh(), kh.getTenKh(), kh.getSdt(), kh.getDiaChi()});
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi nạp dữ liệu khách hàng: " + ex.getMessage());
        }
    }

    private void addKhachHang() {
        try {
            KhachHang kh = new KhachHang(0, txtTenKh.getText().trim(), txtSdt.getText().trim(), txtDiaChi.getText().trim());
            if (khachHangBUS.save(kh)) {
                MessageUtil.showInfo(this, "Thêm khách hàng thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, ex.getMessage());
        }
    }

    private void editKhachHang() {
        if (txtMaKh.getText().isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng chọn khách hàng cần sửa từ bảng");
            return;
        }
        try {
            int ma = Integer.parseInt(txtMaKh.getText());
            KhachHang kh = new KhachHang(ma, txtTenKh.getText().trim(), txtSdt.getText().trim(), txtDiaChi.getText().trim());
            if (khachHangBUS.save(kh)) {
                MessageUtil.showInfo(this, "Cập nhật khách hàng thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, ex.getMessage());
        }
    }

    private void deleteKhachHang() {
        if (txtMaKh.getText().isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng chọn khách hàng cần xóa");
            return;
        }
        int ma = Integer.parseInt(txtMaKh.getText());
        if (MessageUtil.showConfirm(this, "Bạn có chắc muốn xóa khách hàng mã " + ma + "?", "Xác nhận xóa")) {
            try {
                if (khachHangBUS.delete(ma)) {
                    MessageUtil.showInfo(this, "Xóa khách hàng thành công!");
                    clearForm();
                    loadData();
                }
            } catch (Exception ex) {
                MessageUtil.showError(this, "Lỗi xóa khách hàng: " + ex.getMessage());
            }
        }
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        table.clearSelection();
    }

    private void selectTableRow() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            txtMaKh.setText(tableModel.getValueAt(r, 0).toString());
            txtTenKh.setText(tableModel.getValueAt(r, 1).toString());
            txtSdt.setText(tableModel.getValueAt(r, 2).toString());
            txtDiaChi.setText(tableModel.getValueAt(r, 3) != null ? tableModel.getValueAt(r, 3).toString() : "");
        }
    }
}
