package com.codmain.order.controllers;

import com.codmain.order.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
public class HelloWorldController {



    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }

    @GetMapping("/products")
    public Product findProduct(){
        log.info("findProduct =>");
        Product product = Product.builder()
                .id(1L)
                .name("Producto one")
                .category("Category one")
                .build();
        return product;
    }
}
