package com.spring.csmis.controller;

import com.spring.csmis.service.DoorLogAccessTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api/doorlog")
@Async
public class DoorLogAccessTransactionController {

        @Autowired
        private final DoorLogAccessTransactionService doorLogAccessTransactionService;

        public DoorLogAccessTransactionController(DoorLogAccessTransactionService doorLogAccessTransactionService) {
            this.doorLogAccessTransactionService = doorLogAccessTransactionService;
        }

    @PostMapping("/upload")
    public ResponseEntity<String> importDoorLog(@RequestParam("file") MultipartFile file) {
        try {
            doorLogAccessTransactionService.importDoorLogFromExcel(file);
            return ResponseEntity.ok("File processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

}

