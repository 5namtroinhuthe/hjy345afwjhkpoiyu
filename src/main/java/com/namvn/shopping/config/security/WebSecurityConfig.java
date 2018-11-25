package com.namvn.shopping.config.security;

import com.namvn.shopping.filter.JWTAuthenticationFilter;
import com.namvn.shopping.security.CustomDaoAuthenticationProvider;
import com.namvn.shopping.security.CustomRememberMeServices;
import com.namvn.shopping.social.google.GoogleUserInfoTokenServices;
import com.namvn.shopping.web.error.CustomAccessDeniedHandler;
import com.namvn.shopping.web.error.RestAuthenticationEntryPoint;
import com.namvn.shopping.web.url.UrlAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.namvn.shopping.service.JwtService.HEADER_STRING;

//@formatter:off
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@PropertySource("classpath:google-oauth2.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
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

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**",
                "/static/fonts/**",
                "/static/img/bg-img/**",
                "/static/img/core-img/**",
                "/static/img/product-img/**",
                "/static/js/**",
                "/static/modal_login/css/**",
                "/static/modal_login/fonts/**",
                "/static/modal_login/js/vendor/**",

                "/static/register/.sass-cache/2af427b2894d1e78c5806e231aa35d00838a84e0/**",
                "/static/register/.sass-cache/632f5d1dd893d2e8764de51b8ca4120e25043113/**",
                "/static/register/.sass-cache/7643c8aa2e335ff5e104bc47d8dd7f1a0693bb39/**",
                "/static/register/.sass-cache/21880c0ffe15753a53f8402e9f64e1077e071510/**",
                "/static/register/.sass-cache/643489b018602f15119d8f44aeb7fc79468873a9/**",
                "/static/register/.sass-cache/f49285f1317a1cafa93ab4f5de346f92bbc40d58/**",
                "/static/register/css/**",
                "/static/register/fonts/material-icon/css/**",
                "/static/register/fonts/material-icon/fonts/**",
                "/static/register/fonts/montserrat/**",
                "/static/register/images/**",
                "/static/register/js/**",
                "/static/register/scss/common/**",
                "/static/register/scss/layouts/**",
                "/static/register/scss/**",
                "/static/register/vendor/jquery/**",
                "/static/scss/**",
                "/templates/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
                .authenticationEntryPoint(restServicesEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/css/**",
                        "/connect/**",
                        UrlAddress.PRODUCT_GET + "/**",
                        "/product/get?page=1",
                        UrlAddress.PRODUCT_GET_ID + "/**")
                .permitAll()
                .anyRequest().authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(UrlAddress.CART_ADD_PRODUCT_ID,
                        UrlAddress.CART_GET_ID,
                        UrlAddress.CART_DELETE_CARTITEM_ID)
                .hasRole("USER")
                .antMatchers("/user/updatePassword*", "/user/savePassword*", "/updatePassword*")
                .hasAuthority("CHANGE_PASSWORD_PRIVILEGE")

                .and()
                .formLogin()
                .loginPage(UrlAddress.PRODUCT_GET_ID)
                .defaultSuccessUrl("/index.html")
                .failureUrl("/login-error")
                .usernameParameter("email")//
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()


//
//
//                .and()

//        http.antMatcher("/rest/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint())

//                .antMatchers("/secure/**").anonymous()


                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(false)
                .logoutSuccessUrl("/logout.html?logSucc=true")
                .deleteCookies("JSESSIONID",HEADER_STRING)
                .permitAll()

                .and()
                .rememberMe().rememberMeServices(rememberMeServices()).key("theKey");

        http.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final CustomDaoAuthenticationProvider authProvider = new CustomDaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeServices;
    }

}

