package com.spring.csmis.repository;

import com.spring.csmis.entity.MealCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealCategoryRepository extends JpaRepository<MealCategoryEntity, Long> {
    List<MealCategoryEntity> findByIsDeletedFalse(); // Only get non-deleted categories

    List<MealCategoryEntity> findByIsDeletedTrue();


}
