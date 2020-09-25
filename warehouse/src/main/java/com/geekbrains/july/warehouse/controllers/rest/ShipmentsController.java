package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Shipment;
import com.geekbrains.july.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.july.warehouse.exceptions.CustomException;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.ShipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@Api("Set of endpoints for CRUD operations for Shipment")
public class ShipmentsController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all shipments")
    public List<Shipment> getAllShipments() {
        return shipmentService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Creates a new shipment")
    public List<Shipment> createNomenclature(@RequestBody Shipment shipment) {
        shipmentService.saveOrUpdate(shipment);
        return shipmentService.findAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one shipment by id")
    public List<Shipment> deleteOneShipments(@PathVariable Long id) {
        shipmentService.deleteById(id);
        return shipmentService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing shipment")
    public List<Shipment> modifyShipment(@RequestBody Shipment shipment) {
        if (shipment.getId() == null || !shipmentService.existsById(shipment.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + shipment.getId());
        }
        shipmentService.saveOrUpdate(shipment);
        return shipmentService.findAll();
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
