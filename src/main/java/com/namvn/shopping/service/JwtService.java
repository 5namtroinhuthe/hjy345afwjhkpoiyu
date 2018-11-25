package com.namvn.shopping.service;

import com.google.gson.Gson;
import com.namvn.shopping.persistence.entity.Privilege;
import com.namvn.shopping.persistence.entity.Role;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security
        .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.google.gson.reflect.TypeToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JwtService {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String SECRET = "ThisIsASecret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "X-XSRF-TOKEN";

    public static String addAuthentication(Authentication authentication) {
        Gson gson = new Gson();
        String roles = gson.toJson(authentication.getAuthorities());
        Claims claims = Jwts.claims().setSubject(authentication.getName());
       // claims.put("userId", user.getUserId());
        claims.put("scopes", roles);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return TOKEN_PREFIX+jwt;
    }

    public static Authentication getAuthentication(String token) {
        if (token != null) {
            //parse the token.
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            Gson gson = new Gson();
            Type type = new TypeToken<Collection<GrantedAuthority>>() {
            }.getType();
            String role = (String) body.get("scopes");
            Collection<GrantedAuthority> roles = gson.fromJson(role, type);

            return body != null ?
                    new UsernamePasswordAuthenticationToken(body.getSubject(), null,roles) :
                    null;
        }
        return null;
    }


    public static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
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


//            }
//        }
//        return null;
//    }
