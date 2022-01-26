package com.codmain.order.services;

import com.codmain.order.entity.Order;
import com.codmain.order.entity.OrderLine;
import com.codmain.order.entity.Product;
import com.codmain.order.entity.User;
import com.codmain.order.exceptions.GeneralServiceException;
import com.codmain.order.exceptions.NoDataFoundException;
import com.codmain.order.exceptions.ValidateServiceException;
import com.codmain.order.repository.OrderLineRepository;
import com.codmain.order.repository.OrderRepository;
import com.codmain.order.repository.ProductRepository;
import com.codmain.order.security.UserPrincipal;
import com.codmain.order.validators.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderLineRepository orderLineRepo;
    @Autowired
    private ProductRepository productRepo;

    public List<Order> findAll(Pageable page){
        try {
            return orderRepo.findAll(page).toList();

        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public Order findById(Long id){
        try{
            return orderRepo.findById(id)
                    .orElseThrow(()-> new NoDataFoundException("La orden no existe"));
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(),e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public void delete(Long id){
        try{
            Order order = orderRepo.findById(id)
                    .orElseThrow(()-> new NoDataFoundException("La orden no existe"));
            orderRepo.delete(order);

        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Order save(Order order){
        try{
            //validations
            OrderValidator.save(order);

            //Necesito saber el usuario que esta creando la orden
            User user = UserPrincipal.getCurrentUser();

            double total = 0;
            for (OrderLine line: order.getLines()){
                Product product =productRepo.findById(line.getProduct().getId())
                        .orElseThrow(()-> new NoDataFoundException("No existe el producto "+ line.getProduct().getId()));
                line.setPrice(product.getPrice());
                line.setTotal(product.getPrice() * line.getQuantity());
                total += line.getTotal();
            }
            order.setTotal(total);

            order.getLines().forEach(line -> line.setOrder(order) ); //las lineas no tienen estable. a que ordern pertenecen
            if(order.getId() == null){
                //creation
                order.setUser(user);
                order.setRegDate(LocalDateTime.now());
                return orderRepo.save(order);
            }
            //update
            Order savedOrder = orderRepo.findById(order.getId())
                    .orElseThrow(()-> new NoDataFoundException("La orden no existe"));
            order.setRegDate(savedOrder.getRegDate()); //con esto seteo la fecha para la actualizacion ya que no la traia
            //si actualizo solo 2 y no agrego el resto(resto desaparece)
            List<OrderLine> deleteLines = savedOrder.getLines();
            deleteLines.removeAll(order.getLines());
            orderLineRepo.deleteAll(deleteLines);

            return orderRepo.save(order);

        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
