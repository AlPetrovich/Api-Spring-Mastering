package com.codmain.order.services;

import com.codmain.order.entity.Product;
import com.codmain.order.exceptions.GeneralServiceException;
import com.codmain.order.exceptions.NoDataFoundException;
import com.codmain.order.exceptions.ValidateServiceException;
import com.codmain.order.repository.ProductRepository;
import com.codmain.order.validators.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Product findById(Long id){
        try {
            log.debug("findById =>"+ id);
            Product product = productRepo.findById(id)
                    .orElseThrow(()-> new NoDataFoundException("No existe el producto"));
            return product;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }

    @Transactional
    public void delete(Long id){
        try {
            Product product = productRepo.findById(id)
                    .orElseThrow(()-> new NoDataFoundException("No existe el producto"));
            productRepo.delete(product);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }

    public List<Product> findAll(Pageable page){
        try {
            List<Product> products = productRepo.findAll(page).toList();
            return products;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }

    public Product create(Product product){
        Product newProduct = productRepo.save(product);
        return newProduct;
    }


    @Transactional
    public Product save(Product product){

        try {
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

        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }
}
