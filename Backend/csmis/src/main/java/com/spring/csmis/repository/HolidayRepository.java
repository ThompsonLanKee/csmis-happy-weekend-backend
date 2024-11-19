package com.spring.csmis.repository;

import com.spring.csmis.entity.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
    List<HolidayEntity> findByMonthAndYear(int month, int year);
}
