package com.lhama.lhamapersonalfinances.model.entities.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateDTO(
        @NotNull
        Long idCategory,
        @NotNull @NotBlank
        String name) {
}
