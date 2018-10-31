package com.namvn.shopping.persistence.model;

import java.util.List;

public class UserOrderInfo {
    private Long orderId;
    private String status;

    private String nameUser;
    private String email;
    private Long cartId;
    List<ProductInfo> list;


    public UserOrderInfo(Long orderId, String status, String nameUser, String email, List list) {
        this.orderId = orderId;
        this.status = status;
        this.nameUser = nameUser;
        this.email = email;
        this.list = list;
    }

    public UserOrderInfo(Long orderId, String nameUser, String email) {
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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
