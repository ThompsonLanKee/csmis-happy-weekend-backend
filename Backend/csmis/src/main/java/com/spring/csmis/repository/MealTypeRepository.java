package com.spring.csmis.repository;

import com.spring.csmis.entity.MealTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealTypeRepository extends JpaRepository<MealTypeEntity, Long> {
    List<MealTypeEntity> findByIsDeletedFalse(); // Retrieve non-deleted meal types
    List<MealTypeEntity> findByIsDeletedTrue();  // Retrieve deleted meal types

    @Query("SELECT m FROM MealTypeEntity m JOIN m.categories c WHERE c.id = :categoryId AND m.isDeleted = false")
    List<MealTypeEntity> findMealTypesByCategoryIdAndIsDeletedFalse(@Param("categoryId") Long categoryId);
}
