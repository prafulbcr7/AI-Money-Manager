package com.java.aimoneymanager.repo;

import com.java.aimoneymanager.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenceRepo extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByProfileIOrderByDateDesc(Long profileId);

    List<ExpenseEntity> findTop5ProfileIdOrderByDateDesc(Long profileId);

    @Query("SELECT SUM(e.amount) FROM ExpenceEntity e WHERE e.profile.id = :profileId")
    BigDecimal List<ExpenseEntity> findTotalExpenceByProfileId(@Param("profileId") Long profileId);


}
