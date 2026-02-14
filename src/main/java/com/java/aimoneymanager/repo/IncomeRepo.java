package com.java.aimoneymanager.repo;

import com.java.aimoneymanager.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepo extends JpaRepository<IncomeEntity, Long> {

}
