package com.geekbrains.july.warehouse.controllers.rest;

import com.geekbrains.july.warehouse.entities.Unit;
import com.geekbrains.july.warehouse.entities.UserAction;
import com.geekbrains.july.warehouse.services.UserActionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actions")
public class UserActionsController {
    @Autowired
    private UserActionService userActionService;

    @GetMapping(produces = "application/json")
    public List<UserAction> getAllActions() {
        return userActionService.findAll();
    }

}
