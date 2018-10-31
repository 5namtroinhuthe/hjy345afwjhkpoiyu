package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.CartItem;

public interface CartItemService {
    void addCartItem(String productId);
    void removeCartItem(Long cardItemId);
    void removeAllCartItems(Long cartId);
}
