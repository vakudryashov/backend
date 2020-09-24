package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Category;
import com.geekbrains.july.warehouse.entities.CategoryTransaction;
import com.geekbrains.july.warehouse.services.CategoryTransactionService;
import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.ProductTransaction;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.CategoriesService;
import com.geekbrains.july.warehouse.services.ProductTransactionService;
import com.geekbrains.july.warehouse.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
@Api("Set of endpoints for CRUD operations for Categories")
public class RestCategoriesController {
    private CategoriesService categoriesService;
    private UsersService usersService;
    private CategoryTransactionService categoryTransactionService;

    @Autowired
    public RestCategoriesController(CategoriesService categoriesService, UsersService usersService,
                                    CategoryTransactionService categoryTransactionService) {
        this.categoriesService = categoriesService;
        this.usersService = usersService;
        this.categoryTransactionService = categoryTransactionService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all categories")
    public List<Category> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one category by id")
    public ResponseEntity<?> getOneCategory(@PathVariable Long id) {
        if (!categoriesService.existsById(id)) {
            throw new ProductNotFoundException("Category not found, id: " + id);
        }
        return new ResponseEntity<>(categoriesService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/products", produces = "application/json")
    @ApiOperation("Returns products from category by id")
    public ResponseEntity<?> getProductsFromCategory(@PathVariable Long id) {
        if (!categoriesService.existsById(id)) {
            throw new ProductNotFoundException("Category not found, id: " + id);
        }
        Category category = categoriesService.findById(id);
        List<Product> products = category.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one category by id")
    public String deleteOneProducts(@PathVariable Long id) {
        categoriesService.deleteById(id);
        CategoryTransaction categoryTransaction = new CategoryTransaction(null, "DELETE", id, usersService.currentUserFullname());
        categoryTransactionService.saveOrUpdate(categoryTransaction);
        return "OK";
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation("Creates a new category")
    public Category saveNewCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            category.setId(null);
        }

        category.setCreationData(new Date());
        category.setAuthorName(usersService.currentUserFullname());

        Category savedCategory = categoriesService.saveOrUpdate(category);
        CategoryTransaction categoryTransaction = new CategoryTransaction(null, "CREATE", savedCategory.getId(),
                                                                           usersService.currentUserFullname());
        categoryTransactionService.saveOrUpdate(categoryTransaction);
        return savedCategory;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("Modifies an existing category")
    public ResponseEntity<?> modifyCategory(@RequestBody Category category) {
        if (category.getId() == null || !categoriesService.existsById(category.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + category.getId());
        }

        CategoryTransaction categoryTransaction = new CategoryTransaction(null, "EDIT", category.getId(),
                                                                           usersService.currentUserFullname());
        categoryTransactionService.saveOrUpdate(categoryTransaction);
        return new ResponseEntity<>(categoriesService.saveOrUpdate(category), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
