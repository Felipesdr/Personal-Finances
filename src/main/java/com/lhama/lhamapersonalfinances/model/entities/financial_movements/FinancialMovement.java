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
    private Long idFinancialMovement;
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
    @Enumerated(EnumType.STRING)
    private FinancialMovementType type;

    public FinancialMovement() {
    }

    public FinancialMovement(Long idFinancialMovement, String name, LocalDateTime date, Double value, Category category, User user, boolean active, FinancialMovementType type) {
        this.idFinancialMovement = idFinancialMovement;
        this.name = name;
        this.date = date;
        this.value = value;
        this.category = category;
        this.user = user;
        this.active = active;
        this.type = type;
    }

    public FinancialMovement(FinancialMovementRegisterDTO financialMovementRegisterDTO, Category category, User user){
        this.name = financialMovementRegisterDTO.name();
        this.date = financialMovementRegisterDTO.date();
        this.value = financialMovementRegisterDTO.value();
        this.category = category;
        this.user = user;
        this.type = financialMovementRegisterDTO.type();
        this.active = true;
    }

    public Long getIdFinancialMovement() {
        return idFinancialMovement;
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

    public FinancialMovementType getType() {
        return type;
    }

    public void setType(FinancialMovementType type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "FinancialMovement{" +
                "idFinancialMovement=" + idFinancialMovement +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", value=" + value +
                ", category=" + category +
                ", user=" + user +
                ", active=" + active +
                ", type=" + type +
                '}';
    }

    public void update(FinancialMovementUpdateDTO movementUpdateData){
        if(movementUpdateData.name() != null){
            this.name = movementUpdateData.name();
        }
        if(movementUpdateData.date() != null){
            this.date = movementUpdateData.date();
        }
        if(movementUpdateData.value() != null){
            this.value = movementUpdateData.value();
        }
    }

    public void updateCategory(Category category){
        this.category = category;
    }

    public void deactivate(){
        this.active = false;
    }
}
