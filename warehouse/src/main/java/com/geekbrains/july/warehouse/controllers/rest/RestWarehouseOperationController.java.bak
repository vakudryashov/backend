package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.dtos.ProductDto;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
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
@RequestMapping("/api/v1/operations")
@Api("Operations for Products")
public class RestWarehouseOperationController {
    private ProductsService productsService;

    @Autowired
    public RestWarehouseOperationController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PutMapping(value = "/receipts", consumes = "application/json", produces = "application/json")
    @ApiOperation("receipts products")
    public ResponseEntity<?> receiptsProduct(@RequestBody Product productReceipts) {
        if (productReceipts.getId() == null || !productsService.existsById(productReceipts.getId())) {
            return new ResponseEntity<>("Product not found, id: " + productReceipts.getId(), HttpStatus.NOT_FOUND);
        }
        if (productReceipts.getQuantity() < 0) {
            return new ResponseEntity<>("Product's quantity can not be negative", HttpStatus.BAD_REQUEST);
        }
        Product product = productsService.findById(productReceipts.getId());
        product.setQuantity(product.getQuantity() + productReceipts.getQuantity());
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }

    @PutMapping(value = "/shipment", consumes = "application/json", produces = "application/json")
    @ApiOperation("shipment products")
    public ResponseEntity<?> shipmentProduct(@RequestBody Product productShipment) {
        if (productShipment.getId() == null || !productsService.existsById(productShipment.getId())) {
            return new ResponseEntity<>("Product not found, id: " + productShipment.getId(), HttpStatus.NOT_FOUND);
        }
        if (productShipment.getQuantity() < 0) {
            return new ResponseEntity<>("Product's quantity can not be negative", HttpStatus.BAD_REQUEST);
        }
        Product product = productsService.findById(productShipment.getId());
        product.setQuantity(product.getQuantity() - productShipment.getQuantity());
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }
}