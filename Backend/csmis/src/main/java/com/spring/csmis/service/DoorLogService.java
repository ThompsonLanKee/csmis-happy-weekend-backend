package com.spring.csmis.service;

import com.spring.csmis.entity.DoorLogEntity;
import com.spring.csmis.repository.DoorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoorLogService {

    @Autowired
    private DoorLogRepository doorLogRepository;

    public List<DoorLogEntity> getAllDoorLogs() {
        return doorLogRepository.findByDeletedFalse();
    }

    public Optional<DoorLogEntity> getDoorLogById(Long id) {
        return doorLogRepository.findById(id).filter(doorLog -> !doorLog.isDeleted());
    }

    public DoorLogEntity saveDoorLog(DoorLogEntity doorLogEntity) {
        return doorLogRepository.save(doorLogEntity);
    }

    public void softDeleteDoorLogById(Long id) {
        doorLogRepository.findById(id).ifPresent(doorLog -> {
            doorLog.setDeleted(true);
            doorLogRepository.save(doorLog);
        });
    }
}
