package com.spring.csmis.repository.feedback;

import com.spring.csmis.entity.FeedBackCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackCategoryRepository extends JpaRepository<FeedBackCategoryEntity, Long> {
    List<FeedBackCategoryEntity> findByIsDeletedFalse();
    List<FeedBackCategoryEntity> findByIsDeletedTrue();
}
