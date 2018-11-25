package com.namvn.shopping.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namvn.shopping.security.LoginAttemptService;
import com.namvn.shopping.web.error.ErrorCode;
import com.namvn.shopping.web.error.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class CustomAuthenticationFailtureHanler extends SimpleUrlAuthenticationFailureHandler {
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

//    @Autowired
//    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);

        //final Locale locale = localeResolver.resolveLocale(request);

//        String errorMessage = messages.getMessage("message.badCredentials", null, locale);
//
//        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
//            errorMessage = messages.getMessage("Your account is disabled please check your mail and click on the confirmation link", null, locale);
//        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
//            errorMessage = messages.getMessage("Your registration token has expired. Please register again.", null, locale);
//        } else if (exception.getMessage().equalsIgnoreCase("blocked")) {
//            errorMessage = messages.getMessage("This ip is blocked for 24 hours", null, locale);
//        }
//        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
