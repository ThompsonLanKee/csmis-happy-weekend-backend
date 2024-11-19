package com.spring.csmis.service.cateringcentre;

import com.spring.csmis.dto.CateringCentreDTO;
import com.spring.csmis.entity.CateringCentreEntity;
import com.spring.csmis.repository.CateringCentreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CateringCentreServiceImpl implements CateringCentreService {

    @Autowired
    private CateringCentreRepository cateringCentreRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CateringCentreDTO addCentre(CateringCentreDTO cateringCentreDTO) {
        CateringCentreEntity centreEntity = modelMapper.map(cateringCentreDTO, CateringCentreEntity.class);
        CateringCentreEntity savedCentre = cateringCentreRepository.save(centreEntity);
        return modelMapper.map(savedCentre, CateringCentreDTO.class);
    }

    @Override
    public CateringCentreDTO updateCentre(Long id, CateringCentreDTO cateringCentreDTO) {
        CateringCentreEntity existingCentre = cateringCentreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catering Centre not found"));

        existingCentre.setCentreName(cateringCentreDTO.getCentreName());
        existingCentre.setCentreAddress(cateringCentreDTO.getCentreAddress());
        existingCentre.setCentreType(cateringCentreDTO.getCentreType());
        existingCentre.setCentrePhone(cateringCentreDTO.getCentrePhone());

        CateringCentreEntity updatedCentre = cateringCentreRepository.save(existingCentre);
        return modelMapper.map(updatedCentre, CateringCentreDTO.class);
    }

    @Override
    public CateringCentreDTO getCentreById(Long id) {
        CateringCentreEntity centre = cateringCentreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catering Centre not found"));
        return modelMapper.map(centre, CateringCentreDTO.class);
    }

    @Override
    public List<CateringCentreDTO> getAllCentres() {
        List<CateringCentreEntity> centres = cateringCentreRepository.findAll();
        return centres.stream()
                .map(centre -> modelMapper.map(centre, CateringCentreDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteCentre(Long id) {
        CateringCentreEntity centre = cateringCentreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catering Centre not found"));

        centre.setDeleted(true); // Soft delete by setting isDeleted to true
        cateringCentreRepository.save(centre);
    }

    @Override
    public void hardDeleteCentre(Long id) {
        CateringCentreEntity centre = cateringCentreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catering Centre not found"));

        cateringCentreRepository.delete(centre); // Hard delete from the database
    }

    @Override
    public void restoreCentre(Long id) {
        CateringCentreEntity centre = cateringCentreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catering Centre not found"));

        centre.setDeleted(false); // Restore by setting isDeleted to false
        cateringCentreRepository.save(centre);
    }

    @Override
    public List<CateringCentreDTO> getAllRemovedCentres() {
        List<CateringCentreEntity> removedCentres = cateringCentreRepository.findAll().stream()
                .filter(CateringCentreEntity::isDeleted) // Get only soft-deleted centres
                .collect(Collectors.toList());

        return removedCentres.stream()
                .map(centre -> modelMapper.map(centre, CateringCentreDTO.class))
                .collect(Collectors.toList());
    }
}
