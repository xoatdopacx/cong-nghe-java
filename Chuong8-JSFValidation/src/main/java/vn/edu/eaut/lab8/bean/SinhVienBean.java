package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.SinhVien;
import vn.edu.eaut.lab8.repository.SinhVienRepository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Managed Bean xử lý CRUD sinh viên.
 * Bài 3: save, Bài 5: delete, Bài 9: edit, Bài 10: search, Bài 12: selectOneMenu.
 */
@Named("sinhVienBean")
@SessionScoped
public class SinhVienBean implements Serializable {
    private SinhVien sinhVien = new SinhVien();
    private final SinhVienRepository repo = new SinhVienRepository();
    private String keyword;
    private boolean editing = false;

    // Danh sách lớp cho selectOneMenu (Bài 12)
    private final List<String> dsLop = Arrays.asList(
            "DCCNTT15.10.1", "DCCNTT15.10.2", "DCCNTT15.10.3",
            "DCKTPM15.10.1", "DCKTPM15.10.2"
    );

    /**
     * Lưu sinh viên mới hoặc cập nhật sinh viên đang sửa.
     */
    public String save() {
        if (editing) {
            repo.update(sinhVien);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã cập nhật sinh viên"));
            editing = false;
        } else {
            repo.add(sinhVien);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã lưu sinh viên"));
        }
        sinhVien = new SinhVien();
        return null;
    }

    /**
     * Xóa sinh viên theo ID (Bài 5).
     */
    public void delete(int id) {
        repo.delete(id);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa sinh viên"));
    }

    /**
     * Chọn sinh viên để sửa (Bài 9).
     */
    public String edit(int id) {
        SinhVien sv = repo.findById(id);
        if (sv != null) {
            this.sinhVien = new SinhVien(sv.getId(), sv.getMaSinhVien(), sv.getHoTen(), sv.getEmail(), sv.getLop());
            this.editing = true;
        }
        return "sinhvien-form?faces-redirect=true";
    }

    /**
     * Hủy chế độ sửa.
     */
    public String cancelEdit() {
        this.sinhVien = new SinhVien();
        this.editing = false;
        return "sinhvien-form?faces-redirect=true";
    }

    /**
     * Lấy danh sách sinh viên, hỗ trợ tìm kiếm (Bài 10).
     */
    public List<SinhVien> getDsSinhVien() {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    // Getters & Setters
    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public boolean isEditing() { return editing; }
    public void setEditing(boolean editing) { this.editing = editing; }

    public List<String> getDsLop() { return dsLop; }
}
