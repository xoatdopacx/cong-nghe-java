package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DanhMucPanel extends JPanel {
    private JTextField txtMaDm;
    private JTextField txtTenDm;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    private final DanhMucBUS danhMucBUS = new DanhMucBUS();

    public DanhMucPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Danh mục sản phẩm (Bài 6)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã danh mục:"), gbc);
        gbc.gridx = 1;
        txtMaDm = new JTextField(10);
        txtMaDm.setEditable(false);
        formPanel.add(txtMaDm, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tên danh mục:"), gbc);
        gbc.gridx = 3;
        txtTenDm = new JTextField(20);
        formPanel.add(txtTenDm, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAdd = new JButton("Thêm danh mục");
        btnEdit = new JButton("Sửa danh mục");
        btnDelete = new JButton("Xóa danh mục");
        btnClear = new JButton("Làm mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.add(formPanel, BorderLayout.NORTH);
        northPanel.add(btnPanel, BorderLayout.SOUTH);

        // Table
        String[] columns = {"Mã DM", "Tên Danh Mục"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Events
        btnAdd.addActionListener(e -> addDanhMuc());
        btnEdit.addActionListener(e -> editDanhMuc());
        btnDelete.addActionListener(e -> deleteDanhMuc());
        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selectTableRow();
        });

        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        try {
            List<DanhMuc> list = danhMucBUS.findAll();
            for (DanhMuc dm : list) {
                tableModel.addRow(new Object[]{dm.getMaDm(), dm.getTenDm()});
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, "Lỗi nạp danh mục: " + ex.getMessage());
        }
    }

    private void addDanhMuc() {
        try {
            DanhMuc dm = new DanhMuc(0, txtTenDm.getText().trim());
            if (danhMucBUS.save(dm)) {
                MessageUtil.showInfo(this, "Thêm danh mục thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, ex.getMessage());
        }
    }

    private void editDanhMuc() {
        if (txtMaDm.getText().isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng chọn danh mục cần sửa từ bảng");
            return;
        }
        try {
            int ma = Integer.parseInt(txtMaDm.getText());
            DanhMuc dm = new DanhMuc(ma, txtTenDm.getText().trim());
            if (danhMucBUS.save(dm)) {
                MessageUtil.showInfo(this, "Cập nhật danh mục thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception ex) {
            MessageUtil.showError(this, ex.getMessage());
        }
    }

    private void deleteDanhMuc() {
        if (txtMaDm.getText().isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng chọn danh mục cần xóa");
            return;
        }
        int ma = Integer.parseInt(txtMaDm.getText());
        if (MessageUtil.showConfirm(this, "Bạn có chắc muốn xóa danh mục mã " + ma + "?", "Xác nhận xóa")) {
            try {
                if (danhMucBUS.delete(ma)) {
                    MessageUtil.showInfo(this, "Xóa danh mục thành công!");
                    clearForm();
                    loadData();
                }
            } catch (Exception ex) {
                MessageUtil.showError(this, ex.getMessage());
            }
        }
    }

    private void clearForm() {
        txtMaDm.setText("");
        txtTenDm.setText("");
        table.clearSelection();
    }

    private void selectTableRow() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            txtMaDm.setText(tableModel.getValueAt(r, 0).toString());
            txtTenDm.setText(tableModel.getValueAt(r, 1).toString());
        }
    }
}
