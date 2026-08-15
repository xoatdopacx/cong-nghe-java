package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {
    private final ThongKeDAL thongKeDAL = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay == null || denNgay == null) {
            throw new IllegalArgumentException("Từ ngày và đến ngày không được để trống");
        }
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Từ ngày không được lớn hơn đến ngày");
        }
        return thongKeDAL.tinhDoanhThu(tuNgay, denNgay);
    }

    public HoaDon findHighestValueInvoice() throws SQLException {
        return thongKeDAL.findHighestValueInvoice();
    }

    public String findTopSellingProduct() throws SQLException {
        return thongKeDAL.findTopSellingProduct();
    }
}
