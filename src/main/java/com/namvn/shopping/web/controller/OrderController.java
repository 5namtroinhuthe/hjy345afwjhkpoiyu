package com.namvn.shopping.web.controller;

import com.namvn.shopping.persistence.model.RevenueInfo;
import com.namvn.shopping.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.namvn.shopping.web.url.UrlAddress.ORDER_CART_ID;

@Controller
public class OrderController {
    @Autowired
    private UserOrderService mUserOrderService;
    @RequestMapping(ORDER_CART_ID)
    public String addOrder(@PathVariable("cartId") Long cartId) {
        mUserOrderService.addUserOrder(cartId);
        return "redirect:/checkout?cartId=" + cartId;
    }
    @RequestMapping("/get/revenue")
    public ModelAndView getRevenue(){
      RevenueInfo list=  mUserOrderService.getSugesst(String.valueOf(1));
        return new ModelAndView("sugess","revenue",list);
    }
}
