package com.lhama.lhamapersonalfinances.entities.category;

import com.lhama.lhamapersonalfinances.entities.user.User;

public record CategoryDTO(Integer idCategory, String name, boolean active, User user){

    public CategoryDTO(Category category){
        this(category.getIdCategory(), category.getName(), category.isActive(), category.getUser());
    }


}
