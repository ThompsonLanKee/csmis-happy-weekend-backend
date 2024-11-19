package com.spring.csmis.controller;

import com.spring.csmis.dto.AvoidMeatDTO;
import com.spring.csmis.entity.AvoidMeatEntity;
import com.spring.csmis.service.AvoidMeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avoidmeat")
public class AvoidMeatController {

    @Autowired
    private AvoidMeatService avoidMeatService;

    @PostMapping("/create")
    public ResponseEntity<AvoidMeatDTO> createAvoidMeat(@RequestBody AvoidMeatDTO avoidMeatDTO) {
        AvoidMeatDTO createdAvoidMeat = avoidMeatService.insertAvoidMeat(avoidMeatDTO);
        return ResponseEntity.ok(createdAvoidMeat);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AvoidMeatDTO> editAvoidMeat(@PathVariable Long id, @RequestBody AvoidMeatDTO avoidMeatDTO) {
        AvoidMeatDTO updatedAvoidMeat = avoidMeatService.editAvoidMeat(id, avoidMeatDTO);
        return updatedAvoidMeat != null ? ResponseEntity.ok(updatedAvoidMeat) : ResponseEntity.notFound().build();
    }

    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<Void> softDeleteAvoidMeat(@PathVariable Long id) {
        avoidMeatService.softDeleteAvoidMeat(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<Void> hardDeleteAvoidMeat(@PathVariable Long id) {
        avoidMeatService.hardDeleteAvoidMeat(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AvoidMeatDTO>> getAllAvoidMeats() {
        List<AvoidMeatDTO> avoidMeats = avoidMeatService.getAllAvoidMeats();
        return ResponseEntity.ok(avoidMeats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvoidMeatDTO> getAvoidMeatById(@PathVariable Long id) {
        Optional<AvoidMeatDTO> avoidMeat = avoidMeatService.getAvoidMeatById(id);
        return avoidMeat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<AvoidMeatDTO> getAvoidMeatsByUserId(@PathVariable Long userId) {
        return avoidMeatService.getAvoidMeatOptionsByUserId(userId);
    }

    @GetMapping("/user/{userId}/month/{monthId}")
    public List<AvoidMeatDTO> getAvoidMeatsByUserIdAndMonthId(@PathVariable Long userId ,@PathVariable Long monthId ) {
        return avoidMeatService.getAvoidMeatOptionsByUserIdAndMonthId(userId , monthId);
    }
}
