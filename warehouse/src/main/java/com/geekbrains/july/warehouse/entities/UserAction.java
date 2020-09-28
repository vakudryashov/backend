package com.geekbrains.july.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_actions")
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;
    private Date actionDate;

    @ManyToOne
    @JoinTable(name = "link__actions_users",
            joinColumns = @JoinColumn(name = "action_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @ManyToOne
    @JoinTable(name = "link__actions_products",
            joinColumns = @JoinColumn(name = "action_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product product;

    public UserAction(String title, Date actionDate, User user, Product product) {
        this.title = title;
        this.actionDate = actionDate;
        this.user = user;
        this.product = product;
    }
}
