package com.namvn.shopping.service;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.ProductParam;

public interface ProductService {
    PagingResult<ProductInfo> getQueryByDetail(int page, int limit, ProductParam productParam);
    ProductInfo getProductById(String id);
    void addProduct(Product product);
    void editProduct(Product product);
    void deleteProduct(String productId);
}
