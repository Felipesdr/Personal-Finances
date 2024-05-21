package com.lhama.lhamapersonalfinances.model.entities.category;

import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateDTO(
        @NotNull
        Long idCategory,
        @NotNull @NotBlank
        String name,
        @NotNull
        FinancialMovementType type) {
}
