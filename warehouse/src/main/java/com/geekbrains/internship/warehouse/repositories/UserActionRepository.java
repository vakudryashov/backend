package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.ProductTransaction;
import com.geekbrains.july.warehouse.entities.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    List<UserAction> findAllByProductId(Long productId);

    List<UserAction> findAllByAuthorName(String authorName);

    List<UserAction> findAllByData(Date data);
}
