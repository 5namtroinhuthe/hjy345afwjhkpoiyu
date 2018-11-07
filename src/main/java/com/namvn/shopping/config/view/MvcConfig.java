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

                "/static/fashe-colorlib/css/**",
                "/static/fashe-colorlib/fonts/**",
                "/static/fashe-colorlib/images/**",
                "/static/fashe-colorlib/includes/**",
                "/static/fashe-colorlib/js/**",
                "/static/fashe-colorlib/vendor/**",

                "/static/fonts/**",
                "/static/img/bg-img/**",
                "/static/img/core-img/**",
                "/static/img/product-img/**",
                "/static/js/**",
                "/static/modal_login/css/**",
                "/static/modal_login/fonts/**",
                "/static/modal_login/js/vendor/**",

                "/static/register/.sass-cache/2af427b2894d1e78c5806e231aa35d00838a84e0/**",
                "/static/register/.sass-cache/632f5d1dd893d2e8764de51b8ca4120e25043113/**",
                "/static/register/.sass-cache/7643c8aa2e335ff5e104bc47d8dd7f1a0693bb39/**",
                "/static/register/.sass-cache/21880c0ffe15753a53f8402e9f64e1077e071510/**",
                "/static/register/.sass-cache/643489b018602f15119d8f44aeb7fc79468873a9/**",
                "/static/register/.sass-cache/f49285f1317a1cafa93ab4f5de346f92bbc40d58/**",
                "/static/register/css/**",
                "/static/register/fonts/material-icon/css/**",
                "/static/register/fonts/material-icon/fonts/**",
                "/static/register/fonts/montserrat/**",
                "/static/register/images/**",
                "/static/register/js/**",
                "/static/register/scss/common/**",
                "/static/register/scss/layouts/**",
                "/static/register/scss/**",
                "/static/register/vendor/jquery/**",
                "/static/scss/**"
                )
                .addResourceLocations(
//                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/css/",

                        "classpath:/static/fashe-colorlib/css/",
                        "classpath:/static/fashe-colorlib/fonts/",
                        "classpath:/static/fashe-colorlib/images/",
                        "classpath:/static/fashe-colorlib/includes/",
                        "classpath:/static/fashe-colorlib/js/",
                        "classpath:/static/fashe-colorlib/vendor/",

                        "classpath:/static/fonts/",
                        "classpath:/static/img/bg-img/",
                        "classpath:/static/img/core-img/",
                        "classpath:/static/img/product-img/",
                        "classpath:/static/js/",
                        "classpath:/static/modal_login/css/",
                        "classpath:/static/modal_login/fonts/",
                        "classpath:/static/modal_login/js/vendor/",

                        "classpath:/static/register/.sass-cache/2af427b2894d1e78c5806e231aa35d00838a84e0/",
                        "classpath:/static/register/.sass-cache/632f5d1dd893d2e8764de51b8ca4120e25043113/",
                        "classpath:/static/register/.sass-cache/7643c8aa2e335ff5e104bc47d8dd7f1a0693bb39/",
                        "classpath:/static/register/.sass-cache/21880c0ffe15753a53f8402e9f64e1077e071510/",
                        "classpath:/static/register/.sass-cache/643489b018602f15119d8f44aeb7fc79468873a9/",
                        "classpath:/static/register/.sass-cache/f49285f1317a1cafa93ab4f5de346f92bbc40d58/",
                        "classpath:/static/register/css/",
                        "classpath:/static/register/fonts/material-icon/css/",
                        "classpath:/static/register/fonts/material-icon/fonts/",
                        "classpath:/static/register/fonts/montserrat/",
                        "classpath:/static/register/images/",
                        "classpath:/static/register/js/",
                        "classpath:/static/register/scss/common/",
                        "classpath:/static/register/scss/layouts/",
                        "classpath:/static/register/scss/",
                        "classpath:/static/register/vendor/jquery/",

                        "classpath:/static/scss/");
    }
}
