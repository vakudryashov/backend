package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
}
