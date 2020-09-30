package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.UserAction;
import com.geekbrains.july.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.july.warehouse.entities.dtos.ProductDto;
import com.geekbrains.july.warehouse.exceptions.CustomException;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.ProductsService;
import com.geekbrains.july.warehouse.services.UserActionService;
import com.geekbrains.july.warehouse.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Api("Set of endpoints for CRUD operations for Product")
public class ProductsController {
    private ProductsService productsService;
    private UsersService usersService;
    private UserActionService userActionService;

    @Autowired
    public ProductsController(ProductsService productsService, UsersService usersService,
                                  UserActionService userActionService) {
        this.productsService = productsService;
        this.usersService = usersService;
        this.userActionService = userActionService;
    }

    @GetMapping("/dto")
    @ApiOperation("Returns list of all products data transfer objects")
    public List<ProductDto> getAllProductsDto() {
        return productsService.getDtoData();
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all products")
    public List<Product> getAllProducts() {
        return productsService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one product by id")
    public Product getOneProducts(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id: " + id);
        }
        return productsService.findById(id);
    }

    @PostMapping
    @ApiOperation("Creates a new product")
    public List<Product> saveNewProduct(@RequestBody Product product) {
        productsService.saveOrUpdate(product);

        UserAction userAction = new UserAction(null, "CREATE", product.getId(), product.getTitle(),
                                               usersService.currentUserFullname());
        userActionService.saveOrUpdate(userAction);
        return productsService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing product")
    public List<Product> modifyProduct(@RequestBody Product product) {
        if (product.getId() == null || !productsService.existsById(product.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + product.getId());
        }
        productsService.saveOrUpdate(product);

        UserAction userAction = new UserAction(null, "EDIT", product.getId(), product.getTitle(),
                usersService.currentUserFullname());
        userActionService.saveOrUpdate(userAction);
        return productsService.findAll();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}