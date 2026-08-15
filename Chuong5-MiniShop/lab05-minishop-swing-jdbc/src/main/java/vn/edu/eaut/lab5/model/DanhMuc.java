package vn.edu.eaut.lab5.model;

public class DanhMuc {
    private int maDm;
    private String tenDm;

    public DanhMuc() {}

    public DanhMuc(int maDm, String tenDm) {
        this.maDm = maDm;
        this.tenDm = tenDm;
    }

    public int getMaDm() { return maDm; }
    public void setMaDm(int maDm) { this.maDm = maDm; }
    public String getTenDm() { return tenDm; }
    public void setTenDm(String tenDm) { this.tenDm = tenDm; }

    @Override
    public String toString() {
        return tenDm;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DanhMuc other = (DanhMuc) obj;
        return maDm == other.maDm;
    }
}
