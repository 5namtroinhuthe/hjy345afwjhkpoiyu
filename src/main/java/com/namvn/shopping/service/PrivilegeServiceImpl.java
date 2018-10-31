package com.namvn.shopping.service;

import com.namvn.shopping.persistence.entity.Privilege;
import com.namvn.shopping.persistence.repository.PrivilegeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(propagation= Propagation.REQUIRED)
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private PrivilegeDao mPrivilegeDao;
    @Override
    public void add(Privilege privilege) throws RuntimeException {
        mPrivilegeDao.add(privilege);
    }

    @Override
    public Privilege findByName(String name) {
        return mPrivilegeDao.findByName(name);
    }
}
