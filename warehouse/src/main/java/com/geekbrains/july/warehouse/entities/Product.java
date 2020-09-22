package com.geekbrains.july.warehouse.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "units")
    private String units;

    @Column(name = "creation_data")
    private Date creationData;

    @Column(name = "image")
    private String imagePath;

    @Column(name = "author")
    private String authorName;

    @ManyToMany
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Product(Long id, String title, String description, int quantity, String units, String imagePath, String authorName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.units = units;
        this.creationData = new Date();
        this.imagePath = imagePath;
        this.authorName = authorName;
    }
}
