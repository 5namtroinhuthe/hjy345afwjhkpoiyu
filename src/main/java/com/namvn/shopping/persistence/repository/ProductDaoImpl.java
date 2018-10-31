package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;

import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.ProductParam;

import com.namvn.shopping.util.CriteriaSkeleton;
import com.namvn.shopping.util.PreprocessingInput;
import com.namvn.shopping.util.constant.ProductContants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.criteria.*;
import java.util.Map;
import java.util.Set;

import static com.namvn.shopping.util.constant.ProductContants.*;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * todo: func to query flow by 2 condition
     * TH1: Having "predicates" of Product table & finding between "price_min and price_max"
     * TH2: Only having "predicates" of Product table
     *
     * @return CriteriaQuery
     */
    public CriteriaQuery<ProductInfo> queryPredicatesBetweenPriceOrNomal(CriteriaSkeleton criteriaSkeleton,
                                                                         ProductParam productParam,
                                                                         int parameter,
                                                                         Predicate predicates[]) {
        CriteriaBuilder builder = criteriaSkeleton.getCriteriaBuilder();
        CriteriaQuery<ProductInfo> criteriaQuery = criteriaSkeleton.getCriteriaQuery();
        Root<Product> root = criteriaSkeleton.getRoot();
        float min_price = productParam.getMinPrice();
        float max_price = productParam.getMaxPrice();

        if ((min_price + max_price) != 0) {
            if (parameter == 1) {
                criteriaQuery.where(builder.and(predicates[0], builder.between(root.get(ProductContants.PRICE_NEW), min_price, max_price)));
            } else if (parameter == 2) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], builder.between(root.get(ProductContants.PRICE_NEW), min_price, max_price)));
            } else if (parameter == 3) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], predicates[2], builder.between(root.get(ProductContants.PRICE_NEW), min_price, max_price)));
            } else if (parameter == 4) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], predicates[2], predicates[3], builder.between(root.get(ProductContants.PRICE_NEW), min_price, max_price)));
            } else if (parameter == 5) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], predicates[2], predicates[3], predicates[4], builder.between(root.get(ProductContants.PRICE_NEW), min_price, max_price)));
            }
            return criteriaQuery;
        } else {
            if (parameter == 1) {
                criteriaQuery.where(predicates);
            } else if (parameter == 2) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1]));
            } else if (parameter == 3) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], predicates[2]));
            } else if (parameter == 4) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], predicates[2], predicates[3]));
            } else if (parameter == 5) {
                criteriaQuery.where(builder.and(predicates[0], predicates[1], predicates[2], predicates[3], predicates[4]));
            }
            return criteriaQuery;
        }

    }

    /**
     * todo: To find between "price_min and price_max", no have to find "predicates" of Product table
     *
     * @return CriteriaQuery
     */
    public CriteriaQuery<ProductInfo> queryBetweenPrice(CriteriaSkeleton criteriaSkeleton, float min, float max) {
        CriteriaBuilder builder = criteriaSkeleton.getCriteriaBuilder();
        CriteriaQuery<ProductInfo> criteriaQuery = criteriaSkeleton.getCriteriaQuery();
        Root<Product> root = criteriaSkeleton.getRoot();
        criteriaQuery.where(builder.between(root.get(PRICE_NEW), min, max));
        return criteriaQuery;
    }

    @Override
    public Predicate[] getPredicateArray(Session session, CriteriaSkeleton criteria, Map predicateMap) {
        Root<Product> root = criteria.getRoot();
        int mapSize = predicateMap.size();
        int i = 0;
        if (mapSize == 1) {
            Predicate predicates[] = new Predicate[1];
            Set<String> set = predicateMap.keySet();
            for (String key : set) {
                predicates[0] = root.get(key).in(predicateMap.get(key));
            }

            return predicates;

        } else if (mapSize == 2) {
            Set<String> set = predicateMap.keySet();
            Predicate predicates[] = new Predicate[2];
            for (Object key : set) {
                predicates[i] = root.get((String) key).in(predicateMap.get(key));
                i++;
            }
            return predicates;

        } else if (mapSize == 3) {
            Set<String> set = predicateMap.keySet();
            Predicate predicates[] = new Predicate[3];
            for (Object key : set) {
                predicates[i] = root.get((String) key).in(predicateMap.get(key));
                i++;
            }
            return predicates;
        } else if (mapSize == 4) {
            Set<String> set = predicateMap.keySet();
            Predicate predicates[] = new Predicate[4];
            for (Object key : set) {
                predicates[i] = root.get((String) key).in(predicateMap.get(key));
                i++;
            }
            return predicates;
        } else if (mapSize == 5) {
            Set<String> set = predicateMap.keySet();
            Predicate predicates[] = new Predicate[5];
            for (Object key : set) {
                predicates[i] = root.get((String) key).in(predicateMap.get(key));
                i++;
            }
            return predicates;
        }
        return null;
    }

    public CriteriaQuery<ProductInfo> queryOrderdPredicatesBySortType(CriteriaSkeleton criteriaSkeleton,
                                                                      String sortType) {
        CriteriaBuilder builder = criteriaSkeleton.getCriteriaBuilder();
        CriteriaQuery<ProductInfo> criteriaQuery = criteriaSkeleton.getCriteriaQuery();
        Root<Product> root = criteriaSkeleton.getRoot();

        if (sortType.equals(PRICE_DATE)) {
            return criteriaQuery.orderBy(builder.desc(root.get(ProductContants.PRICE_DATE)));
        } else if (sortType.equals(ProductContants.PRICE_ASC)) {
            return criteriaQuery.orderBy(builder.asc(root.get(ProductContants.PRICE_NEW)));
        } else if (sortType.equals(ProductContants.PRICE_DESC)) {
            return criteriaQuery.orderBy(builder.desc(root.get(ProductContants.PRICE_NEW)));
        }
        return null;
    }


    @Override
    public Query<ProductInfo> queryByPredicates(Session session, ProductParam productParam) {
        String sortType = productParam.getSortType();
        float min_price = productParam.getMinPrice();
        float max_price = productParam.getMaxPrice();
        Map predicateMap = new PreprocessingInput().filterNumberPredicateProduct(productParam);
        Map paramMap = new PreprocessingInput().filterLeftRequestParam(min_price, max_price, sortType);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductInfo> criteriaQuery = builder.createQuery(ProductInfo.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(root.get(ProductContants.NAME),
                root.get(ProductContants.IMAGE),
                root.get(ProductContants.PRICES),
                root.get(ProductContants.PRICE_NEW),
                root.get(ProductContants.MANUFACTURER));
        Query<ProductInfo> query = null;
        int mapSize = predicateMap.size();
        int paramSize = paramMap.size();
        if (mapSize == 0 && paramSize == 0) {
            query = session.createQuery(criteriaQuery);
            return query;
        } else if (mapSize == 0 && paramSize > 0) {
            CriteriaSkeleton criteriaSkeleton = new CriteriaSkeleton(builder, criteriaQuery, root);
            if (paramMap.containsKey(KEY_SORT_TYPE) && paramMap.containsKey(KEY_MAX)) {
                criteriaSkeleton.setCriteriaQuery(queryBetweenPrice(criteriaSkeleton, min_price, max_price));
                query = session.createQuery(queryOrderdPredicatesBySortType(criteriaSkeleton, sortType));
            } else if (paramMap.containsKey(KEY_SORT_TYPE)) {
                query = session.createQuery(queryOrderdPredicatesBySortType(criteriaSkeleton, sortType));

            } else if (paramMap.containsKey(KEY_MAX)) {
                query = session.createQuery(queryBetweenPrice(criteriaSkeleton, min_price, max_price));
            }
            return query;
        }
        /**
         * Tim kiem thong thuong ko co "tim khoang, sap xep"
         * */
        else if (mapSize > 0 && paramSize == 0) {
            CriteriaSkeleton criteriaSkeleton = new CriteriaSkeleton(builder, criteriaQuery, root);
            Predicate[] predicates = getPredicateArray(session, new CriteriaSkeleton(root), predicateMap);
            query = session.createQuery(queryPredicatesBetweenPriceOrNomal(criteriaSkeleton, productParam, mapSize, predicates));
            return query;
        } else if (mapSize > 0 && paramSize > 0) {
            CriteriaSkeleton criteriaSkeleton = new CriteriaSkeleton(builder, criteriaQuery, root);
            Predicate[] predicates = getPredicateArray(session, new CriteriaSkeleton(root), predicateMap);
            /**
             * TH1: vua co tim kiem gia trong khoang
             * TH2: co ca sap xep order by nua
             * */
            if (paramMap.containsKey(KEY_SORT_TYPE) && paramMap.containsKey(KEY_MAX)) {
                /*
                 * todo: lay ra doi tuong criteriaquery
                 * TH1: tra ve theo 1,2,3,4,5 thuoc tinh thong thuong
                 * TH2: tra ve theo 1,2,3,4,5 thuoc tinh nhung co tim kiem lay trong khoang gia
                 * @Mong doi TH1
                 * Can phai set lai CriteriaQuery moi trong class CriteriaSkeleton
                 * */
                criteriaSkeleton.setCriteriaQuery(queryPredicatesBetweenPriceOrNomal(criteriaSkeleton, productParam, mapSize, predicates));
                query = session.createQuery(queryOrderdPredicatesBySortType(criteriaSkeleton, sortType));
            } else if (paramMap.containsKey(KEY_SORT_TYPE)) {
                query = session.createQuery(queryOrderdPredicatesBySortType(criteriaSkeleton, sortType));

            } else if (paramMap.containsKey(KEY_MAX)) {
                query = session.createQuery(queryPredicatesBetweenPriceOrNomal(criteriaSkeleton, productParam, mapSize, predicates));
            }
            return query;
        }


        return null;
    }


    @Override
    public PagingResult<ProductInfo> getQueryByDetail(int page, int limit, ProductParam productParam) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductInfo> productQuery = queryByPredicates(session, productParam);
        return new PagingResult<ProductInfo>(productQuery, page, limit);
    }

    @Override
    public ProductInfo getProductById(String productId) {
        // Reading the records from the table
        Session session = sessionFactory.getCurrentSession();
        // select * from Product where isbn=i
        Product product = session.get(Product.class, productId);

        return new ProductInfo(product.getProductId(), product.getName(), product.getPriceNew(), product.getPriceNew());

    }

    @Override
    public void deleteProduct(String productId) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, productId);
        session.delete(product);
        // close the session
    }

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);

    }

    @Override
    public void editProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }
}
