package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {

}