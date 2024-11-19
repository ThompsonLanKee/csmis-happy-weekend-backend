package com.spring.csmis.service.meal;

import com.spring.csmis.dto.meal.MealCategoryDTO;

import java.util.List;

public interface MealCategoryService {
    MealCategoryDTO addCategory(MealCategoryDTO categoryDTO);
    MealCategoryDTO updateCategory(Long id, MealCategoryDTO categoryDTO);
    MealCategoryDTO getCategoryById(Long id);
    List<MealCategoryDTO> getAllCategories();
    void softDeleteCategory(Long id);
    void hardDeleteCategory(Long id);
    void restoreCategory(Long id);
    List<MealCategoryDTO> getAllRemovedCategories();
}
