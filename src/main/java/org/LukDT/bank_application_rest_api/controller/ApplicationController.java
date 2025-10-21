package org.LukDT.bank_application_rest_api.controller;

import org.LukDT.bank_application_rest_api.dto.OperationFilterRequest;
import org.LukDT.bank_application_rest_api.dto.PutMoneyRequest;
import org.LukDT.bank_application_rest_api.entity.Operation;
import org.LukDT.bank_application_rest_api.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;

    @PostMapping("/operations")
    public List<Operation> getOperationList(@RequestBody OperationFilterRequest request) {
        return applicationService.getOperationList(request.userId(), request.from(), request.to());
    }

    @GetMapping("/user/{id}")
    public BigDecimal getBalance(@PathVariable long id) {

        return applicationService.getBalance(id);
    }

    @PutMapping("/user/{id}/put")
    public int putMoney(@PathVariable long id, @RequestBody PutMoneyRequest request) {
        return applicationService.putMoney(id, request.summa().toString());
    }

    @PutMapping("/user/{id}/take")
    public int takeMoney(@PathVariable long id, @RequestBody PutMoneyRequest request) {
        return applicationService.takeMoney(id, request.summa().toString());
    }

}
