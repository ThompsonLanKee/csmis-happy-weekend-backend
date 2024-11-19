package com.spring.csmis.controller;


import com.spring.csmis.entity.DivisionEntity;
import com.spring.csmis.service.DivisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/division")
public class DivisionController {
    private final DivisionService divisionService;

    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @PostMapping("/upload")
    public String uploadDepartments(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }

        try {
            divisionService.importDivisionsFromExcel(file.getInputStream());
            return "Division imported successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while importing departments.";
        }
    }

    @PostMapping("/addDivision")
    public ResponseEntity<DivisionEntity> saveDepartment(@RequestBody DivisionEntity division){
        DivisionEntity  division1= divisionService.insertDivision(division);
        return ResponseEntity.ok(division1);
    }

    @GetMapping("/showDivisions")
    public List<DivisionEntity> showAllDivision(){

        return divisionService.showAllDivisions();
    }

    @GetMapping("/showbyDivisionid/{id}")
    public Optional<DivisionEntity> showDivisionbyId(@PathVariable("id") String id){
        return divisionService.showbyDivisionId(Integer.parseInt(id));
    }

    @GetMapping("/showbyDivisionName/{name}")
    public Optional<DivisionEntity> showDivisionbyName(@PathVariable("name") String name){
        return divisionService.showbyDivisionName(name);
    }

    @PutMapping ("/updateDivision")
    public DivisionEntity updateDepartment(@RequestBody DivisionEntity division){
        return divisionService.updateDivision(division);
    }

    @PutMapping("/deleteDivision/{id}")
    public  void deleteDivision(@PathVariable("id") String id) {
        divisionService.deleteDivision(Integer.parseInt(id));
    }
}
