package com.lhama.lhamapersonalfinances.entities.category;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRegisterDTO(
        @NotNull(message = "Nome da categoria não pode ser nulo")
        String name,

        int idUser) {
}
