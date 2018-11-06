package com.namvn.shopping.social.providers;


import com.namvn.shopping.social.google.GoogleUserInfoTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.social.google.api.Google;
//import org.springframework.social.google.api.plus.Person;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


//@Service
public class GoogleProvider {
    // This is made possible because of @EnableOAuth2Client
    // and RequestContextListener.


//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        // May need an OAuth2AuthenticationEntryPoint for non-browser clients
//        return new LoginUrlAuthenticationEntryPoint(oauth2FilterCallbackPath);
//    }
//    private static final String REDIRECT_CONNECT_GOOGLE = "redirect:/login";
//    private static final String GOOGLE = "google";
//
//    @Autowired
//    BaseProvider baseProvider;
//
//
//    public String getGoogleUserData(Model entity, User userForm) {
//
//        ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
//        if (connectionRepository.findPrimaryConnection(GooglePojo.class) == null) {
//            return REDIRECT_CONNECT_GOOGLE;
//        }
//
//        populateUserDetailsFromGoogle(userForm);
//        //Save the details in DB
//        baseProvider.saveUserDetails(userForm);
//
//        //Login the User
//        baseProvider.autoLoginUser(userForm);
//
//        entity.addAttribute("loggedInUser", userForm);
//        return "secure/user";
//    }
//
//
//    	protected void populateUserDetailsFromGoogle(User userform) {
//		Google google =  baseProvider.getGoogle();
//		Person googleUser = google.plusOperations().getGoogleProfile();
//		userform.setEmail(googleUser.getAccountEmail());
//		userform.setFirstName(googleUser.getGivenName());
//		userform.setLastName(googleUser.getFamilyName());
//		userform.setImage(googleUser.getImageUrl());
//		userform.setProvider(GOOGLE);
//	}
//    public String populateUserDetailsFromGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
//        String code = request.getParameter("code");
//
//        if (code == null || code.isEmpty()) {
//            return "redirect:/login?google=error";
//        }
//        GoogleUtils googleUtils = baseProvider.getGoogle();
//        String accessToken = googleUtils.getToken(code);
//
//        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
//
////        UserDetails userDetail = googleUtils.buildUser(googlePojo);
////        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
////                userDetail.getAuthorities());
////        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return "redirect:/user";
//    }
}
