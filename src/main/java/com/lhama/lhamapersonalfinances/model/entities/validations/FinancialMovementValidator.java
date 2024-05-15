package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRole;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.FinancialMovementRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class FinancialMovementValidator {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    FinancialMovementRepository financialMovementRepository;

    public void idCategoryUserValidation(Long idRequestingUser, Long idCategory) {
        User user = userRepository.getReferenceById(idRequestingUser);
        Category category = categoryRepository.getReferenceById(idCategory);

        if (!Objects.equals(idRequestingUser, category.getUser().getIdUser()) && category.getUser().getRole() != UserRole.ADMIN)
            throw new ValidationException("Can´t do this for another user");
    }

    public void movementAlreadyBeenDeactivated(Long idMovement){
        FinancialMovement movement = financialMovementRepository.getReferenceById(idMovement);
        if(!movement.isActive()) throw new ValidationException("Financial movement is already deactivated");
    }
}
