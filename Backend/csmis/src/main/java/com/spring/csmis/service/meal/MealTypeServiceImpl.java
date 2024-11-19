package com.spring.csmis.service.meal;

import com.spring.csmis.dto.meal.MealTypeDTO;
import com.spring.csmis.entity.MealTypeEntity;
import com.spring.csmis.repository.MealTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealTypeServiceImpl implements MealTypeService {

    @Autowired
    private MealTypeRepository typeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MealTypeDTO addType(MealTypeDTO mealTypeDTO) {
        MealTypeEntity mealTypeEntity = modelMapper.map(mealTypeDTO, MealTypeEntity.class);
        MealTypeEntity savedType = typeRepository.save(mealTypeEntity);
        return mapToDTO(savedType);
    }

    @Override
    public MealTypeDTO updateType(Long id, MealTypeDTO mealTypeDTO) {
        MealTypeEntity mealTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal type not found with id: " + id));

        mealTypeEntity.setTypeName(mealTypeDTO.getTypeName());

        MealTypeEntity updatedType = typeRepository.save(mealTypeEntity);
        return mapToDTO(updatedType);
    }

    @Override
    public List<MealTypeDTO> getMealTypesByCategoryIdAndIsDeleteFalse(Long id) {
        List<MealTypeEntity> mealTypes = typeRepository.findMealTypesByCategoryIdAndIsDeletedFalse(id);
        return mealTypes.stream()
                .map(mealType -> modelMapper.map(mealType, MealTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MealTypeDTO getTypeById(Long id) {
        MealTypeEntity mealTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal type not found with id: " + id));
        return mapToDTO(mealTypeEntity);
    }

    @Override
    public List<MealTypeDTO> getAllTypes() {
        return typeRepository.findByIsDeletedFalse().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTypeDTO> getAllRemovedTypes() {
        return typeRepository.findByIsDeletedTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteType(Long id) {
        MealTypeEntity mealTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal type not found with id: " + id));

        mealTypeEntity.setDeleted(true);
        typeRepository.save(mealTypeEntity);
    }

    @Override
    public void hardDeleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public void restoreType(Long id) {
        MealTypeEntity mealTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal type not found with id: " + id));

        mealTypeEntity.setDeleted(false);
        typeRepository.save(mealTypeEntity);
    }

    private MealTypeDTO mapToDTO(MealTypeEntity mealTypeEntity) {
        return modelMapper.map(mealTypeEntity, MealTypeDTO.class);
    }
}
