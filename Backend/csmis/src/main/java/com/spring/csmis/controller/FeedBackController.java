package com.spring.csmis.controller;

import com.spring.csmis.dto.feedback.FeedBackDTO;
import com.spring.csmis.service.feedback.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/feedback")
public class FeedBackController {

    @Autowired
    private FeedBackService feedbackService;

    // Create new feedback
    @PostMapping("/add")
    public ResponseEntity<FeedBackDTO> addFeedback(@RequestBody FeedBackDTO feedbackDTO) {
        FeedBackDTO createdFeedback = feedbackService.addFeedBack(feedbackDTO);
        return ResponseEntity.ok(createdFeedback);
    }

    // Update existing feedback
    @PutMapping("/edit/{id}")
    public ResponseEntity<FeedBackDTO> updateFeedback(@PathVariable Long id, @RequestBody FeedBackDTO feedbackDTO) {
        FeedBackDTO updatedFeedback = feedbackService.updateFeedBack(id, feedbackDTO);
        return ResponseEntity.ok(updatedFeedback);
    }

    // Retrieve a single feedback by ID
    @GetMapping("/getone/{id}")
    public ResponseEntity<FeedBackDTO> getFeedbackById(@PathVariable Long id) {
        FeedBackDTO feedbackDTO = feedbackService.getFeedBackById(id);
        return ResponseEntity.ok(feedbackDTO);
    }

    // Retrieve all feedbacks
    @GetMapping("/getall")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedbacks() {
        List<FeedBackDTO> feedbacks = feedbackService.getAllFeedBacks();
        return ResponseEntity.ok(feedbacks);
    }

    // Soft delete feedback by ID
    @PutMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteFeedback(@PathVariable Long id) {
        feedbackService.softDeleteFeedBack(id);
        return ResponseEntity.ok().build();
    }

    // Hard delete feedback by ID
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteFeedback(@PathVariable Long id) {
        feedbackService.hardDeleteFeedBack(id);
        return ResponseEntity.ok().build();
    }

    // Restore soft-deleted feedback by ID
    @PutMapping("/restore/{id}")
    public ResponseEntity<Void> restoreFeedback(@PathVariable Long id) {
        feedbackService.restoreFeedBack(id);
        return ResponseEntity.ok().build();
    }

    // Retrieve feedbacks by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FeedBackDTO>> getFeedbackByUserId(@PathVariable Long userId) {
        List<FeedBackDTO> feedbacks = feedbackService.getFeedbackByUserId(userId);
        return ResponseEntity.ok(feedbacks);
    }

    // Retrieve feedbacks by Menu ID
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<FeedBackDTO>> getFeedbackByMenuId(@PathVariable Long menuId) {
        List<FeedBackDTO> feedbacks = feedbackService.getFeedbackByMenuId(menuId);
        return ResponseEntity.ok(feedbacks);
    }

    // Retrieve all soft-deleted feedbacks
    @GetMapping("/getallremoved")
    public ResponseEntity<List<FeedBackDTO>> getAllRemovedFeedbacks() {
        List<FeedBackDTO> removedFeedbacks = feedbackService.getAllRemovedFeedBacks();
        return ResponseEntity.ok(removedFeedbacks);
    }
}
