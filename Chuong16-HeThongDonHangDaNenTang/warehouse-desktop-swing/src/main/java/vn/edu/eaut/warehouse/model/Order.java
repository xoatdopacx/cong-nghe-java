package vn.edu.eaut.warehouse.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private long id;
    private long customerId;
    private String customerName;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String status;
    private String note;
    private int version;

    public Order() {}

    public Order(long id, long customerId, String customerName,
                 LocalDateTime createdAt, BigDecimal totalAmount,
                 String status, String note, int version) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.status = status;
        this.note = note;
        this.version = version;
    }

    public long getId()                   { return id; }
    public void setId(long id)            { this.id = id; }
    public long getCustomerId()           { return customerId; }
    public void setCustomerId(long v)     { this.customerId = v; }
    public String getCustomerName()       { return customerName; }
    public void setCustomerName(String v) { this.customerName = v; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public BigDecimal getTotalAmount()    { return totalAmount; }
    public void setTotalAmount(BigDecimal v) { this.totalAmount = v; }
    public String getStatus()             { return status; }
    public void setStatus(String status)  { this.status = status; }
    public String getNote()               { return note; }
    public void setNote(String note)      { this.note = note; }
    public int getVersion()               { return version; }
    public void setVersion(int v)         { this.version = v; }
}
