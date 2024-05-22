package com.lhama.lhamapersonalfinances.model.repositorys;

import com.lhama.lhamapersonalfinances.model.entities.goal.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query(value = """
            select g.* from goal g
            where g.id_user = :idUser
            and g.id_category = :idCategory
            and g.active = true
            """, nativeQuery = true)
    public Goal getGoalByCategoryAndIdUser(Long idCategory, Long idUser);
}
