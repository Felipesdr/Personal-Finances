package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

import java.time.LocalDateTime;

public record FinancialMovementDTO(Long idMovement, FinancialMovementType type, String name, LocalDateTime date, Double value, Long idCategory, Long idUser, boolean active) {
    public FinancialMovementDTO(FinancialMovement financialMovement){
        this(
                financialMovement.getIdFinancialMovement(),
                financialMovement.getType(),
                financialMovement.getName(),
                financialMovement.getDate(),
                financialMovement.getValue(),
                financialMovement.getCategory().getIdCategory(),
                financialMovement.getUser().getIdUser(),
                financialMovement.isActive()
        );
    }
}
