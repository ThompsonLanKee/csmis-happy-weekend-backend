package com.spring.csmis.controller;

import com.spring.csmis.dto.feedback.FeedBackTypeDTO;
import com.spring.csmis.dto.meal.MealTypeDTO;
import com.spring.csmis.entity.FeedBackTypeEntity;
import com.spring.csmis.service.feedback.FeedBackTypeService;
import com.spring.csmis.service.meal.MealTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/feedbacktype")
public class FeedBackTypeController {

    @Autowired
    private FeedBackTypeService feedBackTypeService;

    @PostMapping("/add")
    public ResponseEntity<FeedBackTypeDTO> createFeedBackType(@RequestBody FeedBackTypeDTO feedBackTypeDTO) {
        FeedBackTypeDTO createdFeedBackType = feedBackTypeService.addType(feedBackTypeDTO);
        return ResponseEntity.ok(createdFeedBackType);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FeedBackTypeDTO> updateFeedBackType(@PathVariable Long id, @RequestBody FeedBackTypeDTO feedBackTypeDTO) {
        FeedBackTypeDTO updatedFeedBackType = feedBackTypeService.updateType(id, feedBackTypeDTO);
        return ResponseEntity.ok(updatedFeedBackType);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<FeedBackTypeDTO> getFeedBackTypeById(@PathVariable Long id) {
        FeedBackTypeDTO feedBackTypeDTO = feedBackTypeService.getTypeById(id);
        return ResponseEntity.ok(feedBackTypeDTO);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<FeedBackTypeDTO>> getAllFeedBackTypes() {
        List<FeedBackTypeDTO> feedBackTypes = feedBackTypeService.getAllTypes();
        return ResponseEntity.ok(feedBackTypes);
    }

    @GetMapping("/getallremoved")
    public ResponseEntity<List<FeedBackTypeDTO>> getAllRemovedFeedBackTypes() {
        List<FeedBackTypeDTO> removedFeedBackTypes = feedBackTypeService.getAllRemovedTypes();
        return ResponseEntity.ok(removedFeedBackTypes);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteFeedBackType(@PathVariable Long id) {
        feedBackTypeService.softDeleteType(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteFeedBackType(@PathVariable Long id) {
        feedBackTypeService.hardDeleteType(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreFeedBackType(@PathVariable Long id) {
        feedBackTypeService.restoreType(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FeedBackTypeDTO>> getFeedBackTypesByCategoryIdAndIsDeleteFalse(@PathVariable Long categoryId) {
        List<FeedBackTypeDTO> feedBackTypes = feedBackTypeService.getFeedBackTypesByCategoryIdAndIsDeleteFalse(categoryId);
        if (feedBackTypes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no meal types found
        }
        return ResponseEntity.ok(feedBackTypes); // Return 200 OK with the list of meal types
    }
}
