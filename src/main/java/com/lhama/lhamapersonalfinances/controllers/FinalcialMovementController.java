package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementDTO;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementUpdateDTO;
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
        RequestValidator.validateNullDTO(movementRegisterData);

        FinancialMovement newFinancialMovement = financialMovementService.registerFinancialMovement(movementRegisterData, tokenService.recoverIdFromToken(header));
        Long idMovement = newFinancialMovement.getIdFinancialMovement();
        URI uri = uriBuilder.path("movement/register/{idMovement}").buildAndExpand(idMovement).toUri();

        return ResponseEntity.created(uri).body(new FinancialMovementDTO(newFinancialMovement));
    }

    @PutMapping("update")
    @Transactional
    public ResponseEntity updateFinancialMovement(@RequestBody @Valid FinancialMovementUpdateDTO movementUpdateData, @RequestHeader HttpHeaders header){
        RequestValidator.validateNullDTO(movementUpdateData);

        FinancialMovement updatedMovement = financialMovementService.updateFinancialMovement(movementUpdateData, tokenService.recoverIdFromToken(header));

        return ResponseEntity.ok(new FinancialMovementDTO(updatedMovement));
    }

    @DeleteMapping("deactivate/{idMovement}")
    @Transactional
    public ResponseEntity deactivateFinsncialMovement(@PathVariable Long idMovement, @RequestHeader HttpHeaders header){
        RequestValidator.validateNullDTO(idMovement);

        financialMovementService.deactivateFinancialMovement(idMovement, tokenService.recoverIdFromToken(header));

        return ResponseEntity.noContent().build();
    }
}
