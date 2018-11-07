package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.repository.CartDao;
import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao mCartDao;
    @Autowired
    private UserDao mUserDao;
    @Override
    public Cart getCartByCartId(Long cartId) {
        return mCartDao.getCartByCartId(cartId);
    }
}
