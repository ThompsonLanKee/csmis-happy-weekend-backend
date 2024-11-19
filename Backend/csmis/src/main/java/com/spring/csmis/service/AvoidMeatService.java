package com.spring.csmis.service;

import com.spring.csmis.dto.AvoidMeatDTO;
import java.util.List;
import java.util.Optional;

public interface AvoidMeatService {
    AvoidMeatDTO insertAvoidMeat(AvoidMeatDTO avoidMeatDTO);
    AvoidMeatDTO editAvoidMeat(Long id, AvoidMeatDTO avoidMeatDTO);
    void softDeleteAvoidMeat(Long id);
    void hardDeleteAvoidMeat(Long id);
    List<AvoidMeatDTO> getAllAvoidMeats();
    Optional<AvoidMeatDTO> getAvoidMeatById(Long id);

    List<AvoidMeatDTO> getAvoidMeatOptionsByUserId(Long userId);

    List<AvoidMeatDTO> getAvoidMeatOptionsByUserIdAndMonthId(Long userId , Long month);
}
