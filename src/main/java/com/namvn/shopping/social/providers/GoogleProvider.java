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

@EnableOAuth2Client
@PropertySource("classpath:google-oauth2.properties")
//@Service
public class GoogleProvider {
    // This is made possible because of @EnableOAuth2Client
    // and RequestContextListener.
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    /**
     * <p>
     * Handles a {@link UserRedirectRequiredException} that is thrown from
     * {@link OAuth2ClientAuthenticationProcessingFilter}.
     * </p>
     * <p>
     * This bean is configured because of <code>@EnableOAuth2Client</code>.
     * </p>
     */
    @Autowired
    private OAuth2ClientContextFilter oauth2ClientContextFilter;

    @Value("${oauth2.clientId}")
    private String clientId;

    @Value("${oauth2.clientSecret}")
    private String clientSecret;

    @Value("${oauth2.userAuthorizationUri}")
    private String userAuthorizationUri;

    @Value("${oauth2.accessTokenUri}")
    private String accessTokenUri;

    @Value("${oauth2.tokenName:authorization_code}")
    private String tokenName;

    @Value("${oauth2.scope}")
    private String scope;

    @Value("${oauth2.userInfoUri}")
    private String userInfoUri;

    @Value("${oauth2.filterCallbackPath}")
    private String oauth2FilterCallbackPath;

    /**
     * todo: fuct to get Authorization Code
     *
     * @return AuthorizationCode
     */
    private OAuth2ProtectedResourceDetails authorizationCodeResource() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("google-oauth-client");
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setAccessTokenUri(accessTokenUri);
        details.setTokenName(tokenName);
        String commaSeparatedScopes = scope;
        details.setScope(parseScopes(commaSeparatedScopes));
        // Defaults to use current URI
        /*
         * If a pre-established redirect URI is used, it will need to be an
         * absolute URI. To do so, it'll need to compute the URI from a
         * request. The HTTP request object is available when you override
         * OAuth2ClientAuthenticationProcessingFilter#attemptAuthentication().
         *
         * details.setPreEstablishedRedirectUri(
         * 		env.getProperty("oauth2.redirectUrl"));
         * details.setUseCurrentUri(false);
         */
        details.setAuthenticationScheme(AuthenticationScheme.query);
        details.setClientAuthenticationScheme(AuthenticationScheme.form);
        return details;
    }

    private List<String> parseScopes(String commaSeparatedScopes) {
        List<String> scopes = new LinkedList<>();
        Collections.addAll(scopes, commaSeparatedScopes.split(","));
        return scopes;
    }

    /**
     * @return an OAuth2 client authentication processing filter
     */
    @Bean
    @Description("Filter that checks for authorization code, "
            + "and if there's none, acquires it from authorization server")
    public OAuth2ClientAuthenticationProcessingFilter
    oauth2ClientAuthenticationProcessingFilter() {
        // Used to obtain access token from authorization server (AS)
        OAuth2RestOperations restTemplate = new OAuth2RestTemplate(
                authorizationCodeResource(),
                oauth2ClientContext);
        OAuth2ClientAuthenticationProcessingFilter filter =
                new OAuth2ClientAuthenticationProcessingFilter(oauth2FilterCallbackPath);
        filter.setRestTemplate(restTemplate);
        // Set a service that validates an OAuth2 access token
        // We can use either Google API's UserInfo or TokenInfo
        // For this, we chose to use UserInfo service
        //Get infor account
        filter.setTokenServices(googleUserInfoTokenServices());
        return filter;
    }

    @Bean
    @Description("Google API UserInfo resource server")
    public GoogleUserInfoTokenServices googleUserInfoTokenServices() {
        GoogleUserInfoTokenServices userInfoTokenServices =
                new GoogleUserInfoTokenServices(userInfoUri, clientId);
        // TODO Configure bean to use local database to read authorities
        // userInfoTokenServices.setAuthoritiesExtractor(authoritiesExtractor);
        return userInfoTokenServices;
    }

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
