package com.spring.csmis.dto.meal;

import lombok.Data;

@Data
public class MealDTO {
    private Long id;
    private String mealName;
    private Long typeId;
    private String typeName;
    private Long categoryId; // Category ID for mapping with entity
    private String categoryName; // Category name as string
}
