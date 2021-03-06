package com.namvn.shopping.service;

import com.namvn.shopping.persistence.model.CartItemInfo;
import com.namvn.shopping.persistence.repository.CartDao;
import com.namvn.shopping.persistence.repository.CartItemDao;
import com.namvn.shopping.persistence.repository.ProductDao;
import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.CartItem;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfoUser;
import com.namvn.shopping.persistence.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private UserDao mUserRepository;
    @Autowired
    private ProductDao mProductDao;
    @Autowired
    private CartItemDao mCartItemDao;
    @Autowired
    private CartDao mCartDao;

    @Override
    public void addCartItem(String productId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();
        com.namvn.shopping.persistence.entity.User customer = mUserRepository.findByEmail(email);
        Cart cart = customer.getCart();
        if (cart != null) {
        List<CartItem> cartItems = cart.getCartItems();
        ProductInfoUser productInfo = mProductDao.getProductById(productId);
            for (int i = 0; i < cartItems.size(); i++) {
                CartItem cartItem = cartItems.get(i);
                if (productInfo.getProductId().equals(cartItem.getProduct().getProductId())) {
                    cartItem.setQuanlity(cartItem.getQuanlity() + 1);
                    cartItem.setPrice(cartItem.getQuanlity() * cartItem.getProduct().getPriceNew());
                    mCartItemDao.addCartItem(cartItem);
                    return;
                }
            }
            Product product = new Product(productInfo.getProductId(), productInfo.getName(), productInfo.getPriceNew(), productInfo.getPrices());
            CartItem cartItem = new CartItem();
            cartItem.setQuanlity(1);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPriceNew() * 1);
            cartItem.setCart(cart);
            mCartItemDao.addCartItem(cartItem);
        } else {
            Cart cartTmp=new Cart(0, new Date(), 1, customer);
            mCartDao.save(cartTmp);
            ProductInfoUser productInfo = mProductDao.getProductById(productId);
            Product product = new Product(productInfo.getProductId(), productInfo.getName(), productInfo.getPriceNew(), productInfo.getPrices());
            CartItem cartItem = new CartItem();
            cartItem.setQuanlity(1);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPriceNew() * 1);
            cartItem.setCart(cartTmp);
            mCartItemDao.addCartItem(cartItem);
        }
    }

    @Override
    public void removeCartItem(Long cardItemId) {
        mCartItemDao.removeCartItem(cardItemId);
    }

    @Override
    public void removeAllCartItems(Long cartId) {
        Cart cart = mCartDao.getCartByCartId(cartId);
        mCartItemDao.removeAllCartItems(cart);
    }

    @Override
    public List<CartItemInfo> getCartItem() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();
        com.namvn.shopping.persistence.entity.User customer = mUserRepository.findByEmail(email);
        Cart cart = customer.getCart();
        List<CartItemInfo> list=new ArrayList<>();
        if (cart != null) {
            List<CartItem> cartItems = cart.getCartItems();
            for (int i = 0; i < cartItems.size(); i++) {
                CartItem cartItem=cartItems.get(i);
                Product product=cartItem.getProduct();
                list.add(new CartItemInfo(product.getName(),
                        product.getImage(),
                        product.getPriceNew(),
                cartItem.getQuanlity(),
                        cartItem.getPrice()));
            }
            return list;
        }
        return null;
    }
}
