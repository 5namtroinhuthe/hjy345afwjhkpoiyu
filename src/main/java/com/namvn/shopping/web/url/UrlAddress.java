package com.namvn.shopping.web.url;

public class UrlAddress {
    public static final String HOME = "/eshop";
    public static final String LOGIN = "/login";
    public static final String LOGIN_FACEBOOK="/login/facebook";
    public static final String LOGIN_GOOGLE="/login/google";
    public static final String REGISTER = "/register";
    public static final String LOG_OUT = "/logout";

    public static final String PRODUCT_GET = "/product/get";
    public static final String PRODUCT_ADD = "/product/add";
    public static final String PRODUCT_EDIT = "product/edit";
    public static final String PRODUCT_DELETE = "product/delete/{productId}";
    public static final String PRODUCT_GET_ID = "/product/getId/{productId}";

    public static final String ORDER_CART_ID = "/order/{cartId}";
    public static final String ORDER_GMAIL="/order/gmail";

    public static final String CART_ADD_PRODUCT_ID = "/cart/add/{productId}";
    public static final String CART_GET_ID = "/cart/get/{cartId}";
    public static final String CART_DELETE_CARTITEM_ID = "/cart/delete/{cartItemId}";
    public static final String CART_DELETE_ALL_CART_ID = "/cart//delete/all/{cartId}";

    public static final String COMPANY_ADD = "/company/add";
    public static final String COMPANY_EDIT = "/company/edit";
    public static final String COMPANY_REMOVE = "/company/remove";
    public static final String COMPANY_GET = "/company/get";



    public static final String ACCOUNT_GET="/account/get";
    public static final String ACCOUNT_EDIT="/account/edit";


}
