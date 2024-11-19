package com.spring.csmis.service.feedback;


import com.spring.csmis.dto.feedback.FeedBackDTO;
import com.spring.csmis.entity.FeedBackEntity;
import com.spring.csmis.entity.MenuEntity;
import com.spring.csmis.entity.UserEntity;

import com.spring.csmis.repository.MenuRepository;
import com.spring.csmis.repository.UserRepository;
import com.spring.csmis.repository.feedback.FeedBackCategoryRepository;
import com.spring.csmis.repository.feedback.FeedBackRepository;
import com.spring.csmis.repository.feedback.FeedBackTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackRepository feedbackRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedBackCategoryRepository categoryRepository;

    @Autowired
    private FeedBackTypeRepository typeRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedBackDTO addFeedBack(FeedBackDTO feedBackDTO) {
        FeedBackEntity feedbackEntity = convertToEntity(feedBackDTO);

        // Set Menu, User, and Category
        feedbackEntity.setMenu(menuRepository.findById(feedBackDTO.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menu not found")));
        feedbackEntity.setUser(userRepository.findById(feedBackDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        feedbackEntity.setCategory(categoryRepository.findById(feedBackDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        // Ensure Feedback Types are managed entities
        feedbackEntity.setTypes(feedBackDTO.getTypes().stream()
                .map(type -> typeRepository.findById(type.getId())
                        .orElseThrow(() -> new RuntimeException("Feedback type not found with id: " + type.getId())))
                .collect(Collectors.toList()));
        feedbackEntity.setContent(feedBackDTO.getContent());
        FeedBackEntity savedFeedback = feedbackRepository.save(feedbackEntity);
        return convertToDTO(savedFeedback);
    }

    @Override
    public FeedBackDTO updateFeedBack(Long id, FeedBackDTO feedBackDTO) {
        FeedBackEntity feedbackEntity = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        // Update fields
        feedbackEntity.setMenu(menuRepository.findById(feedBackDTO.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menu not found")));
        feedbackEntity.setUser(userRepository.findById(feedBackDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        feedbackEntity.setCategory(categoryRepository.findById(feedBackDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        // Update Feedback Types
        feedbackEntity.setTypes(feedBackDTO.getTypes().stream()
                .map(type -> typeRepository.findById(type.getId())
                        .orElseThrow(() -> new RuntimeException("Feedback type not found with id: " + type.getId())))
                .collect(Collectors.toList()));
        feedbackEntity.setContent(feedBackDTO.getContent());
        FeedBackEntity updatedFeedback = feedbackRepository.save(feedbackEntity);
        return convertToDTO(updatedFeedback);
    }

    @Override
    public FeedBackDTO getFeedBackById(Long id) {
        FeedBackEntity feedbackEntity = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        return convertToDTO(feedbackEntity);
    }

    @Override
    public List<FeedBackDTO> getAllFeedBacks() {
        return feedbackRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteFeedBack(Long id) {
        FeedBackEntity feedbackEntity = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        feedbackEntity.setDeleted(true); // Assuming `isDeleted` exists in entity
        feedbackRepository.save(feedbackEntity);
    }

    @Override
    public void hardDeleteFeedBack(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public void restoreFeedBack(Long id) {
        FeedBackEntity feedbackEntity = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        feedbackEntity.setDeleted(false); // Re-enable the feedback entry
        feedbackRepository.save(feedbackEntity);
    }

    @Override
    public List<FeedBackDTO> getAllRemovedFeedBacks() {
        return feedbackRepository.findAll().stream()
                .filter(FeedBackEntity::isDeleted) // Assuming `isDeleted` exists
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert entity to DTO
    private FeedBackDTO mapToDTO(FeedBackEntity feedbackEntity) {
        return modelMapper.map(feedbackEntity, FeedBackDTO.class);
    }

    // Implementation for getFeedbackByMenuId
    @Override
    public List<FeedBackDTO> getFeedbackByMenuId(Long menuId) {
        List<FeedBackEntity> feedbacks = feedbackRepository.findByMenuIdAndIsDeletedFalse(menuId);
        return feedbacks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Implementation for getFeedbackByUserId
    @Override
    public List<FeedBackDTO> getFeedbackByUserId(Long userId) {
        List<FeedBackEntity> feedbacks = feedbackRepository.findByUserIdAndIsDeletedFalse(userId);
        return feedbacks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private FeedBackDTO convertToDTO(FeedBackEntity feedbackEntity) {
        return modelMapper.map(feedbackEntity, FeedBackDTO.class);
    }

    private FeedBackEntity convertToEntity(FeedBackDTO feedbackDTO) {
        return modelMapper.map(feedbackDTO, FeedBackEntity.class);
    }
}
