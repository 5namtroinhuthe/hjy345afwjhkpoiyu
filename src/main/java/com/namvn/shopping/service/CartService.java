package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Cart;

public interface CartService {
    Cart getCartByCartId(Long cartId);
}
