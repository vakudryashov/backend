package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Contractor;
import com.geekbrains.july.warehouse.repositories.ContractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorService {
    @Autowired
    private ContractorRepository contractorRepository;

    public List<Contractor> findAll() {
        return contractorRepository.findAll();
    }

    public Contractor saveOrUpdate(Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    public void deleteById(Long id) {
        contractorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return contractorRepository.existsById(id);
    }
}
