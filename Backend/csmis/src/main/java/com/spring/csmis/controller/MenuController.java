package com.spring.csmis.controller;

import com.spring.csmis.dto.menu.MenuDTO;
import com.spring.csmis.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/add")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        MenuDTO createdMenu = menuService.addMenu(menuDTO);
        return ResponseEntity.ok(createdMenu);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        MenuDTO updatedMenu = menuService.updateMenu(id, menuDTO);
        return ResponseEntity.ok(updatedMenu);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long id) {
        MenuDTO menuDTO = menuService.getMenuById(id);
        return ResponseEntity.ok(menuDTO);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        List<MenuDTO> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/getallremoved")
    public ResponseEntity<List<MenuDTO>> getAllRemovedMenus() {
        List<MenuDTO> removedMenus = menuService.getAllRemovedMenus();
        return ResponseEntity.ok(removedMenus);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteMenu(@PathVariable Long id) {
        menuService.softDeleteMenu(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteMenu(@PathVariable Long id) {
        menuService.hardDeleteMenu(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreMenu(@PathVariable Long id) {
        menuService.restoreMenu(id);
        return ResponseEntity.ok().build();
    }
}
