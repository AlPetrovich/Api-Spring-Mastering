package com.codmain.order.dtos;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String regDate; //fecha de registro
    private List<OrderLineDTO> lines; //caract del producto
    private Double total; //sumatoria
}
