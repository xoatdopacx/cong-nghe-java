package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

/**
 * Entity Diem - quan hệ @ManyToOne với SinhVien và MonHoc.
 */
@Entity
@Table(name = "diem",
       uniqueConstraints = @UniqueConstraint(columnNames = {"sinh_vien_id", "mon_hoc_id"}))
public class Diem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mon_hoc_id", nullable = false)
    private MonHoc monHoc;

    @Column(name = "diem_so")
    private Double diemSo;

    @Column(name = "ghi_chu", length = 255)
    private String ghiChu;

    public Diem() {}

    public Diem(SinhVien sinhVien, MonHoc monHoc, Double diemSo) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
        this.diemSo = diemSo;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }

    public MonHoc getMonHoc() { return monHoc; }
    public void setMonHoc(MonHoc monHoc) { this.monHoc = monHoc; }

    public Double getDiemSo() { return diemSo; }
    public void setDiemSo(Double diemSo) { this.diemSo = diemSo; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    /**
     * Xếp loại theo thang điểm 10.
     */
    public String getXepLoai() {
        if (diemSo == null) return "Chưa có điểm";
        if (diemSo >= 8.5) return "Giỏi";
        if (diemSo >= 7.0) return "Khá";
        if (diemSo >= 5.5) return "Trung bình";
        if (diemSo >= 4.0) return "Yếu";
        return "Kém";
    }

    @Override
    public String toString() {
        return sinhVien.getHoTen() + " - " + monHoc.getTenMon() + ": " + diemSo;
    }
}
