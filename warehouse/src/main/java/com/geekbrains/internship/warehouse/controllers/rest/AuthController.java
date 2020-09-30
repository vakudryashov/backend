package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.configs.JwtTokenUtil;
import com.geekbrains.internship.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.internship.warehouse.entities.dtos.JwtRequest;
import com.geekbrains.internship.warehouse.entities.dtos.JwtResponse;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Set of endpoints for CRUD operations for authentication")
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserDetailsService userDetailsService,
                          JwtTokenUtil jwtTokenUtil,
                          AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/v1/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception{
        try{
            authenticate(authRequest.getUsername(),authRequest.getPassword());
        }catch(BadCredentialsException e){
            throw new CustomException("Доступ запрещён. Проверьте правильность логина и пароля", HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
