package com.geekbrains.july.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products_history")
@Data
@NoArgsConstructor
public class DataProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "products_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinTable(name = "products",
            joinColumns = @JoinColumn(name = "products_id"))
    private Product product;

    public DataProductHistory(Long id, Long productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }
}

