package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record FinancialMovementRegisterDTO(
        @NotNull
        FinancialMovementType type,
        @NotNull @NotBlank
        String name,
        @NotNull
        LocalDateTime date,
        @NotNull @Positive
        Double value,
        @NotNull
        Long idCategory) {
}
