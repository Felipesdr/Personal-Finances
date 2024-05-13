package com.lhama.lhamapersonalfinances.model.entities.category;

import com.lhama.lhamapersonalfinances.model.entities.user.User;
import jakarta.annotation.Nullable;

public record CategoryDTO(Integer idCategory, String name, boolean active,
                          @Nullable
                          Integer idUser){

    public CategoryDTO(Category category){
        this(
                category.getIdCategory(),
                category.getName(), category.isActive(),
                category.getUser().getIdUser()
        );
    }


}
