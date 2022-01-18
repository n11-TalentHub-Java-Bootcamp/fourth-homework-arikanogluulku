package com.n11bootcamp.fourthhomework.dao;

import com.n11bootcamp.fourthhomework.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("from Collection collection where collection.dateOfRegistration BETWEEN :startDate AND :endDate")
    List<Collection> findAllByDateOfRegistrationBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(" from Collection collection " +
            " left join Debt debt on collection.debtId = debt.id " +
            " where debt.userId = :userId "
    )
    List<Collection> findByUserCollections(@Param("userId") Long userId);

    @Query(" from Collection collection " +
            " left join Debt debt on collection.debtId = debt.id " +
            " where debt.userId = :userId AND collection.debtType = 'LATE_FEE'"

    )
    List<Collection> findByUserDebtTypeLateFee(@Param("userId") Long userId);
}
