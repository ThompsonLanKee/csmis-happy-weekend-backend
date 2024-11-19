package com.spring.csmis.repository.feedback;

import com.spring.csmis.entity.FeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Long> {
    List<FeedBackEntity> findByUserId(Long userId);
    List<FeedBackEntity> findByMenuId(Long menuId);

    @Query("SELECT f FROM FeedBackEntity f WHERE f.menu.id = :menuId AND f.isDeleted = false")
    List<FeedBackEntity> findByMenuIdAndIsDeletedFalse(@Param("menuId") Long menuId);

    @Query("SELECT f FROM FeedBackEntity f WHERE f.user.id = :userId AND f.isDeleted = false")
    List<FeedBackEntity> findByUserIdAndIsDeletedFalse(@Param("userId") Long userId);
}
