package com.lhama.lhamapersonalfinances.services;

import com.lhama.lhamapersonalfinances.entities.category.*;
import com.lhama.lhamapersonalfinances.entities.user.User;
import com.lhama.lhamapersonalfinances.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public Category registerCategory(CategoryRegisterDTO categoryRegisterData){
        Category newCategory = new Category(categoryRegisterData);
        newCategory = categoryRepository.save(newCategory);

        return newCategory;
    }

    public Category registerCategoryCreatedByUser(CategoryRegisterCreatedByUserDTO categoryRegisterCreatedByUserData){
        User user =  userRepository.findById(categoryRegisterCreatedByUserData.idUser()).orElseThrow();
        Category newCategory = new Category(categoryRegisterCreatedByUserData, user);
        newCategory = categoryRepository.save(newCategory);

        return newCategory;
    }

    public List<Category> getAllCategoryByIdUserAndGlobal(Integer idUser){
        User user = userRepository.findById(idUser).orElseThrow();

       return categoryRepository.findAllByUserOrUserIsNullAndActiveTrue(user);
    }

    public Category updateCategoryById(CategoryUpdateDTO categoryUpdateData){
        Category updatedCategory = categoryRepository.getReferenceById(categoryUpdateData.idCategory());
        updatedCategory.updateCategory(categoryUpdateData);
        return updatedCategory;

    }

}
