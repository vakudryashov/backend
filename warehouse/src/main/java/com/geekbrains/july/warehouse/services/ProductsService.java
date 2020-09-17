package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Category;
import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.Role;
import com.geekbrains.july.warehouse.entities.User;
import com.geekbrains.july.warehouse.entities.dtos.ProductDto;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.repositories.CategoriesRepository;
import com.geekbrains.july.warehouse.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductsService {
    private ProductsRepository productsRepository;
    private CategoriesRepository categoriesRepository;

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Autowired
    public void setCategoriesRepository(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Product saveOrUpdate(Product product) {
        return productsRepository.save(product);
    }

    public Product findById(Long id) {
        return productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found product with id = " + id));
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public Page<Product> findAll(Specification<Product> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return productsRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public void deleteAll() {
        productsRepository.deleteAll();
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productsRepository.existsById(id);
    }

    public List<ProductDto> getDtoData() {
        return productsRepository.findAllBy();
    }

    public void setCategories(Product product, List<Long> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            categories.add(categoriesRepository.findById(categoryId).get());
        }
        product.setCategories(categories);
    }
}
