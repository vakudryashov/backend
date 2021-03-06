package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Category;
import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.dtos.ProductDto;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.CategoriesService;
import com.geekbrains.july.warehouse.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
@Api("Set of endpoints for CRUD operations for Products")
public class RestCategoriesController {
    private CategoriesService categoriesService;

    @Autowired
    public RestCategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
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

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one category by id")
    public String deleteOneProducts(@PathVariable Long id) {
        categoriesService.deleteById(id);
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
        return categoriesService.saveOrUpdate(category);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("Modifies an existing category")
    public ResponseEntity<?> modifyCategory(@RequestBody Category category) {
        if (category.getId() == null || !categoriesService.existsById(category.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + category.getId());
        }
        return new ResponseEntity<>(categoriesService.saveOrUpdate(category), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
