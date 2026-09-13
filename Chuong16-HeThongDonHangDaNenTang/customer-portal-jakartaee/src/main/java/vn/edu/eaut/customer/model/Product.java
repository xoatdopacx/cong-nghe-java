package vn.edu.eaut.customer.model;

import java.math.BigDecimal;

public class Product {
    private long id;
    private String code;
    private String name;
    private BigDecimal price;
    private int stock;
    private boolean active;

    public long getId()                    { return id; }
    public void setId(long id)             { this.id = id; }
    public String getCode()                { return code; }
    public void setCode(String v)          { this.code = v; }
    public String getName()                { return name; }
    public void setName(String v)          { this.name = v; }
    public BigDecimal getPrice()           { return price; }
    public void setPrice(BigDecimal v)     { this.price = v; }
    public int getStock()                  { return stock; }
    public void setStock(int v)            { this.stock = v; }
    public boolean isActive()              { return active; }
    public void setActive(boolean v)       { this.active = v; }
}
