package com.codmain.order.controllers;

import com.codmain.order.converters.ProductConverter;
import com.codmain.order.dtos.ProductDTO;
import com.codmain.order.entity.Product;
import com.codmain.order.services.ProductService;
import com.codmain.order.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter converter;


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(
            @RequestParam(value = "pageNumber",required = false,defaultValue ="0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue ="3") int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber, pageSize);

        List<Product> products =productService.findAll(page);
        List<ProductDTO> productDTOS= converter.fromEntity(products);
        return new WrapperResponse(true,"success",productDTOS)
                .createResponse(HttpStatus.OK);

    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<WrapperResponse<ProductDTO>> findById(@PathVariable("productId") Long productId){
        Product product = productService.findById(productId);
        ProductDTO productDTO = converter.fromEntity(product);
        return new WrapperResponse<ProductDTO>(true,"success",productDTO)
                .createResponse(HttpStatus.OK);
    }


    @PostMapping("/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product){

        Product newProduct = productService.save(converter.fromDTO(product));
        ProductDTO productDTO = converter.fromEntity(newProduct);
        return new WrapperResponse(true,"success",productDTO)
                .createResponse(HttpStatus.CREATED);

    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO product){
        Product productUpdate = productService.save(converter.fromDTO(product));
        ProductDTO productDTO = converter.fromEntity(productUpdate);
        return new WrapperResponse(true,"success",productDTO)
                .createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") Long productId){
       productService.delete(productId);

        return new WrapperResponse(true,"success",null)
                .createResponse(HttpStatus.OK);
    }
}
