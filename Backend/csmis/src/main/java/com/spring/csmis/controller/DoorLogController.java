package com.spring.csmis.controller;

import com.spring.csmis.entity.DoorLogEntity;
import com.spring.csmis.service.DoorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/doorlogs")
public class DoorLogController {

    @Autowired
    private DoorLogService doorLogService;

    @GetMapping
    public List<DoorLogEntity> getAllDoorLogs() {
        return doorLogService.getAllDoorLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoorLogEntity> getDoorLogById(@PathVariable Long id) {
        Optional<DoorLogEntity> doorLog = doorLogService.getDoorLogById(id);
        return doorLog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DoorLogEntity createDoorLog(@RequestBody DoorLogEntity doorLogEntity) {
        return doorLogService.saveDoorLog(doorLogEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoorLogEntity> updateDoorLog(@PathVariable Long id, @RequestBody DoorLogEntity updatedDoorLog) {
        return doorLogService.getDoorLogById(id).map(existingLog -> {
            existingLog.setDoorLogNo(updatedDoorLog.getDoorLogNo());
            existingLog.setEmployee(updatedDoorLog.getEmployee());
            DoorLogEntity savedLog = doorLogService.saveDoorLog(existingLog);
            return ResponseEntity.ok(savedLog);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoorLog(@PathVariable Long id) {
        if (doorLogService.getDoorLogById(id).isPresent()) {
            doorLogService.softDeleteDoorLogById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
