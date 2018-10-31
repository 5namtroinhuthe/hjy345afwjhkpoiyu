package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.CartItem;

public interface CartItemDao {
    void addCartItem(CartItem cartItem);
    void removeCartItem(Long cardItemId);
    void removeAllCartItems(Cart cart);
}
