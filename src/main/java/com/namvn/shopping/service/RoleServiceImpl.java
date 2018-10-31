package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Role;
import com.namvn.shopping.persistence.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao mRoleDao;

    @Override
    public void add(Role role) throws RuntimeException {
        mRoleDao.add(role);
    }

    @Override
    public Role findByName(String name) {
        return mRoleDao.findByName(name);
    }

    @Override
    public void delete(Role role) {
        mRoleDao.delete(role);
    }
}
