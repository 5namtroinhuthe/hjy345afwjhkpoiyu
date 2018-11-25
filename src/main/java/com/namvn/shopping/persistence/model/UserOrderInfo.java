package com.namvn.shopping.persistence.model;

import java.util.List;

public class UserOrderInfo {
    private Long orderId;
    private String status;

    private String nameUser;
    private String email;
    private String productId;
    private String productName;


    public UserOrderInfo(Long orderId, String status, String nameUser, String email, List list) {
        this.orderId = orderId;
        this.status = status;
        this.nameUser = nameUser;
        this.email = email;

    }

    public UserOrderInfo(Long orderId, String nameUser, String email) {
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public UserOrderInfo(Long orderId, String status, String nameUser, String email, String productId, String productName) {
        this.orderId = orderId;
        this.status = status;
        this.nameUser = nameUser;
        this.email = email;
        this.productId = productId;
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
