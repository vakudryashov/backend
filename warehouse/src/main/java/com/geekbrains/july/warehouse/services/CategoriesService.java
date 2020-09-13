package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Category;
import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    private CategoriesRepository categoriesRepository;

    @Autowired
    public void setCategoriesRepository(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoriesRepository.findAllById(ids);
    }

    public Category saveOrUpdate(Category category) {
        return categoriesRepository.save(category);
    }

    public Category findById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found category with id = " + id));
    }

    public void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoriesRepository.existsById(id);
    }
}
