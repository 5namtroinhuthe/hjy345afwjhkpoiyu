package com.namvn.shopping.service;

import com.namvn.shopping.web.form.UserDto;
import com.namvn.shopping.persistence.entity.PasswordResetToken;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.entity.VerificationToken;
import com.namvn.shopping.web.error.UserAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(UserDto user);
void saveRegisteredUser(User user);
    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(long id);

    void changeUserPassword(UserDto user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

//    String generateQRUrl(User user) throws UnsupportedEncodingException;

    //User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

}
