package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.persistence.entity.Catergory;
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

public class CatergoryDaoImpl implements CatergoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCatergoryProduct(Catergory catergory) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(catergory);
    }

//    @Override
//    public void removeCatergoryProduct(String name) {
//        Session session = sessionFactory.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaDelete<Catergory> criteriaDelete=criteriaBuilder.createCriteriaDelete(Catergory.class);
//        Root<Catergory> root=criteriaDelete.from(Catergory.class);
//        criteriaDelete.where(criteriaBuilder.equal(root.get(CatergoryConstant.NAME),name));
//        Query<Catergory> query=session.createQuery(criteriaDelete);
//    }

    @Override
    public void getCatergory(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Catergory> criteriaQuery=criteriaBuilder.createQuery(Catergory.class);
        Root<Catergory> root=criteriaQuery.from(Catergory.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(CatergoryConstant.NAME),name));
        Query<Catergory> query=session.createQuery(criteriaQuery);
        Catergory catergory=query.getSingleResult();
        session.get(Catergory.class,catergory.getCatergoryId());
    }
}
