package com.lhama.lhamapersonalfinances.model.entities.goal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GoalRegisterDTO (
    @NotNull @NotBlank
    String name,
    @NotNull @Positive
    Double value,
    @NotNull
    Long idCategory){

}
