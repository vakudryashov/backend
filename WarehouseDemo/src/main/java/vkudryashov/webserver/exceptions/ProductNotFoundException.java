package vkudryashov.webserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotFoundException extends ResponseStatusException {
    public ProductNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
