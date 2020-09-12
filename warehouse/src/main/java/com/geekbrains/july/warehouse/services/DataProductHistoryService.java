package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Category;
import com.geekbrains.july.warehouse.entities.DataProductHistory;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.repositories.CategoriesRepository;
import com.geekbrains.july.warehouse.repositories.DataProductHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataProductHistoryService {
    private DataProductHistoryRepository dataProductHistoryRepository;

    @Autowired
    public void setDataProductHistoryRepository(DataProductHistoryRepository dataProductHistoryRepository) {
        this.dataProductHistoryRepository = dataProductHistoryRepository;
    }

    public List<DataProductHistory> getAllCategories() {
        return dataProductHistoryRepository.findAll();
    }

    public DataProductHistory saveOrUpdate(DataProductHistory dataProductHistory) {
        return dataProductHistoryRepository.save(dataProductHistory);
    }

    public void deleteById(Long id) {
        dataProductHistoryRepository.deleteById(id);
    }
}
