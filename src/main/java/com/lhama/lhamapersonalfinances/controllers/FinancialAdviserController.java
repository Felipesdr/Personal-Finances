package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.model.util.financialAdviser.FinancialAdviserDTO;
import com.lhama.lhamapersonalfinances.model.util.financialAdviser.FinancialAdviserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advice")
public class FinancialAdviserController {

    @Autowired
    FinancialAdviserService service;

    @PostMapping
    public ResponseEntity getFinancialAdvice(@RequestBody FinancialAdviserDTO financialAdviserData){

        List<String> response = service.requestAdvice(financialAdviserData.prompt());


        return ResponseEntity.ok(response.stream().map(FinancialAdviserDTO::new));
    }
}
