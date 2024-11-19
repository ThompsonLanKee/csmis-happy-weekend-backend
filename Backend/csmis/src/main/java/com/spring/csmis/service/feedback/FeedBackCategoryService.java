package com.spring.csmis.service.feedback;

import com.spring.csmis.dto.feedback.FeedBackCategoryDTO;
import java.util.List;

public interface FeedBackCategoryService {
    FeedBackCategoryDTO addCategory(FeedBackCategoryDTO categoryDTO);
    FeedBackCategoryDTO updateCategory(Long id, FeedBackCategoryDTO categoryDTO);
    FeedBackCategoryDTO getCategoryById(Long id);
    List<FeedBackCategoryDTO> getAllCategories();
    void softDeleteCategory(Long id);
    void hardDeleteCategory(Long id);
    void restoreCategory(Long id);
    List<FeedBackCategoryDTO> getAllRemovedCategories();
}
