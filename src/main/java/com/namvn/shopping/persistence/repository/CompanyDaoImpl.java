package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Catergory;
import com.namvn.shopping.persistence.entity.Company;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.util.constant.CatergoryConstant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository

public class CompanyDaoImpl implements CompanyDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void add(Company company) {
        Session session=sessionFactory.getCurrentSession();
        session.save(company);
    }

    @Override
    public void remove(Long companyId) {
        Session session = sessionFactory.getCurrentSession();
        Company product = session.get(Company.class, companyId);
        session.delete(product);
    }

    @Override
    public void edit(Company company) {
        Session session = sessionFactory.getCurrentSession();
        session.update(company);
    }

    @Override
    public Query<Company> queryByPredicates(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery=criteriaBuilder.createQuery(Company.class);
        Root<Company> root=criteriaQuery.from(Company.class);
        criteriaQuery.select(root);
        Query<Company> query=session.createQuery(criteriaQuery);
        return query;
    }

    @Override
    public PagingResult<Company> getCompanies(int page,int limit) {
        Session session=sessionFactory.getCurrentSession();
        return new PagingResult<Company>(queryByPredicates(session),page,limit);
    }

    @Override
    public Company getCompany(Long companyId) {
        // Reading the records from the table
        Session session = sessionFactory.getCurrentSession();
        // select * from Product where isbn=i
        Company company = session.get(Company.class, companyId);

        return company;
    }
}
