package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.category.CategoryDTO;
import com.lhama.lhamapersonalfinances.model.entities.category.CategoryRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.category.CategoryUpdateDTO;
import com.lhama.lhamapersonalfinances.model.entities.goal.Goal;
import com.lhama.lhamapersonalfinances.model.entities.goal.GoalDTO;
import com.lhama.lhamapersonalfinances.model.entities.goal.GoalRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.goal.GoalUpdateDTO;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidator;
import com.lhama.lhamapersonalfinances.model.services.GoalService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("goal")
public class GoalController {
    @Autowired
    TokenService tokenService;
    @Autowired
    GoalService goalService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity registerGoal(@Valid @RequestBody GoalRegisterDTO goalRegisterData, UriComponentsBuilder uriBuilder, @RequestHeader HttpHeaders headers){
        RequestValidator.validateNullRequest(goalRegisterData);
        Long idUser = tokenService.recoverIdFromToken(headers);
        Goal newGoal = goalService.registerGoal(goalRegisterData, idUser);

        Long newGoalId = newGoal.getIdGoal();
        URI uri = uriBuilder.path("register/goal/{id}").buildAndExpand(newGoalId).toUri();

        return ResponseEntity.created(uri).body(new GoalDTO(newGoal));
    }

    @PutMapping("update")
    @Transactional
    public ResponseEntity updateGoal(@Valid @RequestBody GoalUpdateDTO goalUpdateData, @RequestHeader HttpHeaders headers){
        RequestValidator.validateNullRequest(goalUpdateData);
        Long idRequestingUser = tokenService.recoverIdFromToken(headers);
        Goal updatedGoal = goalService.updateGoal(goalUpdateData, idRequestingUser);

        return ResponseEntity.ok(new GoalDTO(updatedGoal));
    }
}
