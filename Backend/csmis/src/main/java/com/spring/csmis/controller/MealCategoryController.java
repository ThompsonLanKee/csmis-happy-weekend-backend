package com.spring.csmis.controller;

import com.spring.csmis.dto.meal.MealCategoryDTO;

import com.spring.csmis.service.meal.MealCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class MealCategoryController {

    @Autowired
    private MealCategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<MealCategoryDTO> createCategory(@RequestBody MealCategoryDTO categoryDTO) {
        MealCategoryDTO createdCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MealCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody MealCategoryDTO categoryDTO) {
        MealCategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<MealCategoryDTO> getCategoryById(@PathVariable Long id) {
        MealCategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MealCategoryDTO>> getAllCategories() {
        List<MealCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getallremoved")
    public ResponseEntity<List<MealCategoryDTO>> getAllRemovedMenus() {
        List<MealCategoryDTO> removedCategories = categoryService.getAllRemovedCategories();
        return ResponseEntity.ok(removedCategories);
    }


    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteCategory(@PathVariable Long id) {
        categoryService.softDeleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteCategory(@PathVariable Long id) {
        categoryService.hardDeleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreCategory(@PathVariable Long id) {
        categoryService.restoreCategory(id);
        return ResponseEntity.ok().build();
    }
}
