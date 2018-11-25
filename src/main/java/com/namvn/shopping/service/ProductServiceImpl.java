package com.namvn.shopping.service;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.model.ProductManager;
import com.namvn.shopping.persistence.repository.ProductDao;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfoUser;
import com.namvn.shopping.persistence.model.ProductRequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao mProductDao;

    @Override
    public PagingResult<ProductInfoUser> getQueryByDetail(int page, int limit, ProductRequestParam productParam) {
        return mProductDao.getQueryByDetail(page, limit, productParam);
    }

    @Override
    public PagingResult<ProductManager> getQueryAlmostOverProduct(int page, int limit, int quantity, String sortType, String catergory) {
        return mProductDao.getListAlmostOverProduct(page, limit, quantity,sortType,catergory);
    }

    @Override
    public ProductInfoUser getProductById(String id) {
        return (ProductInfoUser) mProductDao.getProductById(id);
    }

    @Override
    public void addProduct(Product product) {
        mProductDao.addProduct(product);
        MultipartFile image = product.getProductImage();
        if (image != null && !image.isEmpty()) {
            Path path = Paths
                    .get("D:\\shopping\\src\\main\\resource\\images\\product-details" + product.getProductId() + ".jpg");
            try {
                image.transferTo(new File(path.toString()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public void editProduct(Product product) {
        mProductDao.editProduct(product);
    }

    @Override
    public void deleteProduct(String productId) {
        Path path = Paths.get("D:\\shopping\\src\\main\\resource\\images\\products-details"
                + productId + ".jpg");

        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mProductDao.deleteProduct(productId);
    }
}
