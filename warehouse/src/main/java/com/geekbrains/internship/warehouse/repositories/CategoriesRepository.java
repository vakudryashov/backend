package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title);
}