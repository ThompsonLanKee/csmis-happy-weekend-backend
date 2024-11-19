package com.spring.csmis.service.feedback;

import com.spring.csmis.dto.feedback.FeedBackTypeDTO;
import com.spring.csmis.entity.FeedBackTypeEntity;
import com.spring.csmis.repository.feedback.FeedBackTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackTypeServiceImpl implements FeedBackTypeService {

    @Autowired
    private FeedBackTypeRepository typeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedBackTypeDTO addType(FeedBackTypeDTO feedBackTypeDTO) {
        FeedBackTypeEntity feedBackTypeEntity = modelMapper.map(feedBackTypeDTO, FeedBackTypeEntity.class);
        FeedBackTypeEntity savedType = typeRepository.save(feedBackTypeEntity);
        return mapToDTO(savedType);
    }

    @Override
    public FeedBackTypeDTO updateType(Long id, FeedBackTypeDTO feedBackTypeDTO) {
        FeedBackTypeEntity feedBackTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FeedBack type not found with id: " + id));

        feedBackTypeEntity.setTypeName(feedBackTypeDTO.getTypeName());

        FeedBackTypeEntity updatedType = typeRepository.save(feedBackTypeEntity);
        return mapToDTO(updatedType);
    }

    @Override
    public List<FeedBackTypeDTO> getFeedBackTypesByCategoryIdAndIsDeleteFalse(Long id) {
        List<FeedBackTypeEntity> feedbackTypes = typeRepository.findFeedBackTypesByCategoryIdAndIsDeletedFalse(id);
        return feedbackTypes.stream()
                .map(feedBackType -> modelMapper.map(feedBackType, FeedBackTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FeedBackTypeDTO getTypeById(Long id) {
        FeedBackTypeEntity feedBackTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal type not found with id: " + id));
        return mapToDTO(feedBackTypeEntity);
    }

    @Override
    public List<FeedBackTypeDTO> getAllTypes() {
        return typeRepository.findByIsDeletedFalse().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedBackTypeDTO> getAllRemovedTypes() {
        return typeRepository.findByIsDeletedTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteType(Long id) {
        FeedBackTypeEntity feedBackTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal type not found with id: " + id));

        feedBackTypeEntity.setDeleted(true);
        typeRepository.save(feedBackTypeEntity);
    }

    @Override
    public void hardDeleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public void restoreType(Long id) {
        FeedBackTypeEntity feedBackTypeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FeedBack type not found with id: " + id));

        feedBackTypeEntity.setDeleted(false);
        typeRepository.save(feedBackTypeEntity);
    }

    private FeedBackTypeDTO mapToDTO(FeedBackTypeEntity feedBackTypeEntity) {
        return modelMapper.map(feedBackTypeEntity, FeedBackTypeDTO.class);
    }
}
