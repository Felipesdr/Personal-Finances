package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.*;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.validations.FinancialMovementValidator;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidator;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.FinancialMovementRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public FinancialMovement registerFinancialMovement(FinancialMovementRegisterDTO movementRegisterData, Long idUser) {
        financialMovementValidator.idCategoryUserValidation(idUser, movementRegisterData.idCategory());

        User user = userRepository.getReferenceById(idUser);
        Category category = categoryRepository.getReferenceById(movementRegisterData.idCategory());
        FinancialMovement newFinancialMovement = new FinancialMovement(movementRegisterData, category, user);

        return financialMovementRepository.save(newFinancialMovement);
    }

    public FinancialMovement updateFinancialMovement(FinancialMovementUpdateDTO movementUpdateData, Long idRequestingUser) {
        FinancialMovement movement = financialMovementRepository.getReferenceById(movementUpdateData.idMovement());
        RequestValidator.idUserValidation(idRequestingUser, movement.getUser().getIdUser());

        if (movementUpdateData.idCategory() != null) {
            financialMovementValidator.idCategoryUserValidation(idRequestingUser, movementUpdateData.idCategory());
            Category category = categoryRepository.getReferenceById(movementUpdateData.idCategory());
            movement.updateCategory(category);
        }
        movement.update(movementUpdateData);

        return financialMovementRepository.save(movement);
    }

    public void deactivateFinancialMovement(Long idMovement, Long idRequestingUser) {
        FinancialMovement movement = financialMovementRepository.getReferenceById(idMovement);
        RequestValidator.idUserValidation(idRequestingUser, movement.getUser().getIdUser());
        financialMovementValidator.movementAlreadyBeenDeactivated(idMovement);

        movement.deactivate();
        financialMovementRepository.save(movement);
    }

    public Double getUserTotalMonthlyExpenses(Long idUser, Integer year, Integer month) {
        Double monthlyExpenses = financialMovementRepository.getUserTotalMonthlyExpenses(idUser, year, month);
        if (monthlyExpenses == null) return 0.0;
        return monthlyExpenses;
    }

    public Double getUserTotalMonthlyIncomes(Long idUser, Integer year, Integer month) {
        Double monthlyIncomes = financialMovementRepository.getUserTotalMonthlyIncomes(idUser, year, month);
        if (monthlyIncomes == null) return 0.0;
        return monthlyIncomes;
    }

    public Double getUserMonthlyBalance(Long idUser, Integer year, Integer month){
        Double monthlyBalance = financialMovementRepository.getUserMonthlyBalance(idUser, year, month);
        if(monthlyBalance == null) return 0.0;
        return monthlyBalance;
    }

    public Double getUserMonthlyTotalByCategory(Long idUser, Long idCategory, Integer year, Integer month){
        financialMovementValidator.idCategoryUserValidation(idUser, idCategory);

        return financialMovementRepository.getUserMonthlyTotalByCategory(idUser, idCategory, year, month);
    }

    public Page<FinancialMovementDTO> getAllUserMonthlyFinancialMovementsByCategory(Long idUser, Long idCategory, Integer year, Integer month, Pageable pageable){
        financialMovementValidator.idCategoryUserValidation(idUser, idCategory);

        Page page = financialMovementRepository.getAllUserMonthlyFinancialMovementsByCategory(idUser, idCategory, year, month, pageable);
        return page.map(e-> new FinancialMovementDTO((FinancialMovement) e));
    }

    public List<FinancialMovementTotalByCategoryDTO> getAllUserMonthlyTotalExpensesByCategory(Long idUser, Integer year, Integer month){

        List<FinancialMovement> allUserFinancialMovements = financialMovementRepository.getAllUserMonthlyTotalByCategory(idUser, year, month);
        List<Long> categoryIds = new ArrayList<>();
        List<FinancialMovementTotalByCategoryDTO> fmTotaByCategorylList = new ArrayList<>();

        allUserFinancialMovements.forEach(fm-> {
            if(!categoryIds.contains(fm.getCategory().getIdCategory())){
                categoryIds.add(fm.getCategory().getIdCategory());
            }
        });

        categoryIds.forEach(cm->{
                Double totalByCategory = allUserFinancialMovements.stream()
                        .filter(fm-> Objects.equals(fm.getCategory().getIdCategory(), cm))
                        .mapToDouble(FinancialMovement::getValue)
                        .sum();
                var fm = categoryRepository.getReferenceById(cm);
                var financialMovementTotalByCategory = new FinancialMovementTotalByCategoryDTO(cm, fm.getName(), totalByCategory, fm.getType());
            fmTotaByCategorylList.add(financialMovementTotalByCategory);
        });

        return fmTotaByCategorylList;
    }
}
