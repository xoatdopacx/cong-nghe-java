package vn.edu.eaut.lab9.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bài 6: Entity LopHoc - quan hệ @OneToMany với SinhVien.
 */
@Entity
@Table(name = "lop_hoc")
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_lop", nullable = false, unique = true, length = 20)
    private String maLop;

    @Column(name = "ten_lop", nullable = false, length = 100)
    private String tenLop;

    @Column(name = "khoa", length = 100)
    private String khoa;

    @OneToMany(mappedBy = "lopHoc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SinhVien> sinhViens = new ArrayList<>();

    public LopHoc() {}

    public LopHoc(String maLop, String tenLop, String khoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoa = khoa;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }

    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }

    public List<SinhVien> getSinhViens() { return sinhViens; }
    public void setSinhViens(List<SinhVien> sinhViens) { this.sinhViens = sinhViens; }

    @Override
    public String toString() {
        return maLop + " - " + tenLop;
    }
}
