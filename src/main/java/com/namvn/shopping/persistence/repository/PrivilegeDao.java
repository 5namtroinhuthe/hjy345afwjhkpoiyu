package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeDao {
void add(Privilege privilege) throws RuntimeException;
    Privilege findByName(String name) ;


    void delete(Privilege privilege);

}
