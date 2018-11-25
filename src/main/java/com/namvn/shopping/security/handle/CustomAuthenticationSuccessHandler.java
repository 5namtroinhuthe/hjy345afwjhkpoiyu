package com.namvn.shopping.security.handle;

import com.namvn.shopping.service.JwtService;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.security.ActiveUserStore;
import com.namvn.shopping.security.listener.LoggedUserSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static com.namvn.shopping.service.JwtService.HEADER_STRING;

/**
 * class to divide role then call a .html. Order 2
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtService csrfTokenRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private ActiveUserStore activeUserStore;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        handle(httpServletRequest, httpServletResponse, authentication);
        final HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(30 * 60);
            LoggedUserSessionListener user = new LoggedUserSessionListener(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
        }

        clearAuthenticationAttributes(httpServletRequest);
    }

    /**
     * @function: get url(abc.html) acording by role(user,admin,manage)
     */
    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(request, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed to the client. Unable to redirect to " + targetUrl);
            return;
        }
        String jwt = JwtService.addAuthentication( authentication);
        if(request.getRequestURL().toString().contains("/user")) response.addHeader(HEADER_STRING, jwt);
        //"XSRF-TOKEN"
        else response.addCookie(new Cookie(HEADER_STRING,jwt));
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected String determineTargetUrl(HttpServletRequest request, final Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        boolean isManager = false;

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("WRITE_PRIVILEGE")) {
                isAdmin = true;
                isUser = false;
                break;
            } else if (grantedAuthority.getAuthority().equals("READ_PRIVILEGE")) {
                isUser = true;
            }
        }
        if (isUser) {

//            cartItemService.addCartItem();
//            cartItemService.getCartItem();
            //?user=" + authentication.getName()
            return "/single-product-details.html";
        } else if (isAdmin) {
            return request.getRequestURL().toString();
        } else if (isManager) {
            return "";
        } else {
            throw new IllegalStateException();
        }
    }

}
