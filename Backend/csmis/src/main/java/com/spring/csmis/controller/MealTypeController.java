package com.spring.csmis.controller;

import com.spring.csmis.dto.meal.MealTypeDTO;
import com.spring.csmis.service.meal.MealTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/mealtype")
public class MealTypeController {

    @Autowired
    private MealTypeService mealTypeService;

    @PostMapping("/add")
    public ResponseEntity<MealTypeDTO> createMealType(@RequestBody MealTypeDTO mealTypeDTO) {
        MealTypeDTO createdMealType = mealTypeService.addType(mealTypeDTO);
        return ResponseEntity.ok(createdMealType);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MealTypeDTO> updateMealType(@PathVariable Long id, @RequestBody MealTypeDTO mealTypeDTO) {
        MealTypeDTO updatedMealType = mealTypeService.updateType(id, mealTypeDTO);
        return ResponseEntity.ok(updatedMealType);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<MealTypeDTO> getMealTypeById(@PathVariable Long id) {
        MealTypeDTO mealTypeDTO = mealTypeService.getTypeById(id);
        return ResponseEntity.ok(mealTypeDTO);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MealTypeDTO>> getAllMealTypes() {
        List<MealTypeDTO> mealTypes = mealTypeService.getAllTypes();
        return ResponseEntity.ok(mealTypes);
    }

    @GetMapping("/getallremoved")
    public ResponseEntity<List<MealTypeDTO>> getAllRemovedMealTypes() {
        List<MealTypeDTO> removedMealTypes = mealTypeService.getAllRemovedTypes();
        return ResponseEntity.ok(removedMealTypes);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteMealType(@PathVariable Long id) {
        mealTypeService.softDeleteType(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteMealType(@PathVariable Long id) {
        mealTypeService.hardDeleteType(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreMealType(@PathVariable Long id) {
        mealTypeService.restoreType(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MealTypeDTO>> getMealTypesByCategoryIdAndIsDeleteFalse(@PathVariable Long categoryId) {
        List<MealTypeDTO> mealTypes = mealTypeService.getMealTypesByCategoryIdAndIsDeleteFalse(categoryId);
        if (mealTypes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no meal types found
        }
        return ResponseEntity.ok(mealTypes); // Return 200 OK with the list of meal types
    }
}
