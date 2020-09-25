package com.geekbrains.july.warehouse.repositories;

import com.geekbrains.july.warehouse.entities.Shipment;
import org.springframework.data.repository.CrudRepository;

public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
}
