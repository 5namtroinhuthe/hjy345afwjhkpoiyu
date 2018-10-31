package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.Root;

public interface RoleDao {
    void add(Role role)throws RuntimeException;

    Role findByName(String name);


    void delete(Role role);

}
