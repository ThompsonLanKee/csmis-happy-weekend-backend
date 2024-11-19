package com.spring.csmis.controller;

import com.spring.csmis.entity.DepartmentEntity;

import com.spring.csmis.entity.DivisionEntity;
import com.spring.csmis.service.DepartmentService;
import com.spring.csmis.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DivisionService divisionService;
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/upload")
    public String uploadDepartments(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }

        try {
            departmentService.importDepartmentsFromExcel(file.getInputStream());
            return "Departments imported successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while importing departments.";
        }
    }

    @PostMapping("/addDepartment")
    public DepartmentEntity saveDepartment(@RequestBody DepartmentEntity department){
        DivisionEntity division = divisionService.showbyDivisionId(department.getDivision().getId())
                .orElseThrow(() -> new RuntimeException("Division not found"));
        department.setDivision(division);
        return departmentService.insertDepartment(department);
    }

    @GetMapping("/showDepartments")
    public List<DepartmentEntity> showAllDepartment(){

        return departmentService.showAllDepartments();
    }

    @GetMapping("/showbyDepartmentid/{id}")
    public Optional<DepartmentEntity> showDepartmentbyId(@PathVariable("id") String id){
        return departmentService.showbyDepartmentId(Integer.parseInt(id));
    }

//    @GetMapping("/showbyDepartmentName/{division}/{name}")
//    public Optional<Department> showDepartmentbyName(@PathVariable("name") String name,@PathVariable("division") String division){
//        return departmentService.getDepartmentByDivisionName(name,division);
//    }

//    @GetMapping("/showbyDepartmentName/{division}/{name}")
//    public ResponseEntity<Department> showDepartmentByName(
//            @PathVariable("name") String name,
//            @PathVariable("division") String division) {
//        Optional<Department> department = departmentService.getDepartmentByDivisionName(name, division);
//
//        return department.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/showbyDepartmentName")
    public ResponseEntity<DepartmentEntity> showDepartmentByName(
            @RequestParam("division") String division,
            @RequestParam("name") String name) {
        Optional<DepartmentEntity> department = departmentService.getDepartmentByDivisionName(name, division);

        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping ("/updateDepartment")
    public DepartmentEntity updateDepartment(@RequestBody DepartmentEntity tag){
        return departmentService.updateDepartment(tag);
    }

    @PutMapping("/deleteDepartment/{id}")
    public  void deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(Integer.parseInt(id));
    }
}
