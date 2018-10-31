package com.namvn.shopping.web.controller;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.namvn.shopping.web.url.UrlAddress.CART_GET_ID;

@Controller
public class CartController {
    @Autowired
    private CartService mCartService;

    @RequestMapping(CART_GET_ID)
    public @ResponseBody
    Cart getCartItems(@PathVariable(value = "cartId") Long cartId) {
        return mCartService.getCartByCartId(cartId);
    }
}
