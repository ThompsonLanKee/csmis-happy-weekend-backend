package com.spring.csmis.repository.feedback;

import com.spring.csmis.entity.FeedBackTypeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedBackTypeRepository extends JpaRepository<FeedBackTypeEntity, Long> {
    List<FeedBackTypeEntity> findByIsDeletedFalse(); // Retrieve non-deleted meal types
    List<FeedBackTypeEntity> findByIsDeletedTrue();  // Retrieve deleted meal types

    @Query("SELECT f FROM FeedBackTypeEntity f JOIN f.categories c WHERE c.id = :categoryId AND f.isDeleted = false")
    List<FeedBackTypeEntity> findFeedBackTypesByCategoryIdAndIsDeletedFalse(@Param("categoryId") Long categoryId);
}
