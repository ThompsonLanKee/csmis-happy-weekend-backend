package com.spring.csmis.repository;

import com.spring.csmis.entity.MenuEntity;
import com.spring.csmis.entity.MenuWeekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MenuWeekRepository extends JpaRepository<MenuWeekEntity, Long> {

    List<MenuWeekEntity> findByIsDeletedFalse();
    List<MenuWeekEntity>findByIsDeletedTrue();

    @Query("SELECT m FROM MenuEntity m JOIN m.menuWeeks mw WHERE mw.fromDate >= :fromDate AND mw.toDate <= :toDate AND mw.isDeleted = false")
    List<MenuEntity> findMenusByMenuWeekDateRange(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);


        @Query("SELECT m FROM MenuWeekEntity m WHERE " +
                "(m.fromDate <= :endDate AND m.toDate >= :startDate)")
        List<MenuWeekEntity> findMenuWeeksForMonth(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);



        @Query("SELECT mw FROM MenuWeekEntity mw WHERE mw.fromDate <= :endDate AND mw.toDate >= :startDate AND mw.isDeleted = false")
     List<MenuWeekEntity> findByDateRange(LocalDate startDate, LocalDate endDate);
}
