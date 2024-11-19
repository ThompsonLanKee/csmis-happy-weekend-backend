package com.spring.csmis.service.feedback;

import com.spring.csmis.dto.feedback.FeedBackDTO;

import java.util.List;

public interface FeedBackService {
    FeedBackDTO addFeedBack(FeedBackDTO feedBackDTO);
    FeedBackDTO updateFeedBack(Long id, FeedBackDTO feedBackDTO);
    FeedBackDTO getFeedBackById(Long id);
    List<FeedBackDTO> getAllFeedBacks();
    void softDeleteFeedBack(Long id);
    void hardDeleteFeedBack(Long id);
    void restoreFeedBack(Long id);

    List<FeedBackDTO> getAllRemovedFeedBacks();

    List<FeedBackDTO> getFeedbackByMenuId(Long menuId);
    List<FeedBackDTO> getFeedbackByUserId(Long userId);
}
