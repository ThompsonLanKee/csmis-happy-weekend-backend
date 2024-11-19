package com.spring.csmis.service.meal;



import com.spring.csmis.dto.meal.MealTypeDTO;

import java.util.List;

public interface MealTypeService {
    MealTypeDTO addType(MealTypeDTO mealTypeDTO);
    MealTypeDTO updateType(Long id, MealTypeDTO mealTypeDTO);
    MealTypeDTO getTypeById(Long id);
    List<MealTypeDTO> getAllTypes();
    void softDeleteType(Long id);
    void hardDeleteType(Long id);
    void restoreType(Long id);
    List<MealTypeDTO> getAllRemovedTypes();
    List<MealTypeDTO> getMealTypesByCategoryIdAndIsDeleteFalse(Long id);
}
