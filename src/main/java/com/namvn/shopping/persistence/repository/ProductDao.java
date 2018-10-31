package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.ProductParam;
import com.namvn.shopping.util.CriteriaSkeleton;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public interface ProductDao {
    ProductInfo getProductById(String productId);

    void deleteProduct(String productId);

    void addProduct(Product product);

    void editProduct(Product product);

    CriteriaQuery queryPredicatesBetweenPriceOrNomal(CriteriaSkeleton criteriaSkeleton,
                                                     ProductParam productParam,
                                                     int parameter,
                                                     Predicate predicates[]);
    CriteriaQuery<ProductInfo> queryBetweenPrice(CriteriaSkeleton criteriaSkeleton,float min,float max);
    CriteriaQuery<ProductInfo> queryOrderdPredicatesBySortType(CriteriaSkeleton criteriaSkeleton, String sortType);

    Predicate[] getPredicateArray(Session session, CriteriaSkeleton criteria, Map predicateMap);

    Query<ProductInfo> queryByPredicates(Session session, ProductParam productParam);

    PagingResult<ProductInfo> getQueryByDetail(int page, int limit, ProductParam productParam);

}
