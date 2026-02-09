package com.java.aimoneymanager.service;

import com.java.aimoneymanager.dto.CategoryDTO;
import com.java.aimoneymanager.entity.CategoryEntity;
import com.java.aimoneymanager.entity.ProfileEntity;
import com.java.aimoneymanager.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final ProfileService profileService;



    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        if (categoryRepo.existsByNameAndProfileId(categoryDTO.getName(), profileEntity.getId()) ){
            //throw new ResponseStatusException(HttpStatus.CONFLICT, "Dublicate Category Found. Category with This name Already Exists !!!.");
            throw new RuntimeException("Dublicate Category Found. Category with This name Already Exists !!!.");
        }

        CategoryEntity newCategory = mapCategorySTOToCategoryEntity(categoryDTO, profileEntity);
        newCategory = categoryRepo.save(newCategory);
        return mapCategoryEntityToCategoryDTO(newCategory);
    }


    //Helper Methods
    private CategoryEntity mapCategorySTOToCategoryEntity(CategoryDTO categoryDTO, ProfileEntity profileEntity) {
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .icon(categoryDTO.getIcon())
                .categoryType(categoryDTO.getCategoryType())
                .profile(profileEntity)
                .build();
    }

    private CategoryDTO mapCategoryEntityToCategoryDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .profileId(categoryEntity.getProfile() != null ? categoryEntity.getProfile().getId() : null)
                .name(categoryEntity.getName())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .icon(categoryEntity.getIcon())
                .categoryType(categoryEntity.getCategoryType())
                .build();
    }

}
