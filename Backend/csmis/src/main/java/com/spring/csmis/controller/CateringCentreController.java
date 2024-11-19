package com.spring.csmis.controller;

import com.spring.csmis.dto.CateringCentreDTO;
import com.spring.csmis.service.cateringcentre.CateringCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cateringcentre")
public class CateringCentreController {

    @Autowired
    private CateringCentreService cateringCentreService;

    // Add a new catering centre
    @PostMapping("/add")
    public ResponseEntity<CateringCentreDTO> createCentre(@RequestBody CateringCentreDTO cateringCentreDTO) {
        CateringCentreDTO createdCateringCentre = cateringCentreService.addCentre(cateringCentreDTO);
        return ResponseEntity.ok(createdCateringCentre);
    }

    // Update an existing catering centre
    @PutMapping("/edit/{id}")
    public ResponseEntity<CateringCentreDTO> updateCentre(@PathVariable Long id, @RequestBody CateringCentreDTO cateringCentreDTO) {
        CateringCentreDTO updatedCateringCentre = cateringCentreService.updateCentre(id, cateringCentreDTO);
        return ResponseEntity.ok(updatedCateringCentre);
    }

    // Get a specific catering centre by its ID
    @GetMapping("/getone/{id}")
    public ResponseEntity<CateringCentreDTO> getCentreById(@PathVariable Long id) {
        CateringCentreDTO cateringCentreDTO = cateringCentreService.getCentreById(id);
        return ResponseEntity.ok(cateringCentreDTO);
    }

    // Get a list of all catering centres
    @GetMapping("/getall")
    public ResponseEntity<List<CateringCentreDTO>> getAllCentres() {
        List<CateringCentreDTO> cateringCentres = cateringCentreService.getAllCentres();
        return ResponseEntity.ok(cateringCentres);
    }

    // Get all soft-deleted catering centres
    @GetMapping("/getallremoved")
    public ResponseEntity<List<CateringCentreDTO>> getAllRemovedCentres() {
        List<CateringCentreDTO> removedCentres = cateringCentreService.getAllRemovedCentres();
        return ResponseEntity.ok(removedCentres);
    }

    // Soft delete a catering centre (marks it as deleted)
    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteCentre(@PathVariable Long id) {
        cateringCentreService.softDeleteCentre(id);
        return ResponseEntity.ok().build();
    }

    // Hard delete a catering centre (permanently removes it from the database)
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteCentre(@PathVariable Long id) {
        cateringCentreService.hardDeleteCentre(id);
        return ResponseEntity.ok().build();
    }

    // Restore a soft-deleted catering centre
    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreCentre(@PathVariable Long id) {
        cateringCentreService.restoreCentre(id);
        return ResponseEntity.ok().build();
    }
}
