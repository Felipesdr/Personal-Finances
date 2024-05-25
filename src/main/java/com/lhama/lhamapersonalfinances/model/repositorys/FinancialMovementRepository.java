package com.lhama.lhamapersonalfinances.model.repositorys;

import com.lhama.lhamapersonalfinances.model.entities.financial_movements.FinancialMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialMovementRepository extends JpaRepository<FinancialMovement, Long> {

    @Query(value = """
            select SUM(fm.value) from financial_movement fm
            where fm.id_user = :idUser
            and fm.type = "EXPENSE"
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            and fm.active = true
            """, nativeQuery = true)
    Double getUserTotalMonthlyExpenses(Long idUser, Integer year, Integer month);

    @Query(value = """
            select SUM(fm.value) from financial_movement fm
            where fm.id_user = :idUser
            and fm.type = "INCOME"
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            and fm.active = true
            """, nativeQuery = true)
    Double getUserTotalMonthlyIncomes(Long idUser, Integer year, Integer month);

    @Query(value = """
            select(
                (select COALESCE(SUM(fm.value),0) from financial_movement fm
                where fm.id_user = :idUser
                and fm.type = "INCOME"
                and YEAR(fm.date) = :year
                and MONTH(fm.date) = :month
                and fm.active = true)
                -
                (select COALESCE(SUM(fm.value),0) from financial_movement fm
                where fm.id_user = :idUser
                and fm.type = "EXPENSE"
                and YEAR(fm.date) = :year
                and MONTH(fm.date) = :month
                and fm.active = true)
            )
            """, nativeQuery = true)
    Double getUserMonthlyBalance(Long idUser, Integer year, Integer month);

    @Query(value = """
            select SUM(fm.value) from financial_movement fm
            where fm.id_user = :idUser
            and fm.id_category = :idCategory
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            and fm.active = true
            """, nativeQuery = true)
    Double getUserMonthlyTotalByCategory(Long idUser, Long idCategory, Integer year, Integer month);

    @Query(value = """
            select fm.* from financial_movement fm
            where (fm.id_user = :idUser or fm.id_user = 3)
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            and fm.active = true
            """, nativeQuery = true)
    List<FinancialMovement> getAllUserMonthlyTotalByCategory(Long idUser, Integer year, Integer month);

    @Query(value = """
            select fm.* from financial_movement fm
            where fm.id_user = :idUser
            and fm.id_category = :idCategory
            and YEAR(fm.date) = :year
            and MONTH(fm.date) = :month
            and fm.active = true
            """, nativeQuery = true)
    Page<FinancialMovement> getAllUserMonthlyFinancialMovementsByCategory(Long idUser, Long idCategory, Integer year, Integer month, Pageable pageable);
}
