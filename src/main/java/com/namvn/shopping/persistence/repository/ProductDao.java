package com.namvn.shopping.persistence.repository;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.ProductInfoUser;
import com.namvn.shopping.persistence.model.ProductManager;
import com.namvn.shopping.persistence.model.ProductRequestParam;
import com.namvn.shopping.util.CriteriaSkeleton;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.Map;

public interface ProductDao {
    ProductInfo getProductById(String productId);

    void deleteProduct(String productId);

    void addProduct(Product product);

    void editProduct(Product product);
    Product getProductById1(String productId);
    CriteriaQuery queryPredicatesBetweenPriceOrNomal(CriteriaSkeleton criteriaSkeleton,
                                                     ProductRequestParam productParam,
                                                     int parameter,
                                                     Predicate predicates[]);
    CriteriaQuery<ProductInfoUser> queryBetweenPrice(CriteriaSkeleton criteriaSkeleton, float min, float max);
    CriteriaQuery<ProductInfoUser> queryOrderdPredicatesBySortType(CriteriaSkeleton criteriaSkeleton, String sortType);

    Predicate[] getPredicateArray(Session session, CriteriaSkeleton criteria, Map predicateMap);

    Query<ProductInfoUser> queryByPredicates(Session session, ProductRequestParam productParam);

    PagingResult<ProductInfoUser> getQueryByDetail(int page, int limit, ProductRequestParam productParam);

  Query<ProductManager> findAlmostOverProduct(Session session, int quantity, String sortType, Long catergory);
    PagingResult<ProductManager> getListAlmostOverProduct(int page, int limit,int quantity, String sortType, Long catergory);
}
