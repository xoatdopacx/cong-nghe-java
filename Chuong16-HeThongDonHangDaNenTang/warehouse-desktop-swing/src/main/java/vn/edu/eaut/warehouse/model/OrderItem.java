package vn.edu.eaut.warehouse.model;

import java.math.BigDecimal;

public class OrderItem {
    private long id;
    private long orderId;
    private long productId;
    private String productName;
    private String productCode;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public OrderItem() {}

    public long getId()                   { return id; }
    public void setId(long id)            { this.id = id; }
    public long getOrderId()              { return orderId; }
    public void setOrderId(long v)        { this.orderId = v; }
    public long getProductId()            { return productId; }
    public void setProductId(long v)      { this.productId = v; }
    public String getProductName()        { return productName; }
    public void setProductName(String v)  { this.productName = v; }
    public String getProductCode()        { return productCode; }
    public void setProductCode(String v)  { this.productCode = v; }
    public int getQuantity()              { return quantity; }
    public void setQuantity(int v)        { this.quantity = v; }
    public BigDecimal getUnitPrice()      { return unitPrice; }
    public void setUnitPrice(BigDecimal v){ this.unitPrice = v; }
    public BigDecimal getSubtotal()       { return subtotal; }
    public void setSubtotal(BigDecimal v) { this.subtotal = v; }
}
