package com.spring.csmis.repository;

import com.spring.csmis.entity.CateringCentreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateringCentreRepository extends JpaRepository<CateringCentreEntity, Long> {

}