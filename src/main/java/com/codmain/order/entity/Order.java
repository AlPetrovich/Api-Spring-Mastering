package com.codmain.order.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REG_DATE", nullable = false, updatable = false)
    private LocalDateTime regDate; //fecha de registro

    @OneToMany(mappedBy = "order")
    private List<OrderLine> lines; //caract del producto

    @Column(name = "TOTAL", nullable = false)
    private Double total; //sumatoria

}
