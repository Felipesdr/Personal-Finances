package com.lhama.lhamapersonalfinances.model.entities.financial_movements;

import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class FinancialMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExpense;
    private String name;
    private LocalDateTime date;
    private Double value;
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "idCategory")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "idUser")
    private User user;
    private boolean active;

    public FinancialMovement() {
    }

    public FinancialMovement(Long idExpense, String name, LocalDateTime date, Double value, Category category, User user) {
        this.idExpense = idExpense;
        this.name = name;
        this.date = date;
        this.value = value;
        this.category = category;
        this.user = user;
        this.active = true;
    }

    public FinancialMovement(FinancialMovementRegisterDTO financialMovementRegisterDTO, Category category, User user){
        this.name = financialMovementRegisterDTO.name();
        this.date = financialMovementRegisterDTO.date();
        this.value = financialMovementRegisterDTO.value();
        this.category = category;
        this.user = user;
        this.active = true;
    }

    public Long getIdFinancialMovement() {
        return idExpense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(o instanceof FinancialMovement financialMovement)) return false;
        return Objects.equals(getIdFinancialMovement(), financialMovement.getIdFinancialMovement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdFinancialMovement());
    }
}
