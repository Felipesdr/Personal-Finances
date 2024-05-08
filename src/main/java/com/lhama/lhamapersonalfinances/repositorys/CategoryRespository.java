package com.lhama.lhamapersonalfinances.repositorys;

import com.lhama.lhamapersonalfinances.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Integer> {

    public List<Category> findAllByActiveTrue();
}
