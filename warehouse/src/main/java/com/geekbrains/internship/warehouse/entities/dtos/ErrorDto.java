package com.geekbrains.july.warehouse.entities.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {
    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }
}
