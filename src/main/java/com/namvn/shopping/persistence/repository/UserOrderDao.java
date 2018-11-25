package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.UserOrder;
import com.namvn.shopping.persistence.model.UserOrderInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.util.HashMap;

public interface UserOrderDao {
    void addOrder(UserOrder customerOrder);

    void removeOrder(String verificationCode);

    void editAuthenticatingOrder(String verificationCode, int status);

    CriteriaQuery<UserOrder> queryOrderByStatus(Session session);

    UserOrderInfo getOrderById(String orderId, String status);


    long countProductInOder(String productId);

    Query<UserOrderInfo> queryOrderNotSend(Session session, String status, Long catergory);

     PagingResult<UserOrderInfo> getListOrderNotSend(int page, int limit, String status, Long catergory);

    HashMap caculateRevenueProducts(HashMap hashMap);

    HashMap caculateRevenueProduct(String productId);

    PagingResult<UserOrder> getListOrder(int status);

}
