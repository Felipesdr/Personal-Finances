package com.lhama.lhamapersonalfinances.model.entities.category;

import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementType;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import jakarta.annotation.Nullable;

public record CategoryDTO(Long idCategory, String name, FinancialMovementType type, boolean active, Long idUser){

    public CategoryDTO(Category category){
        this(
                category.getIdCategory(),
                category.getName(),
                category.getType(),
                category.isActive(),
                category.getUser().getIdUser()
        );
    }


}
