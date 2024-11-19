package com.spring.csmis.controller;

import com.spring.csmis.dto.PaymentDTO;
import com.spring.csmis.dto.menu.MenuWeekDTO;
import com.spring.csmis.entity.MenuEntity;
import com.spring.csmis.service.PaymentService;
import com.spring.csmis.service.menu.MenuWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Add new menu week
    @PostMapping("/add")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        PaymentDTO createdPayment = paymentService.addPayment(paymentDTO);
        return ResponseEntity.ok(createdPayment);
    }

//    // Edit existing menu week
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<MenuWeekDTO> updateMenuWeek(@PathVariable Long id, @RequestBody MenuWeekDTO menuWeekDTO) {
//        MenuWeekDTO updatedMenuWeek = menuWeekService.updateMenuWeek(id, menuWeekDTO);
//        return ResponseEntity.ok(updatedMenuWeek);
//    }
//
//    // Get a specific menu week by ID
//    @GetMapping("/getone/{id}")
//    public ResponseEntity<MenuWeekDTO> getMenuById(@PathVariable Long id) {
//        MenuWeekDTO menuWeekDTO = menuWeekService.getMenuWeekById(id);
//        return ResponseEntity.ok(menuWeekDTO);
//    }
//
//    // Get all menu weeks
//    @GetMapping("/getall")
//    public ResponseEntity<List<MenuWeekDTO>> getAllMenuWeeks() {
//        List<MenuWeekDTO> menusWeek = menuWeekService.getAllMenusWeek();
//        return ResponseEntity.ok(menusWeek);
//    }
//
//    // Get all removed (soft-deleted) menu weeks
//    @GetMapping("/getallremoved")
//    public ResponseEntity<List<MenuWeekDTO>> getAllRemovedMenuWeeks() {
//        List<MenuWeekDTO> removedMenusWeek = menuWeekService.getAllRemovedMenusWeek();
//        return ResponseEntity.ok(removedMenusWeek);
//    }
//
//    // Soft delete a menu week by ID
//    @DeleteMapping("/softdelete/{id}")
//    public ResponseEntity<Void> softDeleteMenuWeek(@PathVariable Long id) {
//        menuWeekService.softDeleteMenuWeek(id);
//        return ResponseEntity.ok().build();
//    }
//
//    // Hard delete a menu week by ID
//    @DeleteMapping("/harddelete/{id}")
//    public ResponseEntity<Void> hardDeleteMenuWeek(@PathVariable Long id) {
//        menuWeekService.hardDeleteMenuWeek(id);
//        return ResponseEntity.ok().build();
//    }
//
//    // Restore a soft-deleted menu week by ID
//    @PostMapping("/restore/{id}")
//    public ResponseEntity<Void> restoreMenuWeek(@PathVariable Long id) {
//        menuWeekService.restoreMenuWeek(id);
//        return ResponseEntity.ok().build();
//    }
//
//    // Get menus within a date range (fromDate to toDate)
//    @GetMapping("/getmenus/bymenuweek/fromdatetodate")
//    public ResponseEntity<List<MenuEntity>> getMenusByDateRange(
//            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
//            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
//
//        List<MenuEntity> menus = menuWeekService.getMenusByMenuWeekFromDateAndToDate(fromDate, toDate);
//
//        // Check if the list is empty and return appropriate response
//        if (menus.isEmpty()) {
//            return ResponseEntity.noContent().build(); // 204 No Content if no menus found
//        }
//
//        return ResponseEntity.ok(menus); // 200 OK with the list of menus
//    }
}
