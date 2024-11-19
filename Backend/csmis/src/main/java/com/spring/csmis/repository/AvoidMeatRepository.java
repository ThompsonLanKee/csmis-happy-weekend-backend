package com.spring.csmis.repository;

import com.spring.csmis.entity.AvoidMeatEntity;
import com.spring.csmis.entity.LunchRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvoidMeatRepository extends JpaRepository<AvoidMeatEntity, Long> {
    Optional<AvoidMeatEntity> findById(Long id);

}
