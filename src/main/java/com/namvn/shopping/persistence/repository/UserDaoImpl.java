package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.web.form.UserDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public User findByEmail(String email) {
        Session session = sessionFactory.openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(builder.equal(root.get("email"), email));
            Query<User> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }


    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return user;
    }


    @Override
    public void addUser(UserDto user)throws RuntimeException {
        Session session = sessionFactory.getCurrentSession();
        User acc=new User();
        acc.setEmail(user.getEmail());
        acc.setPassword(user.getPassword());
        session.save(acc);
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        User user1 = session.get(User.class, user.getUserId());
        session.delete(user1);
    }
}
