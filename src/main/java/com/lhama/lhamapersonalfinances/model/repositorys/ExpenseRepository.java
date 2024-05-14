package com.lhama.lhamapersonalfinances.model.repositorys;

import com.lhama.lhamapersonalfinances.model.entities.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
