package com.lhama.lhamapersonalfinances.model.entities.goal;

public record GoalDTO (
        Long idGoal, String name, Double value, Long idUser, Long idCategory, String nameCategory
){
    public GoalDTO(Goal goal){
        this(
                goal.getIdGoal(),
                goal.getName(),
                goal.getValue(),
                goal.getUser().getIdUser(),
                goal.getCategory().getIdCategory(),
                goal.getCategory().getName()
        );
    }
}
