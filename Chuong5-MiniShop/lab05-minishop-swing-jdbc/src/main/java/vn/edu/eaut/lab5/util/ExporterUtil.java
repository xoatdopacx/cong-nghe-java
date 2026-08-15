package vn.edu.eaut.lab5.util;

import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ExporterUtil {

    public static File exportHoaDonTxt(HoaDon hd, String destinationDir) throws IOException {
        File dir = new File(destinationDir);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, "HoaDon_" + hd.getMaHd() + ".txt");
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("====================================================\n");
            writer.write("              HÓA ĐƠN BÁN HÀNG - MINISHOP            \n");
            writer.write("====================================================\n");
            writer.write("Mã hóa đơn: " + hd.getMaHd() + "\n");
            writer.write("Ngày lập  : " + hd.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("Khách hàng: " + (hd.getTenKhachHang() != null ? hd.getTenKhachHang() : "Khách lẻ") + "\n");
            writer.write("Nhân viên : " + (hd.getUsername() != null ? hd.getUsername() : "Hệ thống") + "\n");
            writer.write("----------------------------------------------------\n");
            writer.write(String.format("%-25s %-10s %-12s %-12s\n", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền"));
            writer.write("----------------------------------------------------\n");

            for (ChiTietHoaDon ct : hd.getChiTietList()) {
                writer.write(String.format("%-25s %-10d %-12s %-12s\n",
                        truncate(ct.getTenSp(), 24),
                        ct.getSoLuong(),
                        nf.format(ct.getDonGia()),
                        nf.format(ct.getThanhTien())));
            }

            writer.write("----------------------------------------------------\n");
            writer.write("TỔNG CỘNG TIỀN THANH TOÁN: " + nf.format(hd.getTongTien()) + " VNĐ\n");
            writer.write("====================================================\n");
            writer.write("       Cảm ơn quý khách và hẹn gặp lại!             \n");
        }
        return file;
    }

    public static File exportHoaDonCsv(HoaDon hd, String destinationDir) throws IOException {
        File dir = new File(destinationDir);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, "HoaDon_" + hd.getMaHd() + ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("MaHoaDon,NgayLap,TenKhachHang,NhanVien,TongTien\n");
            writer.write(String.format("%d,%s,%s,%s,%.2f\n\n",
                    hd.getMaHd(),
                    hd.getNgayLap(),
                    hd.getTenKhachHang(),
                    hd.getUsername(),
                    hd.getTongTien()));

            writer.write("MaSP,TenSP,SoLuong,DonGia,ThanhTien\n");
            for (ChiTietHoaDon ct : hd.getChiTietList()) {
                writer.write(String.format("%d,%s,%d,%.2f,%.2f\n",
                        ct.getMaSp(),
                        ct.getTenSp(),
                        ct.getSoLuong(),
                        ct.getDonGia(),
                        ct.getThanhTien()));
            }
        }
        return file;
    }

    private static String truncate(String text, int width) {
        if (text == null) return "";
        if (text.length() <= width) return text;
        return text.substring(0, width - 3) + "...";
    }
}
