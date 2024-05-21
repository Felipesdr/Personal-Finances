package com.lhama.lhamapersonalfinances.model.entities.goal;

import com.lhama.lhamapersonalfinances.model.entities.category.Category;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGoal;
    private String name;
    private Double value;
    private User user;
    private Category category;
    private boolean active;

    public Goal() {
    }

    public Goal(Long idGoal, String name, Double value, User user, Category category) {
        this.idGoal = idGoal;
        this.name = name;
        this.value = value;
        this.user = user;
        this.category = category;
        this.active = true;
    }

    public Goal(GoalRegisterDTO goalRegisterData, Category category, User user){
        this.name = goalRegisterData.name();
        this.value = goalRegisterData.value();
        this.user = user;
        this.category = category;
        this.active = true;
    }

    public Long getIdGoal() {
        return idGoal;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isActive() {
        return active;
    }

}
