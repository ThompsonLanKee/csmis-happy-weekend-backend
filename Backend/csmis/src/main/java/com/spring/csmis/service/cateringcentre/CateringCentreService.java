package com.spring.csmis.service.cateringcentre;

import com.spring.csmis.dto.CateringCentreDTO;

import java.util.List;

public interface CateringCentreService {
    CateringCentreDTO addCentre(CateringCentreDTO cateringCentreDTO);
    CateringCentreDTO updateCentre(Long id, CateringCentreDTO cateringCentreDTO);
    CateringCentreDTO getCentreById(Long id);
    List<CateringCentreDTO> getAllCentres();
    void softDeleteCentre(Long id);
    void hardDeleteCentre(Long id);
    void restoreCentre(Long id);
    List<CateringCentreDTO> getAllRemovedCentres();
}
