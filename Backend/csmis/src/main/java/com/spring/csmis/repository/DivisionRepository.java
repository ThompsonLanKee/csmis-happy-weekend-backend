package com.spring.csmis.repository;


import com.spring.csmis.entity.DivisionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DivisionRepository extends JpaRepository<DivisionEntity, Integer> {
    List<DivisionEntity> findByName(String name);

    Optional<DivisionEntity> findDivisionByName(String name);

    @Query("select division from DivisionEntity division where division.isDeleted<>true")
    public List<DivisionEntity> showAllDivisions();

    @Modifying
    @Transactional
    @Query("update DivisionEntity division set division.isDeleted=true where division.id=:id")
    public void deleteDivision(Integer id);
}
