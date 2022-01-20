package com.codmain.order.converters;

import com.codmain.order.dtos.OrderDTO;
import com.codmain.order.dtos.OrderLineDTO;
import com.codmain.order.entity.Order;
import com.codmain.order.entity.OrderLine;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter extends AbstractConverter<Order, OrderDTO>{

    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
    private ProductConverter productConverter = new ProductConverter();


    @Override
    public OrderDTO fromEntity(Order entity) {
        if (entity == null) return null;
        List<OrderLineDTO> lines = fromOrderLineEntity(entity.getLines());

        return OrderDTO.builder()
                .id(entity.getId())
                .lines(lines)
                .regDate(entity.getRegDate().format(dateTimeFormat))
                .total(entity.getTotal())
                .build();
    }

    @Override
    public Order fromDTO(OrderDTO dto) {
        if (dto == null) return null;
        List<OrderLine> lines = fromOrderLineDTO(dto.getLines());

        return Order.builder()
                .id(dto.getId())
                .lines(lines)
                .total(dto.getTotal())
                .build();

    }

    private List<OrderLineDTO> fromOrderLineEntity(List<OrderLine> lines){
        if( lines == null ) return null;

        return lines.stream()
                .map(line -> {
                    return OrderLineDTO.builder()
                            .id(line.getId())
                            .price(line.getPrice())
                            .product(productConverter.fromEntity(line.getProduct()))
                            .quantity(line.getQuantity())
                            .total(line.getTotal())
                            .build();
                })
                        .collect(Collectors.toList());
    }

    private List<OrderLine> fromOrderLineDTO(List<OrderLineDTO> linesDto){
        if (linesDto == null) return null;

        return linesDto.stream()
                .map(lineDto ->{
                    return OrderLine.builder()
                            .id(lineDto.getId())
                            .product(productConverter.fromDTO(lineDto.getProduct()))
                            .price(lineDto.getPrice())
                            .quantity(lineDto.getQuantity())
                            .total(lineDto.getTotal())
                            .build();
                })
                .collect(Collectors.toList());

    }
}
