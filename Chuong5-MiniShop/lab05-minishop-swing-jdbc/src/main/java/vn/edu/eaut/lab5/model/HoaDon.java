package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private int maHd;
    private LocalDate ngayLap;
    private int maKh;
    private String tenKhachHang; // Helper field for UI display
    private BigDecimal tongTien;
    private String username;
    private List<ChiTietHoaDon> chiTietList = new ArrayList<>();

    public HoaDon() {}

    public HoaDon(int maHd, LocalDate ngayLap, int maKh, BigDecimal tongTien, String username) {
        this.maHd = maHd;
        this.ngayLap = ngayLap;
        this.maKh = maKh;
        this.tongTien = tongTien;
        this.username = username;
    }

    public int getMaHd() { return maHd; }
    public void setMaHd(int maHd) { this.maHd = maHd; }
    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }
    public int getMaKh() { return maKh; }
    public void setMaKh(int maKh) { this.maKh = maKh; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<ChiTietHoaDon> getChiTietList() { return chiTietList; }
    public void setChiTietList(List<ChiTietHoaDon> chiTietList) { this.chiTietList = chiTietList; }
}
