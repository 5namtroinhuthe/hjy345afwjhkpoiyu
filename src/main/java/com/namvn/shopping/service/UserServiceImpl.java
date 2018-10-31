package com.namvn.shopping.service;

import com.namvn.shopping.persistence.repository.RoleDao;
import com.namvn.shopping.web.form.UserDto;
import com.namvn.shopping.persistence.entity.PasswordResetToken;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.entity.VerificationToken;
import com.namvn.shopping.persistence.repository.PasswordResetTokenDao;
import com.namvn.shopping.persistence.repository.UserDao;
import com.namvn.shopping.persistence.repository.VerificationTokenDao;
import com.namvn.shopping.web.error.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao repository;
    @Autowired
    private RoleDao roleRepository;
    @Autowired
    private VerificationTokenDao tokenRepository;

    @Autowired
    private PasswordResetTokenDao passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private SessionRegistry sessionRegistry;

    public static  String TOKEN_INVALID = "invalidToken";
    public static  String TOKEN_EXPIRED = "expired";
    public static  String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";

    // API

    @Override
    public void registerNewUserAccount(UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
        }
        User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        // user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
         repository.addUser(user);
    }

    @Override
    public User getUser( String verificationToken) {
         VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken( String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser( User user) {
        repository.addUser(user);
    }

    @Override
    public void deleteUser( User user) {
         VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

         PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        repository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser( User user,  String token) {
         VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.add(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken( String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.add(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser( User user,  String token) {
         PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.add(myToken);
    }

    @Override
    public User findUserByEmail( String email) {
        return repository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken( String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken( String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID( long id) {
        return repository.findById(id);
    }

    @Override
    public void changeUserPassword( User user,  String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.addUser(user);
    }

    @Override
    public boolean checkIfValidOldPassword( User user,  String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
         VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

         User user = verificationToken.getUser();
         Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        repository.addUser(user);
        return TOKEN_VALID;
    }

//    @Override
//    public String generateQRUrl(User user) throws UnsupportedEncodingException {
//        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
//    }

//    @Override
//    public User updateUser2FA(boolean use2FA) {
//         Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) curAuth.getPrincipal();
//        //currentUser.setUsing2FA(use2FA);
//        currentUser = repository.addUser(currentUser);
//         Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        return currentUser;
//    }

    private boolean emailExist( String email) {
        return repository.findByEmail(email) != null;
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals().stream().filter((u) -> !sessionRegistry.getAllSessions(u, false).isEmpty()).map(Object::toString).collect(Collectors.toList());
    }

}