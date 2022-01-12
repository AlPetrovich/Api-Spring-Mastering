package com.codmain.order.controllers;

import com.codmain.order.converters.ProductConverter;
import com.codmain.order.dtos.ProductDTO;
import com.codmain.order.entity.Product;
import com.codmain.order.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    private ProductConverter converter = new ProductConverter();


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<Product> products =productService.findAll();
        List<ProductDTO> productDTOS= converter.fromEntity(products);
        return new ResponseEntity<List<ProductDTO>>(productDTOS,HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("productId") Long productId){
        Product product = productService.findById(productId);
        ProductDTO productDTO = converter.fromEntity(product);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }


    @PostMapping("/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product){

        Product newProduct = productService.save(converter.fromDTO(product));
        ProductDTO productDTO = converter.fromEntity(newProduct);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO product){
        Product productUpdate = productService.save(converter.fromDTO(product));
        ProductDTO productDTO = converter.fromEntity(productUpdate);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId){
       productService.delete(productId);
       return new ResponseEntity<>(HttpStatus.OK);
    }
}
