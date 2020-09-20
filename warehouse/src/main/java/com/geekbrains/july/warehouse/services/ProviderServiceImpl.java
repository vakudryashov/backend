package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Provider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProviderServiceImpl implements ProviderService{


        // Хранилище поставщиков
        private static final Map<Integer, Provider> PROVIDER_REPOSITORY_MAP = new HashMap<>();

        // Переменная для генерации ID поставщика
        private static final AtomicInteger PROVIDER_ID_HOLDER = new AtomicInteger();

        @Override
        public void create(Provider provider) {
            final int providerId = PROVIDER_ID_HOLDER.incrementAndGet();
            provider.setId(providerId);
            PROVIDER_REPOSITORY_MAP.put(providerId, provider);
        }

        @Override
        public List<Provider> readAll() {
            return new ArrayList<>(PROVIDER_REPOSITORY_MAP.values());
        }

        @Override
        public Provider read(int id) {
            return PROVIDER_REPOSITORY_MAP.get(id);
        }

        @Override
        public boolean update(Provider provider, int id) {
            if (PROVIDER_REPOSITORY_MAP.containsKey(id)) {
                provider.setId(id);
                PROVIDER_REPOSITORY_MAP.put(id, provider);
                return true;
            }

            return false;
        }

        @Override
        public boolean delete(int id) {
            return PROVIDER_REPOSITORY_MAP.remove(id) != null;
        }

}
