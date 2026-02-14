package com.java.aimoneymanager.repo;

import com.java.aimoneymanager.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByProfileIOrderByDateDesc(Long profileId);

    List<IncomeEntity> findTop5ProfileIdOrderByDateDesc(Long profileId);

    @Query("SELECT SUM(e.amount) FROM IncomeEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenceByProfileId(@Param("profileId") Long profileId);

    List<IncomeEntity> findProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId, LocalDate startDate, LocalDate endDate, String keyword, Sort sort);

    List<IncomeEntity> findProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

}
