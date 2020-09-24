package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.ProductTransaction;
import com.geekbrains.july.warehouse.entities.dtos.ProductDto;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.ProductTransactionService;
import com.geekbrains.july.warehouse.services.ProductsService;
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
@RequestMapping("/api/v1/products")
@Api("Set of endpoints for CRUD operations for Products")
public class RestProductsController {
    private ProductsService productsService;
    private UsersService usersService;
    private ProductTransactionService productTransactionService;

    @Autowired
    public RestProductsController(ProductsService productsService, UsersService usersService,
                                  ProductTransactionService productTransactionService) {
        this.productsService = productsService;
        this.usersService = usersService;
        this.productTransactionService = productTransactionService;
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
    public ResponseEntity<?> getOneProducts(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id: " + id);
        }
        return new ResponseEntity<>(productsService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation("Removes all products")
    public String deleteAllProducts() {
        productsService.deleteAll();
        return "OK";
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one product by id")
    public String deleteOneProducts(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            throw new ProductNotFoundException("Product not found, id: " + id);
        }
        int productQuantity = productsService.findById(id).getQuantity();
        productsService.deleteById(id);
        ProductTransaction productTransaction = new ProductTransaction(null, "DELETE", id, productQuantity, usersService.currentUserFullname());
        productTransactionService.saveOrUpdate(productTransaction);
        return "OK";
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation("Creates a new product")
    public Product saveNewProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            product.setId(null);
        }

        product.setCreationData(new Date());
        product.setAuthorName(usersService.currentUserFullname());

        Product savedProduct = productsService.saveOrUpdate(product);
        ProductTransaction productTransaction = new ProductTransaction(null, "CREATE", savedProduct.getId(),
                                                                  savedProduct.getQuantity(), usersService.currentUserFullname());
        productTransactionService.saveOrUpdate(productTransaction);
        return savedProduct;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("Modifies an existing product")
    public ResponseEntity<?> modifyProduct(@RequestBody Product product) {
        if (product.getId() == null || !productsService.existsById(product.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + product.getId());
        }
        if (product.getQuantity() < 0) {
            return new ResponseEntity<>("Product's quantity can not be negative", HttpStatus.BAD_REQUEST);
        }

        ProductTransaction productTransaction = new ProductTransaction(null, "EDIT", product.getId(),
                                                          product.getQuantity(), usersService.currentUserFullname());
        productTransactionService.saveOrUpdate(productTransaction);
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}