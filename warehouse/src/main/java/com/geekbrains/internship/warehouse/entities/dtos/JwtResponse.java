package com.geekbrains.july.warehouse.entities.dtos;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
