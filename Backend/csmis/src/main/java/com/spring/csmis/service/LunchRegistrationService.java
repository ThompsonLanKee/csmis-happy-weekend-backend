// LunchRegistrationService.java
package com.spring.csmis.service;

import com.spring.csmis.dto.LunchRegistrationDTO;
import com.spring.csmis.entity.LunchRegistrationEntity;

import java.util.List;
import java.util.Optional;

public interface LunchRegistrationService {
    LunchRegistrationDTO insertLunchRegistration(LunchRegistrationDTO lunchRegistration);
    LunchRegistrationDTO updateLunchRegistration(LunchRegistrationDTO lunchRegistration);
    void softDeleteLunchRegistration(Long id);
    void hardDeleteLunchRegistration(Long id);
    List<LunchRegistrationDTO> getAllLunchRegistrations();
    LunchRegistrationDTO getLunchRegistrationById(Long id);

    Optional<LunchRegistrationDTO> findLunchRegistrationByUserIdAndMonthId(Long userId, Long monthId);

    double calculateUserLunchCost(Long userId, Long monthId);
}
