// LunchRegistrationRepository.java
package com.spring.csmis.repository;

import com.spring.csmis.entity.AvoidMeatEntity;
import com.spring.csmis.entity.LunchRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LunchRegistrationRepository extends JpaRepository<LunchRegistrationEntity, Long> {
    @Query("SELECT lr, m.name AS monthName, m.year AS monthYear " +
            "FROM LunchRegistrationEntity lr " +
            "JOIN lr.month m " +
            "WHERE lr.user.id = :userId " +
            "AND lr.month.id = :monthId")
    Optional<LunchRegistrationEntity> findLunchRegistrationByUserIdAndMonthId(
            @Param("userId") Long userId,
            @Param("monthId") Long monthId);


    // Join query to get avoided meats by user ID
    @Query("SELECT ame FROM LunchRegistrationEntity lre " +
            "JOIN lre.avoidMeats ame " +
            "WHERE lre.user.id = :userId")
    List<AvoidMeatEntity> findAvoidMeatsByUserId(@Param("userId") Long userId);

    @Query("SELECT ame FROM LunchRegistrationEntity lre " +
            "JOIN lre.avoidMeats ame " +
            "WHERE lre.user.id = :userId " +
            "AND lre.month.id = :monthId")
    List<AvoidMeatEntity> findAvoidMeatsByUserIdAndMonthId(@Param("userId") Long userId,
                                                           @Param("monthId") Long monthId);

}
