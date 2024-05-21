package com.lhama.lhamapersonalfinances.model.entities.category;

import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementType;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;

    @Column(length = 512)
    private String name;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "idUser")
    private User user;

    @Enumerated(EnumType.STRING)
    private FinancialMovementType type;

    public Category() {
    }

    public Category(Long idCategory, String name, Boolean active, User user, FinancialMovementType type) {
        this.idCategory = idCategory;
        this.name = name;
        this.active = active;
        this.user = user;
        this.type = type;
    }

    public Category(CategoryRegisterDTO categoryRegisterDTO){
        this.name = categoryRegisterDTO.name();
        this.active = true;
    }

    public Category(CategoryRegisterDTO categoryRegisterDTO, User user){
        this.name = categoryRegisterDTO.name();
        this.active = true;
        this.type = categoryRegisterDTO.type();
        this.user = user;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FinancialMovementType getType() {
        return type;
    }

    public void setType(FinancialMovementType type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", user=" + user +
                '}';
    }

    public void updateCategory(CategoryUpdateDTO categoryUpdateData){
        if (categoryUpdateData != null){
            this.name = categoryUpdateData.name();
        }
    }

    public void deactivateCategory(){
        if(idCategory != null){
            this.active = false;
        }
    }
}
