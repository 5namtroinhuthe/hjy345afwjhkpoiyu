package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Role;

public interface RoleService {
    void add(Role role)throws RuntimeException;

    Role findByName(String name);


    void delete(Role role);
}
