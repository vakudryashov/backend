package com.geekbrains.july.warehouse.controllers.web;

import com.geekbrains.july.warehouse.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdditionalController {
    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/errorpage")
    public void errorPage(){
        throw new CustomException("Неправильный логин и/или пароль", HttpStatus.UNAUTHORIZED);
    }

}