package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.DataProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DataProductHistoryRepository extends JpaRepository<DataProductHistory, Long> {
}
