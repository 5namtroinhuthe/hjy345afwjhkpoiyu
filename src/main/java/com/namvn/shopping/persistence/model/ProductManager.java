package com.namvn.shopping.persistence.model;

import javax.validation.constraints.NotNull;

public class ProductManager extends ProductInfo{

    protected String detail;
//    private String color;
//    private String size;
//
//    private String manufacturer;
//    private String material;
//    private String madeIn;
    protected int quantity;

    public ProductManager(String productId, String name, String detail, int quantity) {
        super(productId, name);
        this.detail = detail;
        this.quantity = quantity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
