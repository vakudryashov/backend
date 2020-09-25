package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Fund;
import com.geekbrains.july.warehouse.repositories.FundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FundService {
    @Autowired
    FundRepository fundRepository;

    public List<Fund> findAll() {
        return fundRepository.findAll();
    }
}
