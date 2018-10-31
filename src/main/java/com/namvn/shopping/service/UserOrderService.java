package com.namvn.shopping.service;

public interface UserOrderService {
    void addUserOrder(Long cartId);
     String sendHtmlEmail(String orderId,String status) ;
}
