package com.spring.csmis.service;

import com.spring.csmis.dto.LunchRegistrationDTO;
import com.spring.csmis.entity.AvoidMeatEntity;
import com.spring.csmis.entity.LunchRegistrationEntity;
import com.spring.csmis.entity.MenuWeekEntity;
import com.spring.csmis.entity.MonthEntity;
import com.spring.csmis.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LunchRegistrationServiceImpl implements LunchRegistrationService {

    @Autowired
    private LunchRegistrationRepository lunchRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private AvoidMeatRepository avoidMeatRepository;

    @Autowired
    private MenuWeekRepository menuWeekRepository;

    private LunchRegistrationEntity convertToEntity(LunchRegistrationDTO dto) {
        LunchRegistrationEntity entity = new LunchRegistrationEntity();
        entity.setId(dto.getId());
        entity.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        entity.setMonth(monthRepository.findById(dto.getMonthId()).orElse(null));
        entity.setDailyLunchStatus(dto.getDailyLunchStatus());
       // entity.setModificationDeadline(dto.getModificationDeadline());

        Set<AvoidMeatEntity> avoidMeats = dto.getAvoidMeatIds().stream()
                .map(id -> avoidMeatRepository.findById(id).orElse(null))
                .collect(Collectors.toSet());
        entity.setAvoidMeats(avoidMeats);

        return entity;
    }

    private LunchRegistrationDTO convertToDTO(LunchRegistrationEntity entity) {
        LunchRegistrationDTO dto = new LunchRegistrationDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setMonthId(entity.getMonth().getId());
        dto.setDailyLunchStatus(entity.getDailyLunchStatus());
       // dto.setModificationDeadline(entity.getModificationDeadline());

        Set<Long> avoidMeatIds = entity.getAvoidMeats().stream()
                .map(avoidMeat -> avoidMeat.getId().longValue()) // Convert Integer to Long
                .collect(Collectors.toSet());
        dto.setAvoidMeatIds(avoidMeatIds);

        return dto;
    }

    @Override
    public LunchRegistrationDTO insertLunchRegistration(LunchRegistrationDTO dto) {
        LunchRegistrationEntity entity = convertToEntity(dto);
        LunchRegistrationEntity savedEntity = lunchRegistrationRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public LunchRegistrationDTO updateLunchRegistration(LunchRegistrationDTO dto) {
        Optional<LunchRegistrationEntity> existingEntityOpt = lunchRegistrationRepository.findById(dto.getId());
        if (existingEntityOpt.isPresent()) {
            LunchRegistrationEntity updatedEntity = convertToEntity(dto);
            LunchRegistrationEntity savedEntity = lunchRegistrationRepository.save(updatedEntity);
            return convertToDTO(savedEntity);
        } else {
            throw new RuntimeException("Lunch Registration not found with ID: " + dto.getId());
        }
    }

    @Override
    public void softDeleteLunchRegistration(Long id) {
        Optional<LunchRegistrationEntity> registration = lunchRegistrationRepository.findById(id);
        if (registration.isPresent()) {
            LunchRegistrationEntity updatedRegistration = registration.get();
            // Soft delete logic here if needed
            lunchRegistrationRepository.save(updatedRegistration);
        } else {
            throw new RuntimeException("Lunch Registration not found with ID: " + id);
        }
    }

    @Override
    public void hardDeleteLunchRegistration(Long id) {
        lunchRegistrationRepository.deleteById(id);
    }

    @Override
    public List<LunchRegistrationDTO> getAllLunchRegistrations() {
        List<LunchRegistrationEntity> entities = lunchRegistrationRepository.findAll();
        return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public LunchRegistrationDTO getLunchRegistrationById(Long id) {
        LunchRegistrationEntity entity = lunchRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lunch Registration not found with ID: " + id));
        return convertToDTO(entity);
    }

    @Override
    public Optional<LunchRegistrationDTO> findLunchRegistrationByUserIdAndMonthId(Long userId, Long monthId) {
        return lunchRegistrationRepository.findLunchRegistrationByUserIdAndMonthId(userId, monthId)
                .map(this::convertToDTO);
    }

    @Override
    public double calculateUserLunchCost(Long userId, Long monthId) {
        double totalCost = 0;

        // Retrieve the month and menu weeks for that month
        MonthEntity monthEntity = monthRepository.findById(monthId)
                .orElseThrow(() -> new RuntimeException("Month not found"));

        Month monthEnum = Month.valueOf(monthEntity.getName().toUpperCase());
        LocalDate startDate = LocalDate.of(monthEntity.getYear(), monthEnum, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // Get all menu weeks overlapping this month
        List<MenuWeekEntity> menuWeeks = menuWeekRepository.findMenuWeeksForMonth(startDate, endDate);

        // Retrieve the user’s lunch registration details for this month
        LunchRegistrationEntity registration = lunchRegistrationRepository.findLunchRegistrationByUserIdAndMonthId(userId, monthId)
                .orElseThrow(() -> new RuntimeException("Lunch Registration not found"));

        Map<Integer, Boolean> dailyLunchStatus = registration.getDailyLunchStatus();

        // Loop over each day of the month and check if user is registered for lunch
        for (int day = 1; day <= endDate.getDayOfMonth(); day++) {
            if (Boolean.TRUE.equals(dailyLunchStatus.get(day))) {
                LocalDate currentDate = LocalDate.of(monthEntity.getYear(), monthEnum, day);

                // Find the applicable MenuWeek for this day
                for (MenuWeekEntity menuWeek : menuWeeks) {
                    if (!currentDate.isBefore(menuWeek.getFromDate()) &&
                            !currentDate.isAfter(menuWeek.getToDate())) {
                        totalCost += menuWeek.getPrice();
                        break;
                    }
                }
            }
        }

        return totalCost;
    }

}
