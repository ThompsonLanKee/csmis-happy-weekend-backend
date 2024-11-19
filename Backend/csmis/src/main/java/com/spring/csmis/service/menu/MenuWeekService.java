package com.spring.csmis.service.menu;

import com.spring.csmis.dto.menu.MenuWeekDTO;
import com.spring.csmis.entity.MenuEntity;
import com.spring.csmis.entity.MenuWeekEntity;

import java.time.LocalDate;
import java.util.List;

public interface MenuWeekService {

    MenuWeekDTO addMenuWeek(MenuWeekDTO menuWeekDTO);
    List<MenuWeekDTO> getAllMenusWeek();
    List<MenuWeekDTO> getAllRemovedMenusWeek();
    MenuWeekDTO getMenuWeekById(Long id);
    MenuWeekDTO updateMenuWeek(Long id, MenuWeekDTO menuWeekDTO); // New method for updating menu
    void softDeleteMenuWeek(Long id);
    void hardDeleteMenuWeek(Long id);
    void restoreMenuWeek(Long id);

    List<MenuEntity> getMenusByMenuWeekFromDateAndToDate(LocalDate fromDate, LocalDate toDate);

    List<MenuWeekEntity> getMenuPricesForMonth(int month, int year);
}
