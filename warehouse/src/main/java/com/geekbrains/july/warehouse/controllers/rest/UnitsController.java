package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Unit;
import com.geekbrains.july.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.july.warehouse.exceptions.CustomException;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/units")
@Api("Set of endpoints for CRUD operations for Posting")
public class UnitsController {
    @Autowired
    private UnitService measureService;

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all units")
    public List<Unit> getAllMeasures() {
        return measureService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new measure")
    public List<Unit> createNomenclature(@RequestBody Unit measure) {
        measureService.saveOrUpdate(measure);
        return measureService.findAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one measure by id")
    public List<Unit> deleteOneProducts(@PathVariable Long id) {
        measureService.deleteById(id);
        return measureService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing measure")
    public List<Unit> modifyCategory(@RequestBody Unit measure) {
        if (measure.getId() == null || !measureService.existsById(measure.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + measure.getId());
        }
        measureService.saveOrUpdate(measure);
        return measureService.findAll();
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
