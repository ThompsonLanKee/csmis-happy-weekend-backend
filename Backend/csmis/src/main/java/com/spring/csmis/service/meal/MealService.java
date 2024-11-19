package com.spring.csmis.service.meal;

import com.spring.csmis.dto.meal.MealDTO;


import java.util.List;

public interface MealService {
    MealDTO addMeal(MealDTO mealDTO);
    MealDTO updateMeal(Long id, MealDTO mealDTO);
    MealDTO getMealById(Long id);
    List<MealDTO> getAllMeals();
    void softDeleteMeal(Long id);
    void hardDeleteMeal(Long id);
    void restoreMeal(Long id);

    List<MealDTO> getAllRemovedMeals();
}
