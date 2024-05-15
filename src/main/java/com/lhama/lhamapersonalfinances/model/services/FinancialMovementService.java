package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementUpdateDTO;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.validations.FinancialMovementValidator;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidator;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.FinancialMovementRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialMovementService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    FinancialMovementRepository financialMovementRepository;
    @Autowired
    FinancialMovementValidator financialMovementValidator;

    public FinancialMovement registerFinancialMovement(FinancialMovementRegisterDTO movementRegisterData, Long idUser){
        financialMovementValidator.idCategoryUserValidation(idUser, movementRegisterData.idCategory());

        User user = userRepository.getReferenceById(idUser);
        Category category = categoryRepository.getReferenceById(movementRegisterData.idCategory());
        FinancialMovement newFinancialMovement = new FinancialMovement(movementRegisterData, category, user);

        return financialMovementRepository.save(newFinancialMovement);
    }

    public FinancialMovement updateFinancialMovement(FinancialMovementUpdateDTO movementUpdateData, Long idRequestingUser){
        financialMovementValidator.idCategoryUserValidation(idRequestingUser, movementUpdateData.idCategory());

        FinancialMovement movement = financialMovementRepository.getReferenceById(movementUpdateData.idMovement());
        RequestValidator.idUserValidation(idRequestingUser, movement.getUser().getIdUser());

        if(movementUpdateData.idCategory() != null){
           Category category = categoryRepository.getReferenceById(movementUpdateData.idCategory());
           movement.updateCategory(category);
        }

        return financialMovementRepository.save(movement);
    }

    public void deactivateFinancialMovement(Long idMovement, Long idRequestingUser){
        FinancialMovement movement = financialMovementRepository.getReferenceById(idMovement);
        RequestValidator.idUserValidation(idRequestingUser, movement.getUser().getIdUser());
        financialMovementValidator.movementAlreadyBeenDeactivated(idMovement);

        movement.deactivate();
        financialMovementRepository.save(movement);
    }
}
