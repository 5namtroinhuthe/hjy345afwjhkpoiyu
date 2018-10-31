package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.*;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.UserOrderInfo;
import com.namvn.shopping.util.constant.UserOrderConstant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.*;

import java.util.List;

import static com.namvn.shopping.util.constant.BillConstant.BILL_ID;
import static com.namvn.shopping.util.constant.BillConstant.EMAIL;
import static com.namvn.shopping.util.constant.BillConstant.NAME_USER;
import static com.namvn.shopping.util.constant.CartConstant.CART_ID;
import static com.namvn.shopping.util.constant.ProductContants.NAME;
import static com.namvn.shopping.util.constant.ProductContants.PRODUCT_ID;
import static com.namvn.shopping.util.constant.UserOrderConstant.*;
@Repository
@Transactional
public class UserOrderImpl implements UserOrderDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void addOrder(UserOrder customerOrder) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customerOrder);
        session.flush();
        session.close();
    }

    @Override
    public void removeOrder(String verificationCode) {

    }

    @Override
    public void editAuthenticatingOrder(String verificationCode, int status) {

    }

    @Override
    public CriteriaQuery<UserOrder> queryOrderByStatus(Session session) {
        return null;
    }

    @Override

    public UserOrderInfo getOrderById(String orderId, String status) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserOrderInfo> criteriaQuery = builder.createQuery(UserOrderInfo.class);
        //USER ORDER
        Root<UserOrder> userDaoRoot = criteriaQuery.from(UserOrder.class);

        Join<Cart, UserOrder> cartUserOrderJoin = userDaoRoot.join(CART_ID, JoinType.INNER);
        Join<Bill, UserOrder> billUserOrderJoin = userDaoRoot.join(BILL_ID, JoinType.INNER);

        List<Selection<?>> selections = null;
        selections.add(userDaoRoot.get(UserOrderConstant.ORDER_ID));
        selections.add(userDaoRoot.get(STATUS));
        selections.add(billUserOrderJoin.get(NAME_USER));
        selections.add(billUserOrderJoin.get(EMAIL));
        selections.add(cartUserOrderJoin.get(CART_ID));
        Predicate predicate = builder.equal(userDaoRoot.get(UserOrderConstant.ORDER_ID), orderId);
        criteriaQuery.multiselect(selections)
                .where(predicate);
        Query<UserOrderInfo> query = session.createQuery(criteriaQuery);
        UserOrderInfo userOrderInfo = query.getSingleResult();

        //CART ITEM
        CriteriaQuery<ProductInfo> productInfoCriteriaQuery = builder.createQuery(ProductInfo.class);
        Root<CartItem> cartItemRoot = productInfoCriteriaQuery.from(CartItem.class);

        Join<Product, CartItem> productCartItemJoin = cartItemRoot.join(PRODUCT_ID, JoinType.INNER);
        criteriaQuery.multiselect(productCartItemJoin.get(PRODUCT_ID), productCartItemJoin.get(NAME))
                .where(builder.equal(cartItemRoot.get(CART_ID), query.getSingleResult().getCartId()));
        Query<ProductInfo> query1 = session.createQuery(productInfoCriteriaQuery);


        session.flush();
        session.close();
        return new UserOrderInfo(userOrderInfo.getOrderId(), userOrderInfo.getStatus(), userOrderInfo.getNameUser(), userOrderInfo.getEmail(), query1.getResultList());
    }

    @Override
    public PagingResult<UserOrder> getListOrder(int status) {
        return null;
    }
}
