// LunchRegistrationController.java
package com.spring.csmis.controller;

import com.spring.csmis.dto.LunchRegistrationDTO;
import com.spring.csmis.entity.LunchRegistrationEntity;
import com.spring.csmis.service.LunchRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lunchregistrations")
public class LunchRegistrationController {

    @Autowired
    private LunchRegistrationService lunchRegistrationService;

    @PostMapping("/add")
    public ResponseEntity<LunchRegistrationDTO> insertLunchRegistration(@RequestBody LunchRegistrationDTO lunchRegistration) {
        LunchRegistrationDTO createdRegistration = lunchRegistrationService.insertLunchRegistration(lunchRegistration);
        return ResponseEntity.ok(createdRegistration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LunchRegistrationDTO> updateLunchRegistration(@PathVariable Long id, @RequestBody LunchRegistrationDTO lunchRegistration) {
        lunchRegistration.setId(id);
        LunchRegistrationDTO updatedRegistration = lunchRegistrationService.updateLunchRegistration(lunchRegistration);
        return ResponseEntity.ok(updatedRegistration);
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<Void> softDeleteLunchRegistration(@PathVariable Long id) {
        lunchRegistrationService.softDeleteLunchRegistration(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteLunchRegistration(@PathVariable Long id) {
        lunchRegistrationService.hardDeleteLunchRegistration(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LunchRegistrationDTO>> getAllLunchRegistrations() {
        List<LunchRegistrationDTO> registrations = lunchRegistrationService.getAllLunchRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LunchRegistrationDTO> getLunchRegistrationById(@PathVariable Long id) {
        LunchRegistrationDTO registration = lunchRegistrationService.getLunchRegistrationById(id);
        return ResponseEntity.ok(registration);
    }

    @GetMapping("/user/{userId}/month/{monthId}")
    public ResponseEntity<LunchRegistrationDTO> getLunchRegistrationByUserAndMonth(
            @PathVariable Long userId,
            @PathVariable Long monthId) {

        Optional<LunchRegistrationDTO> lunchRegistration = lunchRegistrationService.findLunchRegistrationByUserIdAndMonthId(userId, monthId);

        return lunchRegistration
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/cost")
    public ResponseEntity<Double> getUserLunchCost(
            @RequestParam Long userId,
            @RequestParam Long monthId) {
        double totalCost = lunchRegistrationService.calculateUserLunchCost(userId, monthId);
        return ResponseEntity.ok(totalCost);
    }

}
