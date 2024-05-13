package com.lhama.lhamapersonalfinances.model.entities.validations;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

public class RequestValidations {
    @Autowired
    static TokenService tokenService;

    public static <T> void validateNullDTO(T dto) {
        if(dto == null) throw new ValidationException("The request body data cannot be null");
    }

    public static void idUserValidation(Integer idRequestingUser, Integer idUser){
        if(!Objects.equals(idUser, idRequestingUser)) throw new ValidationException("Can´t do this for another user");
    }
}
