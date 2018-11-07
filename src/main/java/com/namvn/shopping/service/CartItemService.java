package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.CartItem;
import com.namvn.shopping.persistence.model.CartItemInfo;

import java.util.List;

public interface CartItemService {
    void addCartItem(String productId);
    void removeCartItem(Long cardItemId);
    void removeAllCartItems(Long cartId);
    List<CartItemInfo> getCartItem();
}
