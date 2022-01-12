package com.codmain.order.controllers;

import com.codmain.order.entity.Product;
import com.codmain.order.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products =productService.findAll();
        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findById(@PathVariable("productId") Long productId){
        Product product = productService.findById(productId);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody Product product){
        Product newProduct = productService.save(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> update(@RequestBody Product product){
        Product productUpdate = productService.save(product);
        return new ResponseEntity<Product>(productUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId){
       productService.delete(productId);
       return new ResponseEntity<>(HttpStatus.OK);
    }
}
