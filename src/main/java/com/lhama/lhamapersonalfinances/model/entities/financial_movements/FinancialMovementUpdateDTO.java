package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record FinancialMovementUpdateDTO(Long idMovement,
                                         String name,
                                         LocalDateTime date,
                                         @Positive
                                         Double value,
                                         Long idCategory) {
}
