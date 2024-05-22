package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.goal.Goal;
import com.lhama.lhamapersonalfinances.model.entities.goal.GoalRegisterDTO;
import com.lhama.lhamapersonalfinances.model.entities.goal.GoalUpdateDTO;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.validations.RequestValidator;
import com.lhama.lhamapersonalfinances.model.repositorys.CategoryRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.FinancialMovementRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.GoalRepository;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import com.lhama.lhamapersonalfinances.model.util.email.Email;
import com.lhama.lhamapersonalfinances.model.util.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GoalRepository goalRepository;
    @Autowired
    FinancialMovementRepository fmRepository;
    @Autowired
    EmailService emailService;


    public Goal registerGoal(GoalRegisterDTO goalRegisterData, Long idUser){
        User user = userRepository.getReferenceById(idUser);
        Category category = categoryRepository.getReferenceById(goalRegisterData.idCategory());
        return goalRepository.save(new Goal(goalRegisterData, category, user));
    }

    public Goal updateGoal(GoalUpdateDTO goalUpdateData, Long idRequestingUser){
        Goal updatedGoal = goalRepository.getReferenceById(goalUpdateData.idGoal());
        User goalUser = userRepository.getReferenceById(updatedGoal.getUser().getIdUser());

        RequestValidator.idUserValidation(idRequestingUser, goalUser.getIdUser());

        updatedGoal.updateGoal(goalUpdateData);
        return updatedGoal;

    }

    public void verifyGoalAfterInsertFm(FinancialMovement financialMovement, User user){
        Goal goal = goalRepository.getGoalByCategoryAndIdUser(financialMovement.getCategory().getIdCategory(), user.getIdUser());
        if(goal != null){
           Double categoryTotal = fmRepository.getUserMonthlyTotalByCategory(
                   user.getIdUser(),
                   financialMovement.getCategory().getIdCategory(),
                   financialMovement.getDate().getYear(),
                   financialMovement.getDate().getMonthValue());

           if(categoryTotal != null && (categoryTotal + financialMovement.getValue()) > goal.getValue()){
               String to = user.getEmail();
               String subject = "Você acaba de ultrapassar uma meta financeira";
               String body = "Olá "
                       + user.getName()
                       + " Você acaba de exceder sua meta "
                       + goal.getName()
                       + " em exatamente R$ "
                       + ((categoryTotal + financialMovement.getValue()) - goal.getValue())
                       + " Hora de tirar a mão do bolso e colocar na consciência ";
               Email email = new Email(to, subject, body);
               emailService.sendEmail(email);
           }
        }
    }




}
