package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.Sach;
import vn.edu.eaut.lab8.repository.SachRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean xử lý CRUD sách (Bài 6).
 */
@Named("sachBean")
@SessionScoped
public class SachBean implements Serializable {
    private Sach sach = new Sach();
    private final SachRepository repo = new SachRepository();

    public String save() {
        repo.add(sach);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã lưu sách"));
        sach = new Sach();
        return null;
    }

    public void delete(int id) {
        repo.delete(id);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa sách"));
    }

    public List<Sach> getDsSach() { return repo.findAll(); }

    public Sach getSach() { return sach; }
    public void setSach(Sach sach) { this.sach = sach; }
}
