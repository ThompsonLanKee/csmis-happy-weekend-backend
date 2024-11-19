package com.spring.csmis.controller;

import com.spring.csmis.dto.meal.MealDTO;
import com.spring.csmis.service.meal.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/meal")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping("/add")
    public ResponseEntity<MealDTO> createMeal(@RequestBody MealDTO mealDTO) {
        MealDTO createdMeal = mealService.addMeal(mealDTO);
        return ResponseEntity.ok(createdMeal);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MealDTO> updateMeal(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
        MealDTO updatedMeal = mealService.updateMeal(id, mealDTO);
        return ResponseEntity.ok(updatedMeal);
    }

    @GetMapping("/getone/{id}")
    public ResponseEntity<MealDTO> getMealById(@PathVariable Long id) {
        MealDTO mealDTO = mealService.getMealById(id);
        return ResponseEntity.ok(mealDTO);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MealDTO>> getAllMeals() {
        List<MealDTO> meals = mealService.getAllMeals();
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/getallremoved")
    public ResponseEntity<List<MealDTO>> getAllRemovedMeals() {
        List<MealDTO> removedMeals = mealService.getAllRemovedMeals();
        return ResponseEntity.ok(removedMeals);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<Void> softDeleteMeal(@PathVariable Long id) {
        mealService.softDeleteMeal(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<Void> hardDeleteMeal(@PathVariable Long id) {
        mealService.hardDeleteMeal(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreMeal(@PathVariable Long id) {
        mealService.restoreMeal(id);
        return ResponseEntity.ok().build();
    }
}
