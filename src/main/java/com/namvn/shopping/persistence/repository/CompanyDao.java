package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Company;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.ProductParam;
import org.hibernate.Session;
import org.hibernate.query.Query;

public interface CompanyDao {
    void add(Company company);
    void remove(Long companyId);
    void edit(Company company);
    Query<Company> queryByPredicates(Session session);
    PagingResult<Company> getCompanies(int page,int limit);
    Company getCompany(Long companyId);
}
