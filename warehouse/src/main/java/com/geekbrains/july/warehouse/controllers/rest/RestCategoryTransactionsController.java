package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.CategoryTransaction;
import com.geekbrains.july.warehouse.services.CategoryTransactionService;
import com.geekbrains.july.warehouse.services.CategoriesService;
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
@RequestMapping("/api/v1/transactions/categories")
@Api("Set of endpoints for CRUD operations for Categories Transaction")
public class RestCategoryTransactionsController {
    private CategoryTransactionService categoryTransactionService;
    private CategoriesService categoriesService;

    @Autowired
    public RestCategoryTransactionsController(CategoryTransactionService categoryTransactionService, CategoriesService categoriesService) {
        this.categoryTransactionService = categoryTransactionService;
        this.categoriesService = categoriesService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all category transactions")
    public List<CategoryTransaction> getAllCategoryTransactions() {
        return categoryTransactionService.getAllTransactions();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns transactions by category id")
    public ResponseEntity<?> getOneCategoryTransactions(@PathVariable Long id) {
        return new ResponseEntity<>(categoryTransactionService.getCategoryTransactions(id), HttpStatus.OK);
    }

    @GetMapping(value = "/author", produces = "application/json")
    @ApiOperation("Returns category transactions by author")
    public ResponseEntity<?> getAuthorTransactions(@RequestParam (name = "author") String author) {
        return new ResponseEntity<>(categoryTransactionService.getAuthorTransactions(author), HttpStatus.OK);
    }

    @GetMapping(value = "/data", produces = "application/json")
    @ApiOperation("Returns category transactions by data")
    public ResponseEntity<?> getDataTransactions(@RequestParam ("data")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data) {
        return new ResponseEntity<>(categoryTransactionService.getDateTransactions(data), HttpStatus.OK);
    }
}


