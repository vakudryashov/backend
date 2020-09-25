package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.ProductTransaction;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.ProductTransactionService;
import com.geekbrains.july.warehouse.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/transactions/products")
@Api("Set of endpoints for CRUD operations for Products Transaction")
public class ProductTransactionsController {
    private ProductTransactionService productTransactionService;
    private ProductsService productsService;

    @Autowired
    public ProductTransactionsController(ProductTransactionService productTransactionService, ProductsService productsService) {
        this.productTransactionService = productTransactionService;
        this.productsService = productsService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all product transactions")
    public List<ProductTransaction> getAllProductTransactions() {
        return productTransactionService.getAllTransactions();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns transactions by product id")
    public ResponseEntity<?> getOneProductTransactions(@PathVariable Long id) {
        return new ResponseEntity<>(productTransactionService.getProductTransactions(id), HttpStatus.OK);
    }

    @GetMapping(value = "/author", produces = "application/json")
    @ApiOperation("Returns transactions by author")
    public ResponseEntity<?> getAuthorTransactions(@RequestParam (name = "author") String author) {
        return new ResponseEntity<>(productTransactionService.getAuthorTransactions(author), HttpStatus.OK);
    }

    @GetMapping(value = "/data", produces = "application/json")
    @ApiOperation("Returns transactions by data")
    public ResponseEntity<?> getDataTransactions(@RequestParam ("data")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data) {
        return new ResponseEntity<>(productTransactionService.getDateTransactions(data), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ProductNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}

