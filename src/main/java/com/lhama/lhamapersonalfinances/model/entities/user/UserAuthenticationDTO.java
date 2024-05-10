package com.lhama.lhamapersonalfinances.model.entities.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserAuthenticationDTO(
        @NotNull
        String email,

        @NotNull
        String password) {
}
