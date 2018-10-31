package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.PasswordResetToken;
import com.namvn.shopping.persistence.entity.PasswordResetToken;
import com.namvn.shopping.persistence.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.stream.Stream;
@Repository
@Transactional
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(PasswordResetToken myToken) {
        Session session = sessionFactory.getCurrentSession();
        session.save(myToken);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PasswordResetToken> criteriaQuery = builder.createQuery(PasswordResetToken.class);
        Root<PasswordResetToken> root = criteriaQuery.from(PasswordResetToken.class);
        criteriaQuery.select(root).where(builder.equal(root.get("token"), token));
        Query<PasswordResetToken> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public PasswordResetToken findByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        User user1 = session.get(User.class, user.getUserId());
        return  user1.getPasswordResetToken();
    }

    @Override
    public Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now) {
        return null;
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {

    }

    @Override
    public void delete(PasswordResetToken passwordToken) {
        Session session = sessionFactory.getCurrentSession();
       // PasswordResetToken passwordResetToken = session.get(PasswordResetToken.class, passwordToken.getId());
        session.delete(passwordToken);
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<PasswordResetToken> criteriaQuery = builder.createCriteriaDelete(PasswordResetToken.class);
        Root<PasswordResetToken> root = criteriaQuery.from(PasswordResetToken.class);
        criteriaQuery.where(builder.lessThanOrEqualTo(root.get("expiryDate"),now));
        Query<PasswordResetToken> query = session.createQuery(criteriaQuery);
    }
}
