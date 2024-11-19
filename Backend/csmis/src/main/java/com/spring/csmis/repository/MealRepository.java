package com.spring.csmis.repository;

import com.spring.csmis.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<MealEntity, Long> {

    // Find all non-deleted menus
    List<MealEntity> findByIsDeletedFalse();
    List<MealEntity>findByIsDeletedTrue();
    // Find a menu by ID, even if it's soft-deleted

    Optional<MealEntity> findById(Long id);





    @Query("SELECT m FROM MealEntity m WHERE m.category = ?1")
    List <MealEntity> findByCategory(Long id);

}
