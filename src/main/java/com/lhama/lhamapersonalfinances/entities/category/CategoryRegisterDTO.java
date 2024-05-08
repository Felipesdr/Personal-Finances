package com.lhama.lhamapersonalfinances.entities.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRegisterDTO(
        @NotNull(message = "Nome da categoria não pode ser nulo")
        @NotBlank(message = "Nome da categoria não pode estar em branco")
        String name) {
}
