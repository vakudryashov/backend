package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Shipment;
import com.geekbrains.july.warehouse.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    UsersService usersService;

    public List<Shipment> findAll() {
        return (List<Shipment>) shipmentRepository.findAll();
    }

    public Shipment saveOrUpdate(Shipment shipment) {
        shipment.setShipmentDate(new Date());
        shipment.setUser(usersService.findLoggedInUser().get());
        return shipmentRepository.save(shipment);
    }

    public void deleteById(Long id) {
        shipmentRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return shipmentRepository.existsById(id);
    }
}
