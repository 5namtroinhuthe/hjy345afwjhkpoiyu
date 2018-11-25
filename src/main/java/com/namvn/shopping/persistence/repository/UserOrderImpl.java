package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.*;
import com.namvn.shopping.persistence.model.UserOrderInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.criteria.*;

import java.util.HashMap;
import java.util.List;


import static com.namvn.shopping.util.constant.ProductContants.NAME;
import static com.namvn.shopping.util.constant.ProductContants.PRODUCT_ID;


@Repository
public class UserOrderImpl implements UserOrderDao {
    public static final String ORDER_ID = "order_id";
    public static final String STATUS = "status";
    public static final String USER_ID = "user_id";
    public static final String STATUS_VERIFICATION = "Đã xác nhận";
    public static final String STATUS_EXPORT = "Đã xuất kho";

    public static final String NAME_USER = "name_user";
    public static final String EMAIL = "email";
    public static final String BILL_ID = "bill_id";
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

        Root<UserOrder> userOrderRoot = criteriaQuery.from(UserOrder.class);
        Join<UserOrder, Bill> userOrderBillJoin = userOrderRoot.join("bill");
        Join<UserOrder, Cart> cartUserOrderJoin = userOrderBillJoin.join("cart");
        Join<CartItem, Cart> cartItemCartJoin = cartUserOrderJoin.join("cart");
        Join<CartItem, Product> cartItemProductJoin = cartItemCartJoin.join("product");

        List<Selection<?>> selections = null;
        selections.add(cartItemProductJoin.get(ORDER_ID));
        selections.add(cartItemProductJoin.get(STATUS));
        selections.add(cartItemProductJoin.get(NAME_USER));
        selections.add(cartItemProductJoin.get(EMAIL));
        selections.add(cartItemProductJoin.get(PRODUCT_ID));
        selections.add(cartItemProductJoin.get(NAME));
        Predicate predicate[] = new Predicate[2];
        predicate[0] = builder.equal(cartItemProductJoin.get(ORDER_ID), orderId);
        predicate[1] = builder.equal(cartItemProductJoin.get(STATUS), status);
        criteriaQuery.multiselect(selections)
                .where(builder.and(predicate[0], predicate[1]));
        Query<UserOrderInfo> query = session.createQuery(criteriaQuery);
        UserOrderInfo userOrderInfo = query.getSingleResult();
        session.flush();
        session.close();
        return userOrderInfo;
    }

    /**
     * todo: get list UserOderInfo.class not send
     *
     * @param status
     * @param catergory
     */

    @Override
    public Query<UserOrderInfo> queryOrderNotSend(Session session, String status, Long catergory) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserOrderInfo> criteriaQuery = builder.createQuery(UserOrderInfo.class);
        //USER ORDER

        Root<UserOrder> userOrderRoot = criteriaQuery.from(UserOrder.class);
        Join<UserOrder, Bill> userOrderBillJoin = userOrderRoot.join("bill");
        Join<UserOrder, Cart> cartUserOrderJoin = userOrderBillJoin.join("cart");
        Join<CartItem, Cart> cartItemCartJoin = cartUserOrderJoin.join("cart");
        Join<CartItem, Product> cartItemProductJoin = cartItemCartJoin.join("product");

        List<Selection<?>> selections = null;
        selections.add(cartItemProductJoin.get(ORDER_ID));
        selections.add(cartItemProductJoin.get(STATUS));
        selections.add(cartItemProductJoin.get(NAME_USER));
        selections.add(cartItemProductJoin.get(EMAIL));
        selections.add(cartItemProductJoin.get(PRODUCT_ID));
        selections.add(cartItemProductJoin.get(NAME));
        Predicate predicate[] = new Predicate[2];
        predicate[0] = builder.equal(cartItemProductJoin.get(STATUS), status);
        predicate[1] = builder.equal(cartItemProductJoin.get("catergoryId"), catergory);
        criteriaQuery.multiselect(selections)
                .where(builder.and(predicate[0], predicate[1]));
        Query<UserOrderInfo> query = session.createQuery(criteriaQuery);
        return query;
    }

    @Override
    public PagingResult<UserOrderInfo> getListOrderNotSend(int page, int limit, String status, Long catergory) {
        Query query = queryOrderNotSend(sessionFactory.getCurrentSession(), status, catergory);
        return new PagingResult<UserOrderInfo>(query, page, limit);
    }

    @Override
    public long countProductInOder(String productId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

        Root<UserOrder> userOrderRoot = criteriaQuery.from(UserOrder.class);
        Join<UserOrder, Cart> cartUserOrderJoin = userOrderRoot.join("cart");
        Join<CartItem, Cart> cartItemCartJoin = cartUserOrderJoin.join("cart");
        Join<CartItem, Product> cartItemProductJoin = cartItemCartJoin.join("product");
        criteriaQuery.select(builder.count(cartItemProductJoin.get(PRODUCT_ID)))
                .where(builder.equal(cartItemProductJoin.get(PRODUCT_ID), productId));
        Query<Long> query = session.createQuery(criteriaQuery);
        long count = query.getSingleResult();

        //USER ORDER
        return count;
    }

    @Override
    public HashMap caculateRevenueProducts(HashMap hashMap) {
        return null;
    }

    @Override
    public HashMap caculateRevenueProduct(String productId) {


        return null;
    }


    @Override
    public PagingResult<UserOrder> getListOrder(int status) {
        return null;
    }
}
