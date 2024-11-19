package com.spring.csmis.service.meal;


import com.spring.csmis.dto.meal.MealDTO;
import com.spring.csmis.entity.MealCategoryEntity;
import com.spring.csmis.entity.MealEntity;
import com.spring.csmis.entity.MealTypeEntity;
import com.spring.csmis.repository.MealCategoryRepository;
import com.spring.csmis.repository.MealRepository;
import com.spring.csmis.repository.MealTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealCategoryRepository categoryRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository; // Add repository for MealTypeEntity

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MealDTO addMeal(MealDTO mealDTO) {
        MealEntity mealEntity = modelMapper.map(mealDTO, MealEntity.class);

        // Fetch the category by ID
        MealCategoryEntity category = categoryRepository.findById(mealDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + mealDTO.getCategoryId()));
        mealEntity.setCategory(category);

        // Fetch and set the meal type by ID
        MealTypeEntity mealType = mealTypeRepository.findById(mealDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("Meal Type not found with id: " + mealDTO.getTypeId()));
        mealEntity.setType(mealType);

        // Save the meal entity
        MealEntity savedMeal = mealRepository.save(mealEntity);

        // Convert the saved entity back to DTO
        MealDTO responseDTO = modelMapper.map(savedMeal, MealDTO.class);
        responseDTO.setCategoryId(category.getId());
        responseDTO.setCategoryName(category.getCategoryName());
        responseDTO.setTypeId(mealType.getId());
        responseDTO.setTypeName(mealType.getTypeName());

        return responseDTO;
    }

    @Override
    public MealDTO updateMeal(Long id, MealDTO mealDTO) {
        MealEntity mealEntity = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));

        // Update meal name
        mealEntity.setMealName(mealDTO.getMealName());

        // Fetch and update the category by ID
        MealCategoryEntity category = categoryRepository.findById(mealDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + mealDTO.getCategoryId()));
        mealEntity.setCategory(category);

        // Fetch and update the meal type by ID
        MealTypeEntity mealType = mealTypeRepository.findById(mealDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("Meal Type not found with id: " + mealDTO.getTypeId()));
        mealEntity.setType(mealType);

        // Save updated meal entity
        MealEntity updatedMeal = mealRepository.save(mealEntity);

        // Convert the updated entity back to DTO
        MealDTO responseDTO = modelMapper.map(updatedMeal, MealDTO.class);
        responseDTO.setCategoryId(category.getId());
        responseDTO.setCategoryName(category.getCategoryName());
        responseDTO.setTypeId(mealType.getId());
        responseDTO.setTypeName(mealType.getTypeName());

        return responseDTO;
    }

    @Override
    public MealDTO getMealById(Long id) {
        MealEntity mealEntity = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));

        if (mealEntity.isDeleted()) {
            throw new RuntimeException("Meal has been deleted.");
        }

        // Convert entity to DTO
        MealDTO mealDTO = modelMapper.map(mealEntity, MealDTO.class);
        mealDTO.setCategoryId(mealEntity.getCategory().getId());
        mealDTO.setCategoryName(mealEntity.getCategory().getCategoryName());
        mealDTO.setTypeId(mealEntity.getType().getId());
        mealDTO.setTypeName(mealEntity.getType().getTypeName());

        return mealDTO;
    }

    @Override
    public List<MealDTO> getAllMeals() {
        List<MealEntity> meals = mealRepository.findByIsDeletedFalse();
        return meals.stream()
                .map(meal -> {
                    MealDTO mealDTO = modelMapper.map(meal, MealDTO.class);
                    mealDTO.setCategoryId(meal.getCategory().getId());
                    mealDTO.setCategoryName(meal.getCategory().getCategoryName());
                    mealDTO.setTypeId(meal.getType().getId());
                    mealDTO.setTypeName(meal.getType().getTypeName());
                    return mealDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MealDTO> getAllRemovedMeals() {
        List<MealEntity> removedMeals = mealRepository.findByIsDeletedTrue();
        return removedMeals.stream()
                .map(meal -> {
                    MealDTO mealDTO = modelMapper.map(meal, MealDTO.class);
                    mealDTO.setCategoryId(meal.getCategory().getId());
                    mealDTO.setCategoryName(meal.getCategory().getCategoryName());
                    mealDTO.setTypeId(meal.getType().getId());
                    mealDTO.setTypeName(meal.getType().getTypeName());
                    return mealDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteMeal(Long id) {
        MealEntity mealEntity = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));

        mealEntity.setDeleted(true);
        mealRepository.save(mealEntity);
    }

    @Override
    public void hardDeleteMeal(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new RuntimeException("Meal not found with id: " + id);
        }
        mealRepository.deleteById(id);
    }

    @Override
    public void restoreMeal(Long id) {
        MealEntity mealEntity = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));

        mealEntity.setDeleted(false);
        mealRepository.save(mealEntity);
    }
}
