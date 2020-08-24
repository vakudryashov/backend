package vkudryashov.webserver.service;

import vkudryashov.webserver.configuration.DefaultRoles;
import vkudryashov.webserver.configuration.SecurityConfiguration;
import vkudryashov.webserver.dao.RoleDao;
import vkudryashov.webserver.dao.UserDao;
import vkudryashov.webserver.model.Role;
import vkudryashov.webserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
        if (user.getConfirmPassword() != null && user.getConfirmPassword().equals(user.getPassword())) {
            user.setPassword(securityConfiguration.getPasswordEncoder().encode(user.getPassword()));
        }
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
    public List<User> findAll() {
        return userDao.findAll();
    }
}