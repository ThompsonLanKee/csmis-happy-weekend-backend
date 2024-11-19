package com.spring.csmis.service.menu;

import com.spring.csmis.dto.menu.MenuDTO;
import com.spring.csmis.entity.MenuWeekEntity;

import java.util.List;

public interface MenuService {
    MenuDTO addMenu(MenuDTO menuDTO);
    List<MenuDTO> getAllMenus();
    List<MenuDTO> getAllRemovedMenus();
    MenuDTO getMenuById(Long id);
    MenuDTO updateMenu(Long id, MenuDTO menuDTO); // New method for updating menu
    void softDeleteMenu(Long id);
    void hardDeleteMenu(Long id);
    void restoreMenu(Long id);  // New method for restoring soft deleted menu


}
