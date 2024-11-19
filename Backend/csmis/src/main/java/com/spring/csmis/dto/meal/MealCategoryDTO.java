package com.spring.csmis.dto.meal;

import com.spring.csmis.entity.MealTypeEntity;
import com.spring.csmis.enums.MealCategory;
import lombok.Data;

import java.util.List;

@Data
public class MealCategoryDTO {
    private Long id;
    private String categoryName; // Enum for category name
    private List<MealTypeEntity> types;
}
