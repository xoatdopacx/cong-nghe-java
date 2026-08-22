package vn.edu.eaut.lab9.entity;

import jakarta.persistence.*;

/**
 * Bài 13: Entity SanPham - module bổ sung.
 */
@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_sp", nullable = false, unique = true, length = 20)
    private String maSP;

    @Column(name = "ten_sp", nullable = false, length = 150)
    private String tenSP;

    @Column(name = "don_gia")
    private Double donGia;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "danh_muc", length = 100)
    private String danhMuc;

    public SanPham() {}

    public SanPham(String maSP, String tenSP, Double donGia, Integer soLuong, String danhMuc) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.danhMuc = danhMuc;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public Double getDonGia() { return donGia; }
    public void setDonGia(Double donGia) { this.donGia = donGia; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getDanhMuc() { return danhMuc; }
    public void setDanhMuc(String danhMuc) { this.danhMuc = danhMuc; }

    /**
     * Tính thành tiền = đơn giá * số lượng.
     */
    public Double getThanhTien() {
        if (donGia != null && soLuong != null) {
            return donGia * soLuong;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return maSP + " - " + tenSP;
    }
}
