package com.namvn.shopping.config.view;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Class used to config view abc.html...(web)
 * Base on{@code org.springframework.web.WebMvcConfigurer} interface
 * <p>
 * how to add view(.html) to controler
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.namvn.shopping.web"})
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("checkout.html");
        registry.addViewController("contact.html");
        registry.addViewController("index.html");
        registry.addViewController("regular-page.html");
        registry.addViewController("shop.html");
        registry.addViewController("single-blog.html");
        registry.addViewController("single-product-detail.html");
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
//                "/webjars/**",
                "/static/css/**",
                "/static/fonts/**",
                "/static/img/bg-img/**",
                "/static/img/core-img/**",
                "/static/img/product-img/**",
                "/static/js/**",
                "/static/modal_login/css/**",
                "/static/modal_login/fonts/**",
                "/static/modal_login/js/vendor/**",
                "/static/scss/**"
                )
                .addResourceLocations(
//                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/css/",
                        "classpath:/static/fonts/",
                        "classpath:/static/img/bg-img/",
                        "classpath:/static/img/core-img/",
                        "classpath:/static/img/product-img/",
                        "classpath:/static/js/",
                        "classpath:/static/modal_login/css/",
                        "classpath:/static/modal_login/fonts/",
                        "classpath:/static/modal_login/js/vendor/",
                        "classpath:/static/scss/");
    }
}
