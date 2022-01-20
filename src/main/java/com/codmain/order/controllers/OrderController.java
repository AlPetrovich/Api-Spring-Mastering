package com.codmain.order.controllers;

import com.codmain.order.converters.OrderConverter;
import com.codmain.order.dtos.OrderDTO;
import com.codmain.order.entity.Order;
import com.codmain.order.utils.WrapperResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderConverter orderConverter= new OrderConverter();

    @GetMapping("/orders")
    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
            ){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Order> orders = null; //orderService.finAll(page);

        OrderConverter converter = new OrderConverter();
        return new WrapperResponse(true,"success", converter.fromEntity(orders))
                .createResponse();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable(name = "id") Long id){
        Order order = null; //orderService.findById(id);

        return new WrapperResponse(true,"success",orderConverter.fromEntity(order))
                .createResponse();
    }

    @PostMapping("/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> create(@RequestBody OrderDTO orderDTO){

        Order newOrder = null; // orderService.save(converter.fromDTO(order))
        return new WrapperResponse(true, "success", orderConverter.fromEntity(newOrder))
                .createResponse();
    }

    @PutMapping("/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> update(@RequestBody OrderDTO orderDTO){
        Order newOrder = null; // orderService.update()

        return  new WrapperResponse(true, "success", orderConverter.fromEntity(newOrder))
                .createResponse();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        //orderService.delete(id);
        return new WrapperResponse(true, "success", null).createResponse();
    }
}
