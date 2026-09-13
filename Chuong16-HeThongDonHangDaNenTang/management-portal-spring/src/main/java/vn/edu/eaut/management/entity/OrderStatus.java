package vn.edu.eaut.management.entity;

public enum OrderStatus {
    PENDING,     // Khách hàng vừa đặt
    PROCESSING,  // Kho đã tiếp nhận và trừ tồn kho
    READY,       // Đã đóng gói, sẵn sàng giao
    SHIPPING,    // Quản lý đã phê duyệt giao
    COMPLETED,   // Khách hàng đã nhận
    CANCELLED    // Đơn đã bị hủy
}
