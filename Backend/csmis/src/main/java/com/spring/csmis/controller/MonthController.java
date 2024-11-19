package com.spring.csmis.controller;

import com.spring.csmis.entity.MonthEntity;
import com.spring.csmis.service.MonthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/months")
public class MonthController {
    private final MonthService monthService;

    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    @GetMapping("/getMonthId")
    public ResponseEntity<Long> getMonthId(@RequestParam String name, @RequestParam Integer year) {
        MonthEntity month = monthService.getMonthByNameAndYear(name, year);
        return ResponseEntity.ok(month.getId());
    }
}
