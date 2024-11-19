package com.spring.csmis.controller;

import com.spring.csmis.repository.DepartmentRepository;
import com.spring.csmis.repository.DivisionRepository;
import com.spring.csmis.repository.TeamRepository;
import com.spring.csmis.service.EmployeeService;
import com.spring.csmis.service.ImportService;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/auth/import")
public class ImportController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DivisionRepository divisionRepository;

    @Autowired
    TeamRepository teamRepository;
    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please upload a file!");
//        }
//
//        try {
//            String message = importService.importDataFromExcel(file.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing data.");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("Three data imported");

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file!");
        }

        try {
            // Import all data from the Excel file (Division, Department, Team, Employee)
            String message = importService.importDataFromExcel(file.getInputStream());
            return ResponseEntity.ok(message);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing data.");
        }
    }

    private String getCellStringEmployeeValue(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return String.valueOf((int) cell.getNumericCellValue());
                case BLANK:
                    return null; // Treat blank cells as null
                default:
                    return null; // Handle other types as null
            }
        }
        return null; // If the cell itself is null
    }
}
