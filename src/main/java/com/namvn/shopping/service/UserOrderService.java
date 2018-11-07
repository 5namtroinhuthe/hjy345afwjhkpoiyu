package com.namvn.shopping.service;

import com.namvn.shopping.persistence.model.RevenueInfo;

import java.util.List;

public interface UserOrderService {
    void addUserOrder(Long cartId);
     String sendHtmlEmail(String orderId,String status) ;
     RevenueInfo getSugesst(String id);
}
