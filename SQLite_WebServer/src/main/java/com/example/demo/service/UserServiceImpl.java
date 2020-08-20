package com.example.demo.service;

import com.example.demo.configuration.DefaultRoles;
import com.example.demo.configuration.SecurityConfiguration;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface
 *
 * @author Vladimir Kudryashov
 * version 1.0
 * */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public void save(User user) {
        String defaultRoleName = DefaultRoles.ROLE_USER.name();
        if (roleDao.count() == 0){
            Role role = new Role();
            role.setName(DefaultRoles.ROLE_ADMIN.name());
            roleDao.save(role);
            role = new Role();
            role.setName(DefaultRoles.ROLE_USER.name());
            roleDao.save(role);
            defaultRoleName = DefaultRoles.ROLE_ADMIN.name();
        }
        user.setPassword(securityConfiguration.getPasswordEncoder().encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();

        roles.add(roleDao.findByName(defaultRoleName));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}