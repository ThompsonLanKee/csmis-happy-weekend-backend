package com.spring.csmis.dto.feedback;

import com.spring.csmis.entity.FeedBackTypeEntity;
import com.spring.csmis.entity.MealTypeEntity;
import com.spring.csmis.enums.MealCategory;
import lombok.Data;

import java.util.List;

@Data
public class FeedBackCategoryDTO {
    private Long id;
    private String categoryName; // Enum for category name
    private List<FeedBackTypeEntity> types;
}
