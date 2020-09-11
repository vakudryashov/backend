package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Role;
import com.geekbrains.july.warehouse.entities.User;
import com.geekbrains.july.warehouse.repositories.RolesRepository;
import com.geekbrains.july.warehouse.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setRolesRepository(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Autowired
    private UserDetailsService userDetailsService;
    public Optional<User> findByLogin(String login) {
        return usersRepository.findOneByLogin(login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> findLoggedInUser() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        if (!userDetails.isAuthenticated()){
            return Optional.empty();
        }else {
            return findByLogin(userDetails.getName());
        }
    }

    public List<User> findAll() {
        return (List<User>) usersRepository.findAll();
    }

    public void setRoles(User user) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : user.getRoleNames()) {
            roles.add(rolesRepository.findOneByName(roleName));
        }
        user.setRoles(roles);
    }

    public void save(User user) {
        usersRepository.save(user);
    }

    public void delete(User user) {
        usersRepository.delete(user);
    }
}