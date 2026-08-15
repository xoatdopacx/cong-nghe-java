package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;

public class SanPham {
    private int maSp;
    private String tenSp;
    private BigDecimal donGia;
    private int soLuong;
    private Integer maDm;
    private String tenDm; // Helper field for display

    public SanPham() {}

    public SanPham(int maSp, String tenSp, BigDecimal donGia, int soLuong, Integer maDm) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.maDm = maDm;
    }

    public int getMaSp() { return maSp; }
    public void setMaSp(int maSp) { this.maSp = maSp; }
    public String getTenSp() { return tenSp; }
    public void setTenSp(String tenSp) { this.tenSp = tenSp; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public Integer getMaDm() { return maDm; }
    public void setMaDm(Integer maDm) { this.maDm = maDm; }
    public String getTenDm() { return tenDm; }
    public void setTenDm(String tenDm) { this.tenDm = tenDm; }

    @Override
    public String toString() {
        return tenSp + " (Mã: " + maSp + " - Tồn: " + soLuong + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SanPham other = (SanPham) obj;
        return maSp == other.maSp;
    }
}
