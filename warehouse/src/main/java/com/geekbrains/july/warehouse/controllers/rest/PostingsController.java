package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Posting;
import com.geekbrains.july.warehouse.entities.dtos.ErrorDto;
import com.geekbrains.july.warehouse.exceptions.CustomException;
import com.geekbrains.july.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.july.warehouse.services.PostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/postings")
@Api("Set of endpoints for CRUD operations for Posting")
public class PostingsController {
    @Autowired
    private PostingService postingService;

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all postings")
    public List<Posting> getAllPostings() {
        return postingService.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new posting")
    public List<Posting> createNomenclature(@RequestBody Posting posting) {
        postingService.saveOrUpdate(posting);
        return postingService.findAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one posting by id")
    public List<Posting> deleteOneProducts(@PathVariable Long id) {
        postingService.deleteById(id);
        return postingService.findAll();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing posting")
    public List<Posting> modifyCategory(@RequestBody Posting posting) {
        if (posting.getId() == null || !postingService.existsById(posting.getId())) {
            throw new ProductNotFoundException("Product not found, id: " + posting.getId());
        }
        postingService.saveOrUpdate(posting);
        return postingService.findAll();
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(CustomException exception){
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()),exception.getStatus());
    }
}
