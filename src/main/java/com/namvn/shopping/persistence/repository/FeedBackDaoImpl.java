package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.FeedBack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository

public class FeedBackDaoImpl implements FeedBackDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addQuery(FeedBack queries) {
        Session session = sessionFactory.openSession();
        session.save(queries);
        session.close();
    }
}
