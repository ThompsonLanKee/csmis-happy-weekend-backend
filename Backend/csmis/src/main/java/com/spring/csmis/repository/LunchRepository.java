package com.spring.csmis.repository;

import com.spring.csmis.entity.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LunchRepository extends JpaRepository<HolidayEntity, Long> {



}
