package com.namvn.shopping.service;

import com.google.gson.Gson;
import com.namvn.shopping.persistence.entity.Privilege;
import com.namvn.shopping.persistence.entity.Role;
import com.namvn.shopping.persistence.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security
        .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.google.gson.reflect.TypeToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Type;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class TokenAuthenticationService {
    private static final long EXPIRATIONTIME = 864_000_000; // 10 days
    private static final String SECRET = "ThisIsASecret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    public static String addAuthentication(User user) {
        Gson gson = new Gson();
        String roles = gson.toJson(user.getRoles());

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userId", user.getUserId());
        claims.put("role", roles);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(HEADER_STRING)) {
                    String token = cookie.getValue();
                    if (token != null) {
                        // parse the token.
                        Claims body = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                                .getBody();
                        Gson gson = new Gson();
                        Type type = new TypeToken<Collection<Role>>() {}.getType();
                        String role = (String) body.get("role");
                        Collection<Role> roles = gson.fromJson(role, type);
                        return body != null ?
                                new UsernamePasswordAuthenticationToken(body.getSubject(), null, getAuthorities(roles)) :
                                null;
                    }
                }
            }
        }
        return null;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private static List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private static List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
}