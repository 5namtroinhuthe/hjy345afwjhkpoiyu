package com.namvn.shopping.service;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfoUser;
import com.namvn.shopping.persistence.model.ProductManager;
import com.namvn.shopping.persistence.model.ProductRequestParam;

public interface ProductService {
    PagingResult<ProductInfoUser> getQueryByDetail(int page, int limit, ProductRequestParam productParam);
    PagingResult<ProductManager> getQueryAlmostOverProduct(int page, int limit, int quantity, String sortType, String catergory);
    ProductInfoUser getProductById(String id);
    void addProduct(Product product);
    void editProduct(Product product);
    void deleteProduct(String productId);

}
