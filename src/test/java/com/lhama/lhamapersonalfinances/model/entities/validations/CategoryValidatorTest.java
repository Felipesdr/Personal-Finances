package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRole;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryValidatorTest {

    @InjectMocks
    CategoryValidator categoryValidator;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    Category category;
    @Mock
    UserRole role;
    @Mock
    User user;

    @Test
    void categoryAlreadyExistsValidationSuccess() {
        given(user.getRole()).willReturn(UserRole.ADMIN);
        given(categoryRepository.existsByNameAndActiveTrueAndUserRole(category.getName(), user.getRole())).willReturn(false);

        Assertions.assertDoesNotThrow(()-> categoryValidator.categoryAlreadyExistsValidation(category.getName()));
    }

    @Test
    void categoryAlreadyExistsValidationError() {
        given(user.getRole()).willReturn(UserRole.ADMIN);
        given(categoryRepository.existsByNameAndActiveTrueAndUserRole(category.getName(), user.getRole())).willReturn(true);

        Assertions.assertThrows(ValidationException.class, ()->categoryValidator.categoryAlreadyExistsValidation(category.getName()));
    }

    @Test
    void categoryAlreadyBeenDeactivatedSuccess() {
        given(categoryRepository.getReferenceById(category.getIdCategory())).willReturn(category);
        given(category.isActive()).willReturn(true);

        Assertions.assertDoesNotThrow(()-> categoryValidator.categoryAlreadyBeenDeactivated(category.getIdCategory()));
    }

    @Test
    void categoryAlreadyBeenDeactivatedError() {
        given(categoryRepository.getReferenceById(category.getIdCategory())).willReturn(category);
        given(category.isActive()).willReturn(false);

        Assertions.assertThrows(ValidationException.class, ()-> categoryValidator.categoryAlreadyBeenDeactivated(category.getIdCategory()));
    }
}