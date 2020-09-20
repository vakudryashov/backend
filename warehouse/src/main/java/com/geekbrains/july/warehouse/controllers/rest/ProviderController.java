package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Provider;
import com.geekbrains.july.warehouse.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProviderController {

        private final ProviderService providerService;

        @Autowired
        public ProviderController(ProviderService providerService) {
            this.providerService = providerService;
        }

    @PostMapping(value = "/providers")
    public ResponseEntity<?> create(@RequestBody Provider provider) {
        providerService.create(provider);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/providers")
    public ResponseEntity<List<Provider>> read() {
        final List<Provider> providers = providerService.readAll();

        return providers != null &&  !providers.isEmpty()
                ? new ResponseEntity<>(providers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/providers/{id}")
    public ResponseEntity<Provider> read(@PathVariable(name = "id") int id) {
        final Provider provider = providerService.read(id);

        return provider != null
                ? new ResponseEntity<>(provider, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/providers/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Provider provider) {
        final boolean updated = providerService.update(provider, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/providers/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = providerService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
