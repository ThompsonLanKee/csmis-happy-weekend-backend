package com.spring.csmis.service.feedback;

import com.spring.csmis.dto.feedback.FeedBackCategoryDTO;
import com.spring.csmis.entity.FeedBackCategoryEntity;

import com.spring.csmis.repository.feedback.FeedBackCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackCategoryServiceImpl implements FeedBackCategoryService {

    @Autowired
    private FeedBackCategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedBackCategoryDTO addCategory(FeedBackCategoryDTO categoryDTO) {
        FeedBackCategoryEntity categoryEntity = modelMapper.map(categoryDTO, FeedBackCategoryEntity.class);
        FeedBackCategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return modelMapper.map(savedCategory, FeedBackCategoryDTO.class);
    }

    @Override
    public FeedBackCategoryDTO updateCategory(Long id, FeedBackCategoryDTO categoryDTO) {
        FeedBackCategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback category not found"));

        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        FeedBackCategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
        return modelMapper.map(updatedCategory, FeedBackCategoryDTO.class);
    }

    @Override
    public FeedBackCategoryDTO getCategoryById(Long id) {
        FeedBackCategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback category not found"));
        return modelMapper.map(categoryEntity, FeedBackCategoryDTO.class);
    }

    @Override
    public List<FeedBackCategoryDTO> getAllCategories() {
        return categoryRepository.findByIsDeletedFalse().stream()
                .map(category -> modelMapper.map(category, FeedBackCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedBackCategoryDTO> getAllRemovedCategories() {
        return categoryRepository.findByIsDeletedTrue().stream()
                .map(category -> modelMapper.map(category, FeedBackCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteCategory(Long id) {
        FeedBackCategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback category not found"));
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void hardDeleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void restoreCategory(Long id) {
        FeedBackCategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback category not found"));
        category.setDeleted(false);
        categoryRepository.save(category);
    }
}
