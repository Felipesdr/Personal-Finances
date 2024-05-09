package com.lhama.lhamapersonalfinances.repositorys;

import com.lhama.lhamapersonalfinances.entities.category.Category;
import com.lhama.lhamapersonalfinances.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public List<Category> findAllByUserOrUserIsNullAndActiveTrue(User user);

}
