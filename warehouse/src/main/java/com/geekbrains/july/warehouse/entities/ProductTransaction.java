package com.geekbrains.july.warehouse.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products_transaction")
@Data
@NoArgsConstructor
@Setter
@Getter
public class ProductTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column(name = "author")
    private String authorName;

    public ProductTransaction(Long id, String type, Long productId, String authorName) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.data = new Date();
        this.authorName = authorName;
    }
}
