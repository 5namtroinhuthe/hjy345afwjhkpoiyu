package com.namvn.shopping.persistence.model;

public class CartItemInfo {
    private String productName;
    private String productImage;
    private float productprice;
    private int quanlity;
    private double price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public CartItemInfo(String productName, String productImage, float productprice, int quanlity, double price) {
        this.productName = productName;
        this.productImage = productImage;
        this.productprice = productprice;
        this.quanlity = quanlity;
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }



    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
