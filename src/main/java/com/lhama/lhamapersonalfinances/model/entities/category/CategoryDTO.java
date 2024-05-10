package com.lhama.lhamapersonalfinances.model.entities.category;

import com.lhama.lhamapersonalfinances.model.entities.user.User;

public record CategoryDTO(Integer idCategory, String name, boolean active, User user){

    public CategoryDTO(Category category){
        this(category.getIdCategory(), category.getName(), category.isActive(), category.getUser());
    }


}
