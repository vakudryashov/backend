package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Provider;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.repositories.CategoriesRepository;
import com.geekbrains.july.warehouse.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProviderServiceImpl implements ProviderService{



        private ProviderRepository providerRepository;



    @Autowired
    public void setCategoriesRepository(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

        @Override
        public Provider create(Provider provider) {
            return providerRepository.save(provider);
        }

        @Override
        public List<Provider> readAll() {
            return providerRepository.findAll();
        }

        @Override
        public Provider read(Long id) {
            return providerRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found provider with id = " + id));
        }

        @Override
        public boolean update(Provider provider, Long id) {
            if (providerRepository.existsById(id)) {
                System.out.println("providerRepository.existsById(id): "+providerRepository.existsById(id));
                providerRepository.save( provider);
                return true;
            }

            return false;
        }

        @Override
        public boolean delete(Long id) {

            if (providerRepository.existsById(id)) {
                providerRepository.deleteById(id);
                return true;
            }
            return false;
        }

}
