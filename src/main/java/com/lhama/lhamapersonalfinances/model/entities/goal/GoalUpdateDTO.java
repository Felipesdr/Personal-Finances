package com.lhama.lhamapersonalfinances.model.entities.goal;

import jakarta.validation.constraints.NotNull;

public record GoalUpdateDTO(
        @NotNull
        Long idGoal,
        String name,
        Double value) {
}
