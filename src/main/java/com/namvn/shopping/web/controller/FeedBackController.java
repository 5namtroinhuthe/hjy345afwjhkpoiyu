package com.namvn.shopping.web.controller;

import com.namvn.shopping.persistence.entity.FeedBack;
import com.namvn.shopping.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class FeedBackController {
    @Autowired
    private FeedBackService mFeedBackService;

    @RequestMapping(value = "/contactus", method = RequestMethod.POST)
    public String addQuery(@Valid @ModelAttribute(value = "contact") FeedBack feedBack, Model model, BindingResult result) {

        if (result.hasErrors())
            return "contactUs";

        mFeedBackService.add(feedBack);
        model.addAttribute("querySuccess",
                "Thank you, Your Message stored in our Server we will contact through corresponding Mail");
        return "register";

    }
}
