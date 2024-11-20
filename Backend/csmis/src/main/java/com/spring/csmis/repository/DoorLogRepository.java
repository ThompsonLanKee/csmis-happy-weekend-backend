package com.spring.csmis.repository;

import com.spring.csmis.entity.DoorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;


public interface DoorLogRepository  extends JpaRepository<DoorLogEntity, Long> {
    DoorLogEntity findByDoorLogNo(String doorLogNo);

    List<DoorLogEntity> findByDeletedFalse();


    //List<DoorLogEntity> findByDoorLogNo(String doorLogNo);
    List<DoorLogEntity> findAllByDoorLogNo(String doorLogNo);
}
