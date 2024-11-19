// MonthRepository.java
package com.spring.csmis.repository;

import com.spring.csmis.entity.MonthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthRepository extends JpaRepository<MonthEntity, Long> {
    Optional<MonthEntity> findByNameAndYear(String name, Integer year);
}
