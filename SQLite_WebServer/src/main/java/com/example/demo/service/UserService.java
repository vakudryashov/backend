package com.example.demo.service;

import com.example.demo.model.User;

/**
* Service class for {@link User}
 *
 * @author Vladimir Kudryashov
 * version 1.0
* */

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
