package com.codmain.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_LINES")
public class OrderLine {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="FK_ORDER", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name= "FK_PRODUCT", nullable = false)
    private Product product;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "QUANTITY", nullable = false)
    private Double quantity;

    @Column(name = "TOTAL", nullable = false)
    private Double total;

}
