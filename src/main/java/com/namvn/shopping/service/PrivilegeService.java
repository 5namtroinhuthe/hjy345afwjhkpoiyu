package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Privilege;

public interface PrivilegeService {
    void add(Privilege privilege) throws RuntimeException;
    Privilege findByName(String name) ;
}
