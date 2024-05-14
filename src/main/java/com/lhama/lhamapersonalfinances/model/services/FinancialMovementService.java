package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.validations.FinancialMovementValidator;
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

    public FinancialMovement registerFinancialMovement(FinancialMovementRegisterDTO financialMovementRegisterData, Long idUser){
        User user = userRepository.getReferenceById(idUser);
        Category category = categoryRepository.getReferenceById(financialMovementRegisterData.idCategory());
        financialMovementValidator.idCategoryUserValidation(idUser, category);

        FinancialMovement newFinancialMovement = new FinancialMovement(financialMovementRegisterData, category, user);

        return financialMovementRepository.save(newFinancialMovement);
    }
}
