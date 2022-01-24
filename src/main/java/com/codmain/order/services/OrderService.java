package com.codmain.order.services;

import com.codmain.order.entity.Order;
import com.codmain.order.entity.OrderLine;
import com.codmain.order.exceptions.GeneralServiceException;
import com.codmain.order.exceptions.NoDataFoundException;
import com.codmain.order.exceptions.ValidateServiceException;
import com.codmain.order.repository.OrderLineRepository;
import com.codmain.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
            order.getLines().forEach(line -> line.setOrder(order) ); //las lineas no tienen estable. a que ordern pertenecen
            if(order.getId() == null){
                //creation
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
