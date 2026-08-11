package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bai08QuanLySinhVien extends JFrame {

    // ─── Model ──────────────────────────────────────────────────
    static class Student {
        String id, name;
        double gpa;
        Student(String id, String name, double gpa) {
            this.id   = id;
            this.name = name;
            this.gpa  = gpa;
        }
        String rank() {
            if (gpa >= 8.5) return "Giỏi";
            if (gpa >= 7.0) return "Khá";
            if (gpa >= 5.0) return "Trung bình";
            return "Yếu";
        }
    }

    // ─── State ──────────────────────────────────────────────────
    private final List<Student>          students   = new ArrayList<>();
    private final String[]               columns    = {"Mã SV", "Họ tên", "ĐTB", "Xếp loại"};
    private final DefaultTableModel      tableModel = new DefaultTableModel(columns, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    // ─── Input fields ───────────────────────────────────────────
    private final JTextField txtId   = new JTextField(10);
    private final JTextField txtName = new JTextField(18);
    private final JTextField txtGpa  = new JTextField(6);

    // ─── Table ──────────────────────────────────────────────────
    private final JTable table = new JTable(tableModel);

    public Bai08QuanLySinhVien() {
        setTitle("Bài 8 - Quản lý sinh viên bằng JTable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ── Input panel ──
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        inputPanel.add(new JLabel("Mã SV:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("Họ tên:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("ĐTB (0-10):")); inputPanel.add(txtGpa);

        // ── Buttons ──
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnAdd    = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear  = new JButton("Làm mới");
        btnPanel.add(btnAdd); btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete); btnPanel.add(btnClear);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(inputPanel, BorderLayout.CENTER);
        topSection.add(btnPanel,   BorderLayout.SOUTH);

        // ── Table ──
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));

        // ── Events ──
        btnAdd.addActionListener(e -> themSinhVien());
        btnUpdate.addActionListener(e -> suaSinhVien());
        btnDelete.addActionListener(e -> xoaSinhVien());
        btnClear.addActionListener(e -> lamMoi());

        // Click row → fill fields
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) dieuHuongRowToFields();
        });

        add(topSection, BorderLayout.NORTH);
        add(scroll,     BorderLayout.CENTER);

        setSize(680, 480);
        setLocationRelativeTo(null);
    }

    // ── Helpers ─────────────────────────────────────────────────
    private boolean validateInput(boolean requireId) {
        String id   = txtId.getText().trim();
        String name = txtName.getText().trim();
        String gpaStr = txtGpa.getText().trim();
        if (requireId && id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtId.requestFocus(); return false;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus(); return false;
        }
        try {
            double gpa = Double.parseDouble(gpaStr);
            if (gpa < 0 || gpa > 10) {
                JOptionPane.showMessageDialog(this, "ĐTB phải trong khoảng 0 – 10!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                txtGpa.requestFocus(); return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ĐTB phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtGpa.requestFocus(); return false;
        }
        return true;
    }

    private void themSinhVien() {
        if (!validateInput(true)) return;
        String id = txtId.getText().trim();
        for (Student s : students) {
            if (s.id.equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(this, "Mã SV đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        Student sv = new Student(id, txtName.getText().trim(), Double.parseDouble(txtGpa.getText().trim()));
        students.add(sv);
        tableModel.addRow(new Object[]{sv.id, sv.name, String.format("%.2f", sv.gpa), sv.rank()});
        lamMoi();
    }

    private void suaSinhVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateInput(false)) return;
        Student sv = students.get(row);
        sv.name = txtName.getText().trim();
        sv.gpa  = Double.parseDouble(txtGpa.getText().trim());
        tableModel.setValueAt(sv.name, row, 1);
        tableModel.setValueAt(String.format("%.2f", sv.gpa), row, 2);
        tableModel.setValueAt(sv.rank(), row, 3);
        lamMoi();
    }

    private void xoaSinhVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa sinh viên: " + students.get(row).name + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            students.remove(row);
            tableModel.removeRow(row);
            lamMoi();
        }
    }

    private void lamMoi() {
        txtId.setText(""); txtName.setText(""); txtGpa.setText("");
        table.clearSelection();
        txtId.requestFocus();
    }

    private void dieuHuongRowToFields() {
        int row = table.getSelectedRow();
        if (row >= 0 && row < students.size()) {
            Student sv = students.get(row);
            txtId.setText(sv.id);
            txtName.setText(sv.name);
            txtGpa.setText(String.format("%.2f", sv.gpa));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai08QuanLySinhVien().setVisible(true));
    }
}
