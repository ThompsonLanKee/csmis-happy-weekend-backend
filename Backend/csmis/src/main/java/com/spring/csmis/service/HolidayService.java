package com.spring.csmis.service;

import com.spring.csmis.entity.HolidayEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HolidayService {
    void saveHolidays(MultipartFile file); // Method to handle Excel file import

    List<HolidayEntity> getAllHolidays();

    List<HolidayEntity> findByMonthAndYear(int month, int year);

}
