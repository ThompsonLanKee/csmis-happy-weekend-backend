package com.spring.csmis.repository;

import com.spring.csmis.entity.EmployeeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findById(Long id);

    @Query("select e from EmployeeEntity e where e.isDeleted <> true")
    Page<EmployeeEntity> showAllUsers(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update EmployeeEntity e set e.isDeleted = true where e.id = :id")
    void deleteUser(Integer id);

    Page<EmployeeEntity> findAll(Pageable pageable);

    EmployeeEntity findByStaffID(String staffID);

    @Modifying
    @Transactional
    @Query("update EmployeeEntity e set e.isDeleted=true where e.id=:id")
    public void deleteEmployee(Long id);
    @Query("select e from EmployeeEntity e where e.isDeleted<>true")
    @EntityGraph(attributePaths = {"division", "department", "team"})
    public List<EmployeeEntity> showAllEmployees();



}
