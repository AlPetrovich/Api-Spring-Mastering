package com.codmain.order.services;

import com.codmain.order.entity.Product;
import com.codmain.order.exceptions.NoDataFoundException;
import com.codmain.order.repository.ProductRepository;
import com.codmain.order.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Product findById(Long id){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new NoDataFoundException("No existe el producto"));
        return product;
    }

    @Transactional
    public void delete(Long id){
        Product product = productRepo.findById(id)
                .orElseThrow(()-> new NoDataFoundException("No existe el producto"));
        productRepo.delete(product);
    }

    public List<Product> findAll(Pageable page){
        List<Product> products = productRepo.findAll(page).toList();
        return products;
    }

    public Product create(Product product){
        Product newProduct = productRepo.save(product);
        return newProduct;
    }


    @Transactional
    public Product save(Product product){
        ProductValidator.save(product);

        if (product.getId() == null){
            Product newProduct = productRepo.save(product);
            return newProduct;
        }
        Product exitProduct = productRepo.findById(product.getId())
                .orElseThrow(()-> new NoDataFoundException("No existe el producto"));
        exitProduct.setName(product.getName());
        exitProduct.setPrice(product.getPrice());
        productRepo.save(exitProduct);
        return exitProduct;
    }
}
