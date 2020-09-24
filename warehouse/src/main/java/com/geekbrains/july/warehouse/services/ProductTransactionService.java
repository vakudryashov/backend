package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.ProductTransaction;
import com.geekbrains.july.warehouse.repositories.ProductTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductTransactionService {
    private ProductTransactionRepository productTransactionRepository;

    @Autowired
    public void setProductTransactionRepository(ProductTransactionRepository productTransactionRepository) {
        this.productTransactionRepository = productTransactionRepository;
    }

    public List<ProductTransaction> getAllTransactions() {
        return productTransactionRepository.findAll();
    }

    public List<ProductTransaction> getProductTransactions(Long productId) {
        return productTransactionRepository.findAllByProductId(productId);
    }

    public List<ProductTransaction> getAuthorTransactions(String authorName) {
        return productTransactionRepository.findAllByAuthorName(authorName);
    }

    public List<ProductTransaction> getDateTransactions(Date data) {
        return productTransactionRepository.findAllByData(data);
    }

    public ProductTransaction saveOrUpdate(ProductTransaction productTransaction) {
        return productTransactionRepository.save(productTransaction);
    }
}

