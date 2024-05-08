package com.lhama.lhamapersonalfinances.entities.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategory;

    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome não pode estar vazio")
    @Column(length = 512)
    private String name;

    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome não pode estar vazio")
    private boolean active;

    public Category() {
    }

    public Category(Integer idCategory, String name, Boolean active) {
        this.idCategory = idCategory;
        this.name = name;
        this.active = active;
    }

    public Category(CategoryRegisterDTO categoryRegisterDTO){
        this.name = categoryRegisterDTO.name();
        this.active = true;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(getIdCategory(), category.getIdCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCategory());
    }
}
