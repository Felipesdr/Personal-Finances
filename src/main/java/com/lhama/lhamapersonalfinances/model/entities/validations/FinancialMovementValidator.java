package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRole;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class FinancialMovementValidator {

    public void idCategoryUserValidation(Long idRequestingUser, Category category) {

        if (!Objects.equals(idRequestingUser, category.getUser().getIdUser()) && category.getUser().getRole() != UserRole.ADMIN)
            throw new ValidationException("Can´t do this for another user");
    }
}
