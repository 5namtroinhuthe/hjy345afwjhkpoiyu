package com.namvn.shopping.service;

import com.namvn.shopping.persistence.model.SugesstProductImport;

import java.util.ArrayList;

public interface UserOrderService {
    void addUserOrder(Long cartId);
     String sendHtmlEmail(String orderId,String status) ;
    ArrayList<SugesstProductImport> getSugesst(int page, int limit, int quantity, String sortType, Long catergory);
}
