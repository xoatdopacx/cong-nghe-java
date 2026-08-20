package vn.edu.eaut.lab8.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Model sách với Bean Validation.
 */
public class Sach {
    private int id;

    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;

    @NotBlank(message = "Tác giả không được để trống")
    private String tacGia;

    @Min(value = 1900, message = "Năm xuất bản phải từ 1900")
    @Max(value = 2030, message = "Năm xuất bản không hợp lệ")
    private int namXuatBan;

    private String theLoai;

    public Sach() {}

    public Sach(int id, String tenSach, String tacGia, int namXuatBan, String theLoai) {
        this.id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.namXuatBan = namXuatBan;
        this.theLoai = theLoai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public String getTacGia() { return tacGia; }
    public void setTacGia(String tacGia) { this.tacGia = tacGia; }

    public int getNamXuatBan() { return namXuatBan; }
    public void setNamXuatBan(int namXuatBan) { this.namXuatBan = namXuatBan; }

    public String getTheLoai() { return theLoai; }
    public void setTheLoai(String theLoai) { this.theLoai = theLoai; }
}
