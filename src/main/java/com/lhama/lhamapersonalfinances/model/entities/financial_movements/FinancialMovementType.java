package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

public enum FinancialMovementType {

    EXPENSE("expense"),
    INCOME("income");

    private String type;

    FinancialMovementType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
