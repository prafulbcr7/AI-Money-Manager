package com.java.aimoneymanager.repo;

import com.java.aimoneymanager.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {

     List<CategoryEntity> findByProfileId(Long profileId);

     Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

     List<CategoryEntity> findByCategoryTypeAndProfileId(String categoryType, Long profileId);

     Boolean existsByNameAndProfileId(String categoryName, Long profileId);
}
