package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import com.lhama.lhamapersonalfinances.model.entities.category.*;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRole;
import com.lhama.lhamapersonalfinances.model.entities.validations.CategoryValidator;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidations;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryValidator categoryValidator;

    public Category registerCategory(CategoryRegisterDTO categoryRegisterData, Long idUser) {
        categoryValidator.categoryAlreadyExistsValidation(categoryRegisterData.name());

        User user = userRepository.getReferenceById(idUser);
        Category newCategory = new Category(categoryRegisterData, user);
        newCategory = categoryRepository.save(newCategory);

        return newCategory;
    }

    public List<Category> getAllCategoryByIdUserAndGlobal(Long idUser) {
        User user = userRepository.getReferenceById(idUser);
        return categoryRepository.findAllByUserOrUserRoleAndActiveTrue(user, UserRole.ADMIN);
    }

    public Category updateCategoryById(CategoryUpdateDTO categoryUpdateData, Long idRequestingUser) {
        Category updatedCategory = categoryRepository.getReferenceById(categoryUpdateData.idCategory());
        User categoryUser = userRepository.getReferenceById(updatedCategory.getUser().getIdUser());
        RequestValidations.idUserValidation(idRequestingUser, categoryUser.getIdUser());
        categoryValidator.categoryAlreadyBeenDeactivated(categoryUpdateData.idCategory());
        categoryValidator.categoryAlreadyExistsValidation(categoryUpdateData.name());

        updatedCategory.updateCategory(categoryUpdateData);
        return updatedCategory;
    }

    public void deactivateCategoryCreatedByUserById(Long idCategory, Long idRequestingUser) {
        Category deactivatedCategory = categoryRepository.getReferenceById(idCategory);
        User categoryUser = userRepository.getReferenceById(deactivatedCategory.getUser().getIdUser());
        RequestValidations.idUserValidation(idRequestingUser, categoryUser.getIdUser());
        categoryValidator.categoryAlreadyBeenDeactivated(idCategory);

        deactivatedCategory.deactivateCategory();
        categoryRepository.save(deactivatedCategory);
    }
}
