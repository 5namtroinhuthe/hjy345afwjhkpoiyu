package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Privilege;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository

public class PrivilegeDaoImpl implements PrivilegeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void add(Privilege privilege) throws RuntimeException {
        Session session = sessionFactory.getCurrentSession();
        session.save(privilege);

    }

    @Override
    @Transactional
    public Privilege findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Privilege> criteriaQuery = builder.createQuery(Privilege.class);
        Root<Privilege> root = criteriaQuery.from(Privilege.class);
        criteriaQuery.select(root).where(builder.equal(root.get("name"), name));
        try {
            Query<Privilege> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Privilege privilege) {
        Session session = sessionFactory.getCurrentSession();
        Privilege privilege1 = session.get(Privilege.class, privilege.getPrivilegeId());
        session.delete(privilege1);
    }
}
