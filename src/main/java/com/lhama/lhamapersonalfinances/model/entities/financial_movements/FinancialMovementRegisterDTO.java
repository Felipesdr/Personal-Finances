package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FinancialMovementRegisterDTO(
        @NotNull @NotBlank
        String name,
        @NotNull
        LocalDateTime date,
        @NotNull
        Double value,
        @NotNull
        Long idCategory) {
}
