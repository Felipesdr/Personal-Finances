package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;

public class RequestValidations {

    public static <T> void validateNullDTO(T dto) {
        if(dto == null) throw new ValidationException("The request body data cannot be null");
    }
}
