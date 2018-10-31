package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class CartDaoImpl implements CartDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Cart getCartByCartId(Long cartId) {
        Session session = sessionFactory.getCurrentSession();
        Cart cart = session.get(Cart.class, cartId);
        return cart;
    }

    @Override
    public Cart validate(Long cartId) throws IOException {
        Cart cart = getCartByCartId(cartId);
        if (cart == null || cart.getCartItems().size() == 0) {
            throw new IOException(cartId + "");
        }
        update(cart);
        return cart;
    }

    @Override
    public void update(Cart cart) {
        float grandTotal = 0;
        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem item : cartItems) {
            grandTotal += item.getPrice();
        }
        cart.setTotalPrice(grandTotal);
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(cart);
    }
}
