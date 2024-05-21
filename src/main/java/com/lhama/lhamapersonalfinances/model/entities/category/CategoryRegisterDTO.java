package com.lhama.lhamapersonalfinances.model.entities.category;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRegisterDTO(
        @NotNull
        String name,
        @NotNull
        FinancialMovementType type){

}
