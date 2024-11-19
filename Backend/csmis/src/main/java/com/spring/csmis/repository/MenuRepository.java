package com.spring.csmis.repository;

import com.spring.csmis.entity.MealTypeEntity;
import com.spring.csmis.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    // Additional query methods for menu retrieval

    List<MenuEntity> findByIsDeletedFalse();
    List<MenuEntity>findByIsDeletedTrue();
}
