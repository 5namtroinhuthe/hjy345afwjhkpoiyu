package com.namvn.shopping.web.controller;

import com.namvn.shopping.social.autologin.Autologin;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.repository.UserDao;
import com.namvn.shopping.social.providers.FacebookProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private UserDao userRepository;

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



    @RequestMapping(value = {"/login" })
    public String login() {
	return "login";
    }

    @GetMapping("/registration")
    public String showRegistration(User userBean) {
	return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(HttpServletResponse httpServletResponse, Model model, @Valid User userBean, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
	    return "registration";
	}
	userBean.setProvider("REGISTRATION");
	// Save the details in DB
	if (StringUtils.isEmpty(userBean.getPassword())) {
	    userBean.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
	}
	userRepository.addUser(userBean);

	autologin.setSecuritycontext(userBean);

	model.addAttribute("loggedInUser", userBean);
	return "secure/user";
    }

    /** If we can't find a user/email combination */
    @RequestMapping("/login-error")
    public String loginError(Model model) {
	model.addAttribute("loginError", true);
	return "login";
    }

}
