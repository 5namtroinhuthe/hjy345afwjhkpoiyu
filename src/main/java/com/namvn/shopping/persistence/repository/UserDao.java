package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.web.form.UserDto;



public interface UserDao {
    //User addUser(UserDto accountDto);
    void addUser(UserDto user) throws RuntimeException;
    void addUser(User user);
    void delete(User user);
    User findByEmail(String email);
    User findById(Long id);


}

