package com.spring.csmis.service.meal;

import com.spring.csmis.dto.meal.MealCategoryDTO;
import com.spring.csmis.entity.MealCategoryEntity;
import com.spring.csmis.entity.MealTypeEntity;
import com.spring.csmis.repository.MealCategoryRepository;
import com.spring.csmis.repository.MealTypeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MealCategoryServiceImpl implements MealCategoryService{

    private final MealCategoryRepository categoryRepository;
    private final MealTypeRepository typeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MealCategoryServiceImpl(MealCategoryRepository categoryRepository, MealTypeRepository typeRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.typeRepository = typeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MealCategoryDTO addCategory(MealCategoryDTO categoryDTO) {
        MealCategoryEntity categoryEntity = modelMapper.map(categoryDTO, MealCategoryEntity.class);

        // Fetch MealTypeEntities from database using IDs in the DTO
        List<MealTypeEntity> typeEntities = categoryDTO.getTypes().stream()
                .map(typeDTO -> typeRepository.findById(typeDTO.getId())
                        .orElseThrow(() -> new RuntimeException("MealType not found with id: " + typeDTO.getId())))
                .collect(Collectors.toList());

        // Set types in category entity
        categoryEntity.setTypes(typeEntities);
        categoryEntity.setDeleted(false);

        MealCategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return modelMapper.map(savedCategory, MealCategoryDTO.class);
    }

    @Override
    public MealCategoryDTO updateCategory(Long id, MealCategoryDTO categoryDTO) {
        MealCategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MealCategory not found with id: " + id));

        // Update basic details
        existingCategory.setCategoryName(categoryDTO.getCategoryName());

        // Fetch MealTypeEntities based on DTO and update the association
        List<MealTypeEntity> typeEntities = categoryDTO.getTypes().stream()
                .map(typeDTO -> typeRepository.findById(typeDTO.getId())
                        .orElseThrow(() -> new RuntimeException("MealType not found with id: " + typeDTO.getId())))
                .collect(Collectors.toList());

        existingCategory.setTypes(typeEntities);

        MealCategoryEntity updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, MealCategoryDTO.class);
    }

    @Override
    public MealCategoryDTO getCategoryById(Long id) {
        MealCategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return mapToDTO(categoryEntity);
    }

    @Override
    public List<MealCategoryDTO> getAllCategories() {
        List<MealCategoryEntity> categories = categoryRepository.findByIsDeletedFalse();
        return categories.stream()
                .map(category -> modelMapper.map(category, MealCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealCategoryDTO> getAllRemovedCategories() {
        return categoryRepository.findByIsDeletedTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteCategory(Long id) {
        MealCategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryEntity.setDeleted(true);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void hardDeleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void restoreCategory(Long id) {
        MealCategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryEntity.setDeleted(false);
        categoryRepository.save(categoryEntity);
    }

    private MealCategoryDTO mapToDTO(MealCategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, MealCategoryDTO.class);
    }
}
