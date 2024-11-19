package com.spring.csmis.service;

import com.spring.csmis.dto.AvoidMeatDTO;
import com.spring.csmis.entity.AvoidMeatEntity;
import com.spring.csmis.entity.LunchRegistrationEntity;
import com.spring.csmis.repository.AvoidMeatRepository;
import com.spring.csmis.repository.LunchRegistrationRepository;
import com.spring.csmis.service.AvoidMeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvoidMeatServiceImpl implements AvoidMeatService {

    @Autowired
    private AvoidMeatRepository avoidMeatRepository;

    @Autowired
    private LunchRegistrationRepository lunchRegistrationRepository;

    @Override
    public AvoidMeatDTO insertAvoidMeat(AvoidMeatDTO avoidMeatDTO) {
        AvoidMeatEntity entity = new AvoidMeatEntity();
        entity.setMeat_name(avoidMeatDTO.getMeatName());
        AvoidMeatEntity savedEntity = avoidMeatRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public AvoidMeatDTO editAvoidMeat(Long id, AvoidMeatDTO avoidMeatDTO) {
        Optional<AvoidMeatEntity> optionalEntity = avoidMeatRepository.findById(id);
        if (optionalEntity.isPresent()) {
            AvoidMeatEntity entity = optionalEntity.get();
            entity.setMeat_name(avoidMeatDTO.getMeatName());
            AvoidMeatEntity updatedEntity = avoidMeatRepository.save(entity);
            return mapToDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void softDeleteAvoidMeat(Long id) {
        Optional<AvoidMeatEntity> optionalEntity = avoidMeatRepository.findById(id);
        optionalEntity.ifPresent(entity -> {
            entity.setDelete(true);
            avoidMeatRepository.save(entity);
        });
    }

    @Override
    public void hardDeleteAvoidMeat(Long id) {
        avoidMeatRepository.deleteById(id);
    }

    @Override
    public List<AvoidMeatDTO> getAllAvoidMeats() {
        return avoidMeatRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AvoidMeatDTO> getAvoidMeatById(Long id) {
        Optional<AvoidMeatEntity> optionalEntity = avoidMeatRepository.findById(id);
        return optionalEntity.map(this::mapToDTO);
    }

    @Override
    public List<AvoidMeatDTO> getAvoidMeatOptionsByUserId(Long userId) {
        List<AvoidMeatEntity> avoidMeats = lunchRegistrationRepository.findAvoidMeatsByUserId(userId);
        return avoidMeats.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvoidMeatDTO> getAvoidMeatOptionsByUserIdAndMonthId(Long userId, Long monthId) {
        List<AvoidMeatEntity> avoidMeats = lunchRegistrationRepository.findAvoidMeatsByUserIdAndMonthId(userId, monthId);
        return avoidMeats.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private AvoidMeatDTO mapToDTO(AvoidMeatEntity entity) {
        AvoidMeatDTO dto = new AvoidMeatDTO();
        dto.setId(entity.getId());
        dto.setMeatName(entity.getMeat_name());
        return dto;
    }
}
