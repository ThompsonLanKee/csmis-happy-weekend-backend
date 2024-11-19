package com.spring.csmis.controller;

import com.spring.csmis.dto.feedback.FeedBackCategoryDTO;
import com.spring.csmis.service.feedback.FeedBackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/feedbackcategory")
public class FeedBackCategoryController {

    @Autowired
    private FeedBackCategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<FeedBackCategoryDTO> addCategory(@RequestBody FeedBackCategoryDTO categoryDTO) {
        FeedBackCategoryDTO createdCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FeedBackCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody FeedBackCategoryDTO categoryDTO) {
        FeedBackCategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<FeedBackCategoryDTO> getCategoryById(@PathVariable Long id) {
        FeedBackCategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<FeedBackCategoryDTO>> getAllCategories() {
        List<FeedBackCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
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

    @PutMapping("/restore/{id}")
    public ResponseEntity<Void> restoreCategory(@PathVariable Long id) {
        categoryService.restoreCategory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getallremoved")
    public ResponseEntity<List<FeedBackCategoryDTO>> getAllRemovedCategories() {
        List<FeedBackCategoryDTO> categories = categoryService.getAllRemovedCategories();
        return ResponseEntity.ok(categories);
    }
}
