package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

public record FinancialMovementTotalByCategoryDTO(Long idCategory, String categoryName, Double value, FinancialMovementType type) {
}
