package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Role;
import com.geekbrains.july.warehouse.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RolesRepository rolesRepository;

    public List<Role> findAll() {
        return (List<Role>) rolesRepository.findAll();
    }
}
