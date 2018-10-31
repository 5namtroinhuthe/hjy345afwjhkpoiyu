package com.namvn.shopping.service;

import com.namvn.shopping.persistence.repository.CartDao;
import com.namvn.shopping.persistence.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao mCartDao;
    @Override
    public Cart getCartByCartId(Long cartId) {
        return mCartDao.getCartByCartId(cartId);
    }
}
