package com.namvn.shopping.security;

import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.repository.UserDao;
import com.namvn.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * To authenticate account.Order 1
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private UserService userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final User user = userRepository.findUserByEmail(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        // Database Password already encrypted:
        String password = user.getPassword();
        boolean passwordsMatch = passwordEncoder.matches(auth.getCredentials().toString(), password);
        if (!passwordsMatch) {
            throw new BadCredentialsException("Invalid username or password");
        }

        else {
            final Authentication result = super.authenticate(auth);
            return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());

        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
