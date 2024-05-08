package com.lhama.lhamapersonalfinances.entities.category;

public record CategoryDTO(Integer idCategory, String name, boolean active){

    public CategoryDTO(Category category){
        this(category.getIdCategory(), category.getName(), category.isActive());
    }
}
