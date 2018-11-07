package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Cart;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.entity.UserOrder;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.RevenueInfo;
import com.namvn.shopping.persistence.model.UserOrderInfo;
import com.namvn.shopping.persistence.repository.CartDao;
import com.namvn.shopping.persistence.repository.ProductDao;
import com.namvn.shopping.persistence.repository.UserOrderDao;
import com.namvn.shopping.util.constant.MailConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    private CartDao mCartDao;
    @Autowired
    private ProductDao mProductDao;
    @Autowired
    private UserOrderDao mUserOrderDao;
    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void addUserOrder(Long cartId) {
        UserOrder customerOrder = new UserOrder();

        Cart cart = mCartDao.getCartByCartId(cartId);
        // Update CartId for customerOrder - set CartId
        customerOrder.setCart(cart);
        User user = cart.getUser();

        customerOrder.setUser(user);
        customerOrder.setBill(user.getBill());

        mUserOrderDao.addOrder(customerOrder);
    }

    @Override
    public String sendHtmlEmail(String orderId, String status) {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        /*
            Là một lớp trợ giúp để tạo ra một tin nhắn MIME,
            nó hỗ trợ image,
            và các tập tin đính kèm,
            và tạo ra các tin nhắn kiểu HTML.
        */
        MimeMessageHelper helper = null;
        UserOrderInfo userOrderInfo = mUserOrderDao.getOrderById(orderId, status);
        try {
            helper = new MimeMessageHelper(message, multipart, "utf-8");
            String htmlMsg = "<h3>Im testing send a HTML email</h3>"
                    + "<img src='http://www.apache.org/images/asf_logo_wide.gif'>";

            message.setContent(htmlMsg, "text/html");

            helper.setTo(MailConstant.FRIEND_EMAIL);
            helper.setSubject("E-SHOP Siêu thị quần áo số 1");
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return "Email Sent!";
    }


    @Override
    public RevenueInfo getSugesst(String id) {

        long count = mUserOrderDao.calRevenue();

        Product product = mProductDao.getProductById1(id);
        float revenue = product.getPriceNew() - product.getPriceInput();
     return  new RevenueInfo(count,revenue);

    }
}
