package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

/**
 * Entity SinhVien - mapping với bảng sinh_vien.
 */
@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_sv", nullable = false, unique = true, length = 20)
    private String maSV;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "dien_thoai", length = 15)
    private String dienThoai;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lop_hoc_id")
    private LopHoc lopHoc;

    public SinhVien() {}

    public SinhVien(String maSV, String hoTen, String email, String dienThoai, String diaChi) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.email = email;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public LopHoc getLopHoc() { return lopHoc; }
    public void setLopHoc(LopHoc lopHoc) { this.lopHoc = lopHoc; }

    @Override
    public String toString() {
        return maSV + " - " + hoTen;
    }
}
