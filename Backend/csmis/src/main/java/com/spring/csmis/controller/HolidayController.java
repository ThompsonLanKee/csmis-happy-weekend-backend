package com.spring.csmis.controller;

import com.spring.csmis.entity.HolidayEntity;
import com.spring.csmis.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping("/getallholidaysbyyearandmonth")
    public List<HolidayEntity> getHolidays(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return holidayService.findByMonthAndYear(month, year);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadHolidays(@RequestParam("file") MultipartFile file) {
        holidayService.saveHolidays(file);
        return ResponseEntity.ok("Holidays uploaded successfully");
    }





    @GetMapping("/getall")
    public List<HolidayEntity> getAllHolidays() {
        return holidayService.getAllHolidays();
    }
}
