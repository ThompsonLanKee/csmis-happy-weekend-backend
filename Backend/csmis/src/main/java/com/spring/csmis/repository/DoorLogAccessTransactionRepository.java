package com.spring.csmis.repository;

import com.spring.csmis.entity.DoorLogAccessTransactionEntity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoorLogAccessTransactionRepository extends JpaRepository<DoorLogAccessTransactionEntity, Integer> {
//    Optional<DoorLogAccessTransactionRepository> findById(int id);
//
//    @Query("select d from DoorLogEntity d where d.isDeleted<>true")
//    public List<DoorLogAccessTransactionRepository> showAllDoorLogs();
//
//    @Modifying
//    @Transactional
//    @Query("update DoorLogEntity d set d.isDeleted=true where d.id=:id")
//    public void deleteDoorLog(Integer id);
//
////    Page<EmployeeEntity> findAll(Pageable pageable);
}
