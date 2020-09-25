package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Unit;
import com.geekbrains.july.warehouse.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public Unit saveOrUpdate(Unit unit) {
        return unitRepository.save(unit);
    }

    public void deleteById(Long id) {
        unitRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return unitRepository.existsById(id);
    }
}
