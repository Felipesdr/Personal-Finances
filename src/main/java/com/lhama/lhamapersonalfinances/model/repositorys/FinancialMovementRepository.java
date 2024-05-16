package com.lhama.lhamapersonalfinances.model.repositorys;

import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovementType;
import com.lhama.lhamapersonalfinances.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;

@Repository
public interface FinancialMovementRepository extends JpaRepository<FinancialMovement, Long> {

    @Query(value = """
            select SUM(fm.value) from financial_movement fm
            where fm.id_user = :idUser
            and fm.type = "EXPENSE"
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            """, nativeQuery = true)
    Double getUserTotalMonthlyExpenses(Long idUser, Integer year, Integer month);

    @Query(value = """
            select SUM(fm.value) from financial_movement fm
            where fm.id_user = :idUser
            and fm.type = "INCOME"
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            """, nativeQuery = true)
    Double getUserTotalMonthlyIncomes(Long idUser, Integer year, Integer month);

    @Query(value = """
            select(
                (select COALESCE(SUM(fm.value),0) from financial_movement fm
                where fm.id_user = :idUser
                and fm.type = "INCOME"
                and YEAR(fm.date) = :year
                and MONTH(fm.date) = :month)
                -
                (select COALESCE(SUM(fm.value),0) from financial_movement fm
                where fm.id_user = :idUser
                and fm.type = "EXPENSE"
                and YEAR(fm.date) = :year
                and MONTH(fm.date) = :month)
            )
            """, nativeQuery = true)
    Double getUserMonthlyBalance(Long idUser, Integer year, Integer month);
}
