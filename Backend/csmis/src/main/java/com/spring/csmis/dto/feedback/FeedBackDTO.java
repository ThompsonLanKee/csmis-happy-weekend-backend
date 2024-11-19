package com.spring.csmis.dto.feedback;

import com.spring.csmis.entity.FeedBackTypeEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FeedBackDTO {
    private Long id;
    private LocalDate date;
    private Long menuId;
    private Long userId;

    private Long categoryId; // Category ID for mapping with entity
    private String categoryName; // Category name as string

    private List<FeedBackTypeEntity> types;
    private String content;




}
