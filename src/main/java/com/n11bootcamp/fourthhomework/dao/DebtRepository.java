package com.n11bootcamp.fourthhomework.dao;

import com.n11bootcamp.fourthhomework.entity.Debt;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface DebtRepository extends JpaRepository<Debt,Long> {


    @Query("from Debt debt where debt.expiryDate BETWEEN :startDate AND :endDate")
    List<Debt> findAllByExpiryDateBetween(@Param("startDate")LocalDate startDate,@Param("endDate") LocalDate endDate);


    @Query("from Debt debt where debt.userId = :userId AND debt.remainingDebt>0")
    List<Debt> findByUserId( @Param("userId")Long userId);


    @Query("from Debt debt where debt.userId = :userId AND debt.expiryDate<CURRENT_DATE")
    List<Debt> findAllOverdueUserDebts(@Param("userId")Long userId);
}
