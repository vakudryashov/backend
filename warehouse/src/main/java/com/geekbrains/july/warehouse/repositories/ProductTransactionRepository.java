package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.ProductTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long>{
    List<ProductTransaction> findAllByProductId(Long productId);

    List<ProductTransaction> findAllByAuthorName(String authorName);

    List<ProductTransaction> findAllByData(Date data);
}