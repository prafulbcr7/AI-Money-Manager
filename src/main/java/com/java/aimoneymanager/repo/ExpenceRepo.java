package com.java.aimoneymanager.repo;

import com.java.aimoneymanager.entity.ExpenseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenceRepo extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByProfileIOrderByDateDesc(Long profileId);

    List<ExpenseEntity> findTop5ProfileIdOrderByDateDesc(Long profileId);

    @Query("SELECT SUM(e.amount) FROM ExpenceEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenceByProfileId(@Param("profileId") Long profileId);

    List<ExpenseEntity> findProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId, LocalDate startDate, LocalDate endDate, String keyword, Sort sort);

    List<ExpenseEntity> findProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);
}
