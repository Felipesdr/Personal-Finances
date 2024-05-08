package com.lhama.lhamapersonalfinances.entities.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRegisterCreatedByUserDTO(
        @NotNull(message = "Nome da categoria não pode ser nulo")
        String name,

        int idUser) {
}
