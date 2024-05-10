package com.lhama.lhamapersonalfinances.model.entities.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDTO(
        @NotNull
        String name,
        @NotNull @Email
        String email,
        @NotNull
        String password,
        @NotNull @Length(max = 11)
        String cpf
) {
}
