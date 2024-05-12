package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.category.*;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidations;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
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
    @Autowired
    TokenService tokenService;

    public Category registerGlobalCategory(CategoryRegisterDTO categoryRegisterData){
        RequestValidations.validateNullDTO(categoryRegisterData);

        if(categoryRepository.existsByNameAndActiveTrue(categoryRegisterData.name())){
            throw new ValidationException("Category already exists");
        }

        Category newCategory = new Category(categoryRegisterData);
        newCategory.setIdCategory(null);
        newCategory = categoryRepository.save(newCategory);
        return newCategory;
    }

    public Category registerCategoryCreatedByUser(CategoryRegisterDTO categoryRegisterCreatedByUserData, Integer idUser){
        RequestValidations.validateNullDTO(categoryRegisterCreatedByUserData);

        if(categoryRepository.existsByNameAndActiveTrue(categoryRegisterCreatedByUserData.name())){
            throw new ValidationException("Category already exists");
        }

        User user =  userRepository.findById(idUser).get();
        Category newCategory = new Category(categoryRegisterCreatedByUserData, user);
        newCategory = categoryRepository.save(newCategory);

        return newCategory;
    }

    public List<Category> getAllCategoryByIdUserAndGlobal(Integer idUser){
        User user = userRepository.findById(idUser).orElseThrow();

       return categoryRepository.findAllByUserAndActiveTrueOrUserIsNullAndActiveTrue(user);
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

    public void deactivateGlobalCategory(Integer idCategory){
        Category deactivatedGlobalCategory = categoryRepository.getReferenceById(idCategory);
        deactivatedGlobalCategory.deactivateCategory();
    }

}
