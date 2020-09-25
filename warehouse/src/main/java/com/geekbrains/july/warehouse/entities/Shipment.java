package com.geekbrains.july.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shipment_date")
    private Date shipmentDate;

    @ManyToOne
    @JoinTable(name = "shipments_products",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product product;

    @ManyToOne
    @JoinTable(name = "shipments_contractors",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "contractor_id"))
    private Contractor contractor;

    @Column(name="quantity")
    private double quantity;

    @ManyToOne
    @JoinTable(name = "shipments_users",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
}
