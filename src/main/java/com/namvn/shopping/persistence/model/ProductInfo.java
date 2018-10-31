package com.namvn.shopping.persistence.model;

public class ProductInfo {

    private String name;
    private String image;
    private float priceNew;
    private float prices;
    private String manufacturer;
    private String sale;
    private String productId;
    public ProductInfo(String productId, String name, float priceNew, float prices) {
        this.productId = productId;
        this.name = name;
        this.priceNew = priceNew;
        this.prices = prices;
    }
    public ProductInfo(String productId, String name, String image, float priceNew, float prices) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.priceNew = priceNew;
        this.prices = prices;
    }

    public ProductInfo(String name, String image, float prices,float priceNew,  String manufacturer) {
        this.name = name;
        this.image = image;
        this.prices = prices;
        this.priceNew = priceNew;
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(float priceNew) {
        this.priceNew = priceNew;
    }

    public float getPrices() {
        return prices;
    }

    public void setPrices(float prices) {
        this.prices = prices;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
