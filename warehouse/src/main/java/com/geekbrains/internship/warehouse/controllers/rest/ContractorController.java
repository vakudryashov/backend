package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.Contractor;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.internship.warehouse.services.ContractorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contractors")
@Api("Set of endpoints for CRUD operations for Contractor")
public class ContractorController {
    @Autowired
    private ContractorService contractorService;

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all contractors")
    public List<Contractor> getAllContractors() {
        return contractorService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new contractor")
    public List<Contractor> createContractor(@RequestBody Contractor contractor) {
        contractorService.saveOrUpdate(contractor);
        return contractorService.findAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one contractor by id")
    public List<Contractor> deleteOneProducts(@PathVariable Long id) {
        contractorService.deleteById(id);
        return contractorService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing contractor")
    public List<Contractor> modifyCategory(@RequestBody Contractor contractor) {
        if (contractor.getId() == null || !contractorService.existsById(contractor.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + contractor.getId());
        }
        contractorService.saveOrUpdate(contractor);
        return contractorService.findAll();
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
