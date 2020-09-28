package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.*;
import com.geekbrains.july.warehouse.entities.dtos.ProductDto;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductsService {
    private ProductsRepository productsRepository;
    private CategoriesService categoriesService;
    private UnitService unitService;
    private UsersService usersService;
    private UserActionService userActionService;

    @Autowired
    public void setProductsRepository(
            ProductsRepository productsRepository,
            CategoriesService categoriesService,
            UnitService unitService,
            UsersService usersService,
            UserActionService userActionService) {
        this.productsRepository = productsRepository;
        this.categoriesService = categoriesService;
        this.unitService = unitService;
        this.usersService = usersService;
        this.userActionService = userActionService;
    }

    public Product saveOrUpdate(Product product) {
        Unit unit = product.getUnit();
        product.setUnit(unitService.saveOrUpdate(product.getUnit()));
        boolean isEditable = productsRepository.existsById(product.getId());
        Product savedProduct = productsRepository.save(product);

//        UserAction userAction = new UserAction(
//                isEditable ? "Edit" : "Create",
//                new Date(),
//                usersService.findLoggedInUser().get(),
//                savedProduct
//        );
//        userActionService.saveOrUpdate(userAction);
        return savedProduct;
    }

    public Product findById(Long id) {
        return productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found product with id = " + id));
    }

    public Product findByTitle(String title) {
        return productsRepository.findByTitle(title);
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

    public boolean existsById(Long id) {
        return productsRepository.existsById(id);
    }

    public List<ProductDto> getDtoData() {
        return productsRepository.findAllBy();
    }

    public void setCategories(Product product, List<Long> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            categories.add(categoriesService.findById(categoryId));
        }
        product.setCategories(categories);
    }
}
