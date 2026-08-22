package vn.edu.eaut.lab9.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bài 7: Entity MonHoc (Môn học).
 */
@Entity
@Table(name = "mon_hoc")
public class MonHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_mon", nullable = false, unique = true, length = 20)
    private String maMon;

    @Column(name = "ten_mon", nullable = false, length = 100)
    private String tenMon;

    @Column(name = "so_tin_chi")
    private int soTinChi;

    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diem> diems = new ArrayList<>();

    public MonHoc() {}

    public MonHoc(String maMon, String tenMon, int soTinChi) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public int getSoTinChi() { return soTinChi; }
    public void setSoTinChi(int soTinChi) { this.soTinChi = soTinChi; }

    public List<Diem> getDiems() { return diems; }
    public void setDiems(List<Diem> diems) { this.diems = diems; }

    @Override
    public String toString() {
        return maMon + " - " + tenMon;
    }
}
