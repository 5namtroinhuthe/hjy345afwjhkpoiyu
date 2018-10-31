package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.VerificationToken;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.entity.VerificationToken;
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
public class VerificationTokenDaoImpl implements VerificationTokenDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public VerificationToken add(VerificationToken verificationToken) {
        Session session = sessionFactory.getCurrentSession();
        return (VerificationToken) session.save(verificationToken);
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(verificationToken);
    }

    @Override
    public VerificationToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<VerificationToken> criteriaQuery = builder.createQuery(VerificationToken.class);
        Root<VerificationToken> root = criteriaQuery.from(VerificationToken.class);
        criteriaQuery.select(root).where(builder.equal(root.get("token"), token));
        Query<VerificationToken> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public VerificationToken findByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        User user1 = session.get(User.class, user.getUserId());
        return  user1.getVerificationToken();
    }

    @Override
    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
        return null;
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {

    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<VerificationToken> criteriaQuery = builder.createCriteriaDelete(VerificationToken.class);
        Root<VerificationToken> root = criteriaQuery.from(VerificationToken.class);
        criteriaQuery.where(builder.lessThanOrEqualTo(root.get("expiryDate"),now));
        Query<VerificationToken> query = session.createQuery(criteriaQuery);
    }
}
