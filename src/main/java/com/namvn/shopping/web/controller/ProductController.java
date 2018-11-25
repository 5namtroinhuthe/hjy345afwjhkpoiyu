package com.namvn.shopping.web.controller;

import com.namvn.shopping.pagination.PagingResult;
import com.namvn.shopping.persistence.entity.Product;
import com.namvn.shopping.persistence.model.ProductInfoUser;
import com.namvn.shopping.persistence.model.ProductManager;
import com.namvn.shopping.persistence.model.ProductRequestParam;
import com.namvn.shopping.service.ProductService;
import com.namvn.shopping.util.IO;
import com.namvn.shopping.web.url.UrlAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProductController {
    public static final String PRODUCT_GET = "/product/get";
    public static final String PRODUCT_ADD = "/product/add";
    public static final String PRODUCT_EDIT = "product/edit";
    public static final String PRODUCT_DELETE = "product/delete/{productId}";
    public static final String PRODUCT_GET_ID = "/product/getId/{productId}";
    public static final String MANAGER_ALMOST_OVER_PRODUCT = "/admin/product/get/almost";
    @Autowired
    private ProductService productService;

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(10240000);
//        return multipartResolver;
//    }

    @GetMapping(value = PRODUCT_GET)
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
        ProductRequestParam productParam = new ProductRequestParam(sortType,
                (minPrice != null) ? (Float.valueOf(minPrice)) : 0,
                (maxPrice != null) ? (Float.valueOf(maxPrice)) : 0,
                io.cutWhiteSpaces(color),
                io.cutWhiteSpaces(size),
                io.cutWhiteSpaces(manufacturer),
                io.cutWhiteSpaces(material),
                io.cutWhiteSpaces(madeIn));
        PagingResult<ProductInfoUser> products = productService.getQueryByDetail(page, 8, productParam);
        return new
                ModelAndView("shop", "products", products);
    }

    @GetMapping(value = MANAGER_ALMOST_OVER_PRODUCT)
    public String getAlmostOverProduct(Model model, @RequestParam int page, @RequestParam int quantity, @RequestParam String sortType, @RequestParam String catergory) {
        PagingResult<ProductManager> pagingResult = productService.getQueryAlmostOverProduct(page, 8, quantity, sortType, catergory);
        model.addAttribute("almostOverProducts", pagingResult);
        return null;
    }

    @GetMapping(PRODUCT_GET_ID)
    public ModelAndView getProductById(@PathVariable(value = "productId") String productId) {
        ProductInfoUser product = productService.getProductById(productId);
        return new ModelAndView("single-product-details", "productObj", product);
    }

    @PostMapping(value = PRODUCT_ADD)
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

    @PostMapping(value = PRODUCT_EDIT)
    public String editProduct(@ModelAttribute(value = "editProductObj") Product product) {
        productService.editProduct(product);
        return "redirect:/getAllProducts";
    }

    @DeleteMapping(PRODUCT_DELETE)
    public String deleteProduct(@PathVariable(value = "productId") String productId) {
        productService.deleteProduct(productId);
        return "redirect:/getAllProducts";
    }

}
