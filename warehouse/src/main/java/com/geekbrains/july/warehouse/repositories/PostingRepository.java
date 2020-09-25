package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {
}
