package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Role;
import com.namvn.shopping.persistence.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Role role) throws RuntimeException {
        Session session = sessionFactory.getCurrentSession();


        session.save(role);

    }



    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);
            criteriaQuery.select(root).where(builder.equal(root.get("name"), name));
            Query<Role> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Role role) {
        Session session = sessionFactory.getCurrentSession();
        Role role1 = session.get(Role.class, role.getRoleId());
        session.delete(role1);
    }
}
