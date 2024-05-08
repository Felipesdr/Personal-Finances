package com.lhama.lhamapersonalfinances.services;

import com.lhama.lhamapersonalfinances.entities.category.Category;
import com.lhama.lhamapersonalfinances.entities.category.CategoryRegisterDTO;
import com.lhama.lhamapersonalfinances.repositorys.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRespository categoryRespository;

    public Category registerCategory(CategoryRegisterDTO categoryRegisterData){

        Category newCategory = new Category(categoryRegisterData);
        newCategory = categoryRespository.save(newCategory);

        return newCategory;
    }

    public List<Category>
}
