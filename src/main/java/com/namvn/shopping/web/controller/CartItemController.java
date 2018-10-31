package com.namvn.shopping.web.controller;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.service.CartItemService;
import com.namvn.shopping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.namvn.shopping.web.url.UrlAddress.*;

@Controller
public class CartItemController {

    @Autowired
    private CartItemService mCartItemService;


    @RequestMapping(CART_ADD_PRODUCT_ID)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addCartItem(@PathVariable(value = "productId") String productId) {
        mCartItemService.addCartItem(productId);
    }

    @RequestMapping(CART_DELETE_CARTITEM_ID)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {
        mCartItemService.removeCartItem(cartItemId);
    }

    @RequestMapping(CART_DELETE_ALL_CART_ID)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllCartItems(@PathVariable(value = "cartId") Long cartId) {
       mCartItemService.removeAllCartItems(cartId);
    }
}
