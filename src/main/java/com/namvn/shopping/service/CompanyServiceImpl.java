package com.namvn.shopping.service;

import com.namvn.shopping.persistence.repository.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService{
    @Autowired private CompanyDao mCompanyDao;
}
