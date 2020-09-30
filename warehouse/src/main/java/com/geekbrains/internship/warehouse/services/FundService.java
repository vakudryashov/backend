package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Fund;
import com.geekbrains.july.warehouse.entities.Product;
import com.geekbrains.july.warehouse.exceptions.CustomException;
import com.geekbrains.july.warehouse.repositories.FundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FundService {
    @Autowired
    FundRepository fundRepository;

    @Autowired
    ProductsService productsService;

    public List<Fund> findAll() {
        return fundRepository.findAll();
    }

    public Fund findByProductId(Long id){
        Product product = productsService.findById(id);
        return fundRepository.findById(product.getId()).orElseThrow(
                () -> new CustomException(String.format("Can't find product fund by id = \"%d\"",id), HttpStatus.NOT_FOUND)
        );
    }

    public Fund findByProductTitle(String title){
        Product product = productsService.findByTitle(title);

        return fundRepository.findById(product.getId()).orElseThrow(
                () -> new CustomException(String.format("Can't find product fund by title \"%s\"",title), HttpStatus.NOT_FOUND)
        );
    }
}
