package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.web.form.UserDto;



public interface UserDao {
    //User addUser(UserDto accountDto);
    void addUser(User user) throws RuntimeException;
    void delete(User user);
    User findByEmail(String email);
    User findById(Long id);


}

