package com.spring.csmis.service.feedback;


import com.spring.csmis.dto.feedback.FeedBackTypeDTO;
import com.spring.csmis.dto.meal.MealTypeDTO;

import java.util.List;

public interface FeedBackTypeService {
    FeedBackTypeDTO addType(FeedBackTypeDTO feedBackTypeDTO);
    FeedBackTypeDTO updateType(Long id, FeedBackTypeDTO feedBackTypeDTO);
    FeedBackTypeDTO getTypeById(Long id);
    List<FeedBackTypeDTO> getAllTypes();
    void softDeleteType(Long id);
    void hardDeleteType(Long id);
    void restoreType(Long id);
    List<FeedBackTypeDTO> getAllRemovedTypes();
    List<FeedBackTypeDTO> getFeedBackTypesByCategoryIdAndIsDeleteFalse(Long id);
}
