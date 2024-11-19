package com.spring.csmis.dto.feedback;

import com.spring.csmis.enums.MealCategory;
import lombok.Data;

@Data
public class FeedBackTypeDTO {
    private Long id;
    private String typeName; // Enum for category name
}
