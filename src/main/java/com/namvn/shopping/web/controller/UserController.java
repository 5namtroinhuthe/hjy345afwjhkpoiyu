package com.namvn.shopping.web.controller;

import com.namvn.shopping.service.UserService;
import com.namvn.shopping.social.autologin.Autologin;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.repository.UserDao;
import com.namvn.shopping.social.providers.FacebookProvider;

import com.namvn.shopping.web.form.UserDto;
import com.namvn.shopping.web.url.UrlAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    FacebookProvider facebookProvider;

//    @Autowired
//    GoogleProvider googleProvider;


    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Autologin autologin;

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String loginToFacebook(Model model) {
        return facebookProvider.getFacebookUserData(model, new User());
    }

//    @RequestMapping(value = "/google", method = RequestMethod.GET)
//    public String loginToGoogle(Model entity) {
//	return googleProvider.getGoogleUserData(entity, new User());
//    }


    @RequestMapping(value = {"/login"})
    public String login(@Valid UserDto user) {

        return "register";
    }

    @GetMapping("/registration")
    public String showRegistration() {
        return "register";
    }

    @PostMapping("/registration")
    public String registerUser(ModelAndView model, @Valid UserDto userBean, BindingResult bindingResult) {
//	if (bindingResult.hasErrors()) {
//	    return "registration";
//	}
//	userBean.setProvider("REGISTRATION");
        // Save the details in DB
        if (userService.findUserByEmail(userBean.getEmail()) == null) {
            if (StringUtils.isEmpty(userBean.getPassword())) {
                userBean.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
                userService.saveRegisteredUser(userBean);
                if (userBean.getUrl().startsWith(UrlAddress.PRODUCT_GET_ID))
                    return userBean.getUrl();
            }
        } else {
            model.addObject("messageRegister", userBean.getEmail() + " is exist. You's regiter by another email");
            return "register";
        }
        return null;
    }



    /** If we can't find a user/email combination */
    @RequestMapping("/login-error")
    public String loginError(Model model) {
	model.addAttribute("loginError", true);
	return "register";
    }

}
