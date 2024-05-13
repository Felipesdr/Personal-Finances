package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRole;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {
    @Autowired
    private  CategoryRepository categoryRepository;
    public  void categoryAlreadyExistsValidation(String name){
        if(categoryRepository.existsByNameAndActiveTrueAndUserRole(name, UserRole.ADMIN)){
            throw new ValidationException("Category already exists");
        }
    }

    public void categoryAlreadyBeenDeactivated(Integer id){
        Category category = categoryRepository.getReferenceById(id);
        if(!category.isActive()) throw new ValidationException("Category is already deactivated");
    }
}
