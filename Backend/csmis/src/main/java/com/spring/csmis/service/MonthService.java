package com.spring.csmis.service;

import com.spring.csmis.entity.MonthEntity;
import com.spring.csmis.repository.MonthRepository;
import org.springframework.stereotype.Service;

@Service
public class MonthService {
    private final MonthRepository monthRepository;

    public MonthService(MonthRepository monthRepository) {
        this.monthRepository = monthRepository;
    }

    public MonthEntity getMonthByNameAndYear(String name, Integer year) {
        return monthRepository.findByNameAndYear(name, year)
                .orElseThrow(() -> new RuntimeException("Month not found"));
    }
}