package com.namvn.shopping.security;

import com.namvn.shopping.service.JwtService;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userRepository;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        //Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //grantedAuthorities.add(new SimpleGrantedAuthority("LOGGED_USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),user.isEnabled(),
                true,true,true, JwtService.getAuthorities(user.getRoles()));


    }




}
