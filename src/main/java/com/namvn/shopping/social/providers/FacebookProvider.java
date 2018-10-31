package com.namvn.shopping.social.providers;

import com.namvn.shopping.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class FacebookProvider {

    private static final String FACEBOOK = "facebook";
    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    BaseProvider baseProvider;


    public String getFacebookUserData(Model model, User userForm) {

        ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return REDIRECT_LOGIN;
        }
        //Populate the Bean
        populateUserDetailsFromFacebook(userForm);
        //Save the details in DB
        baseProvider.saveUserDetails(userForm);
        //Login the User
        baseProvider.autoLoginUser(userForm);
        model.addAttribute("loggedInUser", userForm);
        return "secure/user";
    }

    /**
     * The function is used to setting user's info
     */
    protected void populateUserDetailsFromFacebook(User userForm) {
        Facebook facebook = baseProvider.getFacebook();
        org.springframework.social.facebook.api.User user = facebook.userOperations().getUserProfile();

        userForm.setEmail(user.getEmail());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setImage(user.getCover().getSource());
        userForm.setProvider(FACEBOOK);
    }

//    @GetMapping("/facebook")
//    public void createFacebookAccessToken(HttpServletRequest request) {
//String code = request.getParameter("code");
//
//    if (code == null || code.isEmpty()) {
//        return "redirect:/login?facebook=error";
//    }
//String link = String.format(env.getProperty("facebook.link.get.token"), env.getProperty("facebook.app.id"),
//        env.getProperty("facebook.app.secret"), env.getProperty("facebook.redirect.uri"), code);
//    String response = Request.Get(link).execute().returnContent().asString();
//    ObjectMapper mapper = new ObjectMapper();
//    JsonNode node = mapper.readTree(response).get("access_token");
//    return node.textValue();
//        String accessToken;
//        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookService.getFacebookAppId(), facebookService.getFacebookSecret());
//        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/facebook", null);
//        accessToken = accessGrant.getAccessToken();
//        Facebook facebook = new FacebookTemplate(accessToken, "myapp");
//
//    }


}
