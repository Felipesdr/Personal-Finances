package com.lhama.lhamapersonalfinances.services;

import com.lhama.lhamapersonalfinances.entities.category.*;
import com.lhama.lhamapersonalfinances.entities.user.User;
import com.lhama.lhamapersonalfinances.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

       return categoryRepository.findAllByUserAndActiveTrueOrUserIsNull(user);
    }

    public Category updateCategoryById(CategoryUpdateDTO categoryUpdateData){
        Category updatedCategory = categoryRepository.getReferenceById(categoryUpdateData.idCategory());
        updatedCategory.updateCategory(categoryUpdateData);
        return updatedCategory;

    }

    public void deactivateCategoryCreatedByUserById(CategoryDeactivateDTO categoryDeactivateData){
        if(categoryDeactivateData.idUser() != null){
            Category deactivatedCategory = categoryRepository.getReferenceById(categoryDeactivateData.idCategory());

            if(Objects.equals(deactivatedCategory.getUser().getIdUser(), categoryDeactivateData.idUser())){
                deactivatedCategory.deactivateCategory();
            }
        }
    }

}
