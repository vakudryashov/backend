package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementation of (@link SecurityService) interface
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Service
public class SecurityServiceImpl implements SecurityService{

    @Override
    public Authentication findLoggedInUser() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        if (userDetails.isAuthenticated()){
            return userDetails;
        }
        return null;
    }

}
