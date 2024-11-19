package com.spring.csmis.dto.meal;

import com.spring.csmis.enums.MealCategory;
import lombok.Data;

@Data
public class MealTypeDTO {
    private Long id;
    private String typeName; // Enum for category name
}
