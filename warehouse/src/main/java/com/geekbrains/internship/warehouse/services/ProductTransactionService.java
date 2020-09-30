package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.entities.ProductTransaction;
import com.geekbrains.july.warehouse.repositories.ProductTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductTransactionService {
    @Autowired
    private ProductTransactionRepository productTransactionRepository;

    @Autowired
    UsersService usersService;

    public List<ProductTransaction> getAllTransactions() {
        return productTransactionRepository.findAll();
    }

    public List<ProductTransaction> getSupplyTransactions() {
        return productTransactionRepository.findByQuantityGreaterThan(0.0);
    }
    public List<ProductTransaction> getShipmentTransactions() {
        List<ProductTransaction> shipments = productTransactionRepository.findByQuantityLessThan(0.0);
        for (ProductTransaction shipment : shipments) {
            shipment.setQuantity(Math.abs(shipment.getQuantity()));
        }
        return shipments;
    }
    public List<ProductTransaction> getTransactionsByProduct(Product product) {
        return productTransactionRepository.findByProduct(product);
    }
    public ProductTransaction createSupply(ProductTransaction supply) {
        supply.setQuantity(Math.abs(supply.getQuantity()));
        return create(supply);
    }
    public ProductTransaction createShipment(ProductTransaction shipment) {
        shipment.setQuantity(-Math.abs(shipment.getQuantity()));
        return create(shipment);
    }
    public ProductTransaction create(ProductTransaction productTransaction) {
        productTransaction.setTransactionDate(new Date());
        productTransaction.setUser(usersService.findLoggedInUser().get());
        return productTransactionRepository.save(productTransaction);
    }
    public ProductTransaction saveOrUpdate(ProductTransaction productTransaction) {
        productTransaction.setTransactionDate(new Date());
        productTransaction.setUser(usersService.findLoggedInUser().get());
        
        return productTransactionRepository.save(productTransaction);
    }
}

