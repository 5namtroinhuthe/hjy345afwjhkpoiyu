package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.UserOrder;
import com.namvn.shopping.persistence.model.UserOrderInfo;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;

public interface UserOrderDao {
    void addOrder(UserOrder customerOrder);

    void removeOrder(String verificationCode);

    void editAuthenticatingOrder(String verificationCode, int status);

    CriteriaQuery<UserOrder> queryOrderByStatus(Session session);

    UserOrderInfo getOrderById(String orderId, String status);

    PagingResult<UserOrder> getListOrder(int status);

}
