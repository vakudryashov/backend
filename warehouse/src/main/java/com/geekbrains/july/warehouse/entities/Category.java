package com.geekbrains.july.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_data")
    @Temporal(TemporalType.DATE)
    private Date creationData;

    @Column(name = "author")
    private String authorName;

    @ManyToMany
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonBackReference
    private List<Product> products;

    public Category(Long id, String title, String description, String authorName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationData = new Date();
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return title;
    }
}
