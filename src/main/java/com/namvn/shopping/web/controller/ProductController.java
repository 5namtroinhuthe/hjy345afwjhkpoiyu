package com.namvn.shopping.web.controller;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfo;
import com.namvn.shopping.persistence.model.ProductParam;
import com.namvn.shopping.service.ProductService;
import com.namvn.shopping.util.IO;
import com.namvn.shopping.web.url.UrlAddress;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(10240000);
//        return multipartResolver;
//    }

    @RequestMapping(value = UrlAddress.PRODUCT_GET, method = RequestMethod.GET)
    public ModelAndView getAllProducts(@RequestParam int page,
                                       @RequestParam(value = "sortType", required = false) String sortType,
                                       @RequestParam(value = "color", required = false) String color,
                                       @RequestParam(value = "size", required = false) String size,
                                       @RequestParam(value = "manufacturer", required = false) String manufacturer,
                                       @RequestParam(value = "material", required = false) String material,
                                       @RequestParam(value = "madeIn", required = false) String madeIn,
                                       @RequestParam(value = "minPrice", required = false) String minPrice,
                                       @RequestParam(value = "maxPrice", required = false) String maxPrice) {
        IO io = new IO();
        ProductParam productParam = new ProductParam(sortType,
                (minPrice != null) ? (Float.valueOf(minPrice)) : 0,
                (maxPrice != null) ? (Float.valueOf(maxPrice)) : 0,
                io.cutWhiteSpaces(color),
                io.cutWhiteSpaces(size),
                io.cutWhiteSpaces(manufacturer),
                io.cutWhiteSpaces(material),
                io.cutWhiteSpaces(madeIn));
        PagingResult<ProductInfo> products = productService.getQueryByDetail(page, 8, productParam);
        return new
                ModelAndView("shop", "products", products);
    }

    @RequestMapping(UrlAddress.PRODUCT_GET_ID)
    public ModelAndView getProductById(@PathVariable(value = "productId") String productId) {
        ProductInfo product = productService.getProductById(productId);
        return new ModelAndView("productPage", "productObj", product);
    }

    @RequestMapping(value = UrlAddress.PRODUCT_ADD, method = RequestMethod.POST)
    public String addProduct(@Valid @ModelAttribute(value = "productFormObj") Product product, BindingResult result) {
        // Binding Result is used if the form that has any error then it will
        // redirect to the same page without performing any functions
        if (result.hasErrors())
            return "addProduct";
        productService.addProduct(product);
        MultipartFile image = product.getProductImage();
        if (image != null && !image.isEmpty()) {
            Path path = Paths
                    .get("D:\\ShoppingCart\\src\\main\\resource\\img\\product-img"
                            + product.getProductId() + ".jpg");

            try {
                image.transferTo(new File(path.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/getAllProducts";
    }

    @RequestMapping(value = UrlAddress.PRODUCT_EDIT, method = RequestMethod.POST)
    public String editProduct(@ModelAttribute(value = "editProductObj") Product product) {
        productService.editProduct(product);
        return "redirect:/getAllProducts";
    }

    @RequestMapping(UrlAddress.PRODUCT_DELETE)
    public String deleteProduct(@PathVariable(value = "productId") String productId) {
        productService.deleteProduct(productId);
        // http://localhost:8080/shoppingCart/getAllProducts
        return "redirect:/getAllProducts";
    }

}
