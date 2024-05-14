package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;

import java.util.Objects;

public class RequestValidator {

    public static <T> void validateNullDTO(T dto) {
        if(dto == null) throw new ValidationException("The request body data cannot be null");
    }

    public static void idUserValidation(Long idRequestingUser, Long idUser){
        if(!Objects.equals(idUser, idRequestingUser)) throw new ValidationException("Can´t do this for another user");
    }
}
