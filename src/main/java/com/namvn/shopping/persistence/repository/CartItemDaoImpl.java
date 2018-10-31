package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public class CartItemDaoImpl implements CartItemDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void addCartItem(CartItem cartItem) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(cartItem);
    }

    @Override
    public void removeCartItem(Long cardItemId) {
        Session session = sessionFactory.getCurrentSession();
        CartItem cartItem =  session.get(CartItem.class, cardItemId);
        session.delete(cartItem);
        Cart cart = cartItem.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.remove(cartItem);
    }

    @Override
    public void removeAllCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            removeCartItem(cartItem.getCartItemId());
        }
    }
}
