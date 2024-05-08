package com.lhama.lhamapersonalfinances.services;

import com.lhama.lhamapersonalfinances.entities.category.Category;
import com.lhama.lhamapersonalfinances.entities.category.CategoryRegisterCreatedByUserDTO;
import com.lhama.lhamapersonalfinances.entities.category.CategoryRegisterDTO;
import com.lhama.lhamapersonalfinances.entities.user.User;
import com.lhama.lhamapersonalfinances.repositorys.CategoryRespository;
import com.lhama.lhamapersonalfinances.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRespository categoryRespository;
    @Autowired
    UserRepository userRepository;

    public Category registerCategory(CategoryRegisterDTO categoryRegisterData){
        Category newCategory = new Category(categoryRegisterData);
        newCategory = categoryRespository.save(newCategory);

        return newCategory;
    }

    public Category registerCategoryCreatedByUser(CategoryRegisterCreatedByUserDTO categoryRegisterCreatedByUserData){
        User user =  userRepository.findById(categoryRegisterCreatedByUserData.idUser()).orElseThrow();
        Category newCategory = new Category(categoryRegisterCreatedByUserData, user);
        newCategory = categoryRespository.save(newCategory);

        return newCategory;
    }

}
