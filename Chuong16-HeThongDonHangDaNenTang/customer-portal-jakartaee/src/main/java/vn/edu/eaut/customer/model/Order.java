package vn.edu.eaut.customer.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long id;
    private long customerId;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String status;
    private String note;
    private List<OrderItem> items;

    public long getId()                      { return id; }
    public void setId(long id)               { this.id = id; }
    public long getCustomerId()              { return customerId; }
    public void setCustomerId(long v)        { this.customerId = v; }
    public LocalDateTime getCreatedAt()      { return createdAt; }
    public void setCreatedAt(LocalDateTime v){ this.createdAt = v; }
    public BigDecimal getTotalAmount()       { return totalAmount; }
    public void setTotalAmount(BigDecimal v) { this.totalAmount = v; }
    public String getStatus()                { return status; }
    public void setStatus(String v)          { this.status = v; }
    public String getNote()                  { return note; }
    public void setNote(String v)            { this.note = v; }
    public List<OrderItem> getItems()        { return items; }
    public void setItems(List<OrderItem> v)  { this.items = v; }
}
