package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

import java.time.LocalDateTime;

public record FinancialMovementDTO(Long idMovement, String name, LocalDateTime date, Double value, Long idCategory, String categoryName, Long idUser) {

    public FinancialMovementDTO(FinancialMovement financialMovement){
        this(
                financialMovement.getIdFinancialMovement(),
                financialMovement.getName(),
                financialMovement.getDate(),
                financialMovement.getValue(),
                financialMovement.getCategory().getIdCategory(),
                financialMovement.getCategory().getName(),
                financialMovement.getUser().getIdUser()
        );
    }
}
