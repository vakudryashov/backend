package com.geekbrains.july.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "postings")
@Data
@NoArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "posting_date")
    private Date postingDate;

    @ManyToOne
    @JoinTable(name = "postings_products",
            joinColumns = @JoinColumn(name = "posting_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product product;

    @Column(name="quantity")
    private double quantity;

    @ManyToOne
    @JoinTable(name = "postings_users",
            joinColumns = @JoinColumn(name = "posting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
}
