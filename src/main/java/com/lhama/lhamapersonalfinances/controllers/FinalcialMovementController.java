package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.*;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidator;
import com.lhama.lhamapersonalfinances.model.services.FinancialMovementService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("movement")
public class FinalcialMovementController {

    @Autowired
    TokenService tokenService;
    @Autowired
    FinancialMovementService financialMovementService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity registerFinancialMovement(@RequestBody @Valid FinancialMovementRegisterDTO movementRegisterData, UriComponentsBuilder uriBuilder, @RequestHeader HttpHeaders header){
        RequestValidator.validateNullRequest(movementRegisterData);

        FinancialMovement newFinancialMovement = financialMovementService.registerFinancialMovement(movementRegisterData, tokenService.recoverIdFromToken(header));
        Long idMovement = newFinancialMovement.getIdFinancialMovement();
        URI uri = uriBuilder.path("movement/register/{idMovement}").buildAndExpand(idMovement).toUri();

        return ResponseEntity.created(uri).body(new FinancialMovementDTO(newFinancialMovement));
    }

    @PutMapping("update")
    @Transactional
    public ResponseEntity updateFinancialMovement(@RequestBody @Valid FinancialMovementUpdateDTO movementUpdateData, @RequestHeader HttpHeaders header){
        RequestValidator.validateNullRequest(movementUpdateData);

        FinancialMovement updatedMovement = financialMovementService.updateFinancialMovement(movementUpdateData, tokenService.recoverIdFromToken(header));

        return ResponseEntity.ok(new FinancialMovementDTO(updatedMovement));
    }

    @DeleteMapping("deactivate/{idMovement}")
    @Transactional
    public ResponseEntity deactivateFinsncialMovement(@PathVariable Long idMovement, @RequestHeader HttpHeaders header){
        RequestValidator.validateNullRequest(idMovement);

        financialMovementService.deactivateFinancialMovement(idMovement, tokenService.recoverIdFromToken(header));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("monthly-expense")
    public ResponseEntity getUserTotalMonthlyExpenses(@RequestParam Integer year, @RequestParam Integer month, @RequestHeader HttpHeaders headers){
        RequestValidator.validateNullRequest(year);
        RequestValidator.validateNullRequest(month);

        Double monthlyExpenses = financialMovementService.getUserTotalMonthlyExpenses(tokenService.recoverIdFromToken(headers), year, month);

        return ResponseEntity.ok(new FinancialMovementValueDTO(monthlyExpenses));
    }

    @GetMapping("monthly-income")
    public ResponseEntity getUserTotalMonthlyIncomes(@RequestParam Integer year, @RequestParam Integer month, @RequestHeader HttpHeaders headers){
        RequestValidator.validateNullRequest(year);
        RequestValidator.validateNullRequest(month);

        Double monthlyExpenses = financialMovementService.getUserTotalMonthlyIncomes(tokenService.recoverIdFromToken(headers), year, month);

        return ResponseEntity.ok(new FinancialMovementValueDTO(monthlyExpenses));
    }

    @GetMapping("monthly-balance")
    public ResponseEntity getUserMonthlyBalance(@RequestParam Integer year, @RequestParam Integer month, @RequestHeader HttpHeaders headers){
        RequestValidator.validateNullRequest(year);
        RequestValidator.validateNullRequest(month);

        Double monthlyBalance = financialMovementService.getUserMonthlyBalance(tokenService.recoverIdFromToken(headers), year, month);

        return ResponseEntity.ok(new FinancialMovementValueDTO(monthlyBalance));
    }
}
