package com.namvn.shopping.filter;

import com.namvn.shopping.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.namvn.shopping.service.JwtService.HEADER_STRING;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURL().toString().contains("/user")) {
            String authToken = httpServletRequest.getHeader(HEADER_STRING);
            setAuthentication(authToken);
        } else {
            Cookie cookies[] = httpServletRequest.getCookies();
            for (Cookie cookie :
                    cookies) {
                if (cookie.getName().equals(HEADER_STRING)) {
                    String authToken = cookie.getValue();
                    if ("POST".equals(httpServletRequest.getMethod()) && authToken != null) {
                        setAuthentication(authToken);
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setAuthentication(String token) {
        try {
            Authentication authentication = JwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            e.printStackTrace();
        }
    }
}







