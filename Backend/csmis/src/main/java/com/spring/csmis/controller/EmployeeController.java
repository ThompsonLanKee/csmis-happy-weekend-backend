package com.spring.csmis.controller;

import com.spring.csmis.dto.EmployeeDTO;
import com.spring.csmis.entity.DepartmentEntity;
import com.spring.csmis.entity.DivisionEntity;
import com.spring.csmis.entity.EmployeeEntity;
import com.spring.csmis.entity.TeamEntity;
import com.spring.csmis.service.DepartmentService;
import com.spring.csmis.service.DivisionService;
import com.spring.csmis.service.EmployeeService;
import com.spring.csmis.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeamService teamService;

    @GetMapping("/divisions")
    public ResponseEntity<List<DivisionEntity>> getAllDivisions() {
        List<DivisionEntity> divisions = divisionService.showAllDivisions();
        return ResponseEntity.ok(divisions);
    }

    @PostMapping("/departments")
    public ResponseEntity<List<DepartmentEntity>> getDepartmentsByDivision(@RequestParam int divisionId) {
        List<DepartmentEntity> departments = departmentService.getDepartmentsByDivision(divisionId);
        return ResponseEntity.ok(departments);
//        http://localhost:8081/api/employees/departments?divisionId=2
    }

    @PostMapping("/teams")
    public ResponseEntity<List<TeamEntity>> getTeamsByDepartment(@RequestParam int departmentId) {
        List<TeamEntity> teams = teamService.getTeamsByDepartment(departmentId);
        return ResponseEntity.ok(teams);
//        http://localhost:8081/api/employees/teams?departmentId=2
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeEntity> addEmployee(@RequestBody EmployeeEntity employee) {
        System.out.println("Add Employee Controller");
        EmployeeEntity newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    //    @GetMapping("/showEmployees")
//    public ResponseEntity<List<EmployeeEntity>> showAllEmployee(){
//        List<EmployeeEntity> employees = employeeService.showAllEmployees();
//        return new ResponseEntity<>(employees, HttpStatus.OK);
//    }
    @GetMapping("/showEmployees")
    public ResponseEntity<List<EmployeeDTO>> showAllEmployee() {
        try {
            List<EmployeeDTO> employees = employeeService.showAllEmployees();
            if (employees == null || employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Handle empty list scenario
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 error
        }
    }

    @GetMapping("/showbyEmployee/{id}")
    public ResponseEntity<EmployeeDTO> showbyId(@PathVariable Long id){
        Optional<EmployeeDTO> optionalEmployee = employeeService.showbyId(id);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok(optionalEmployee.get());  // Return the EmployeeEntity
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if not found
        }
    }

    @PutMapping(value = "/updateEmployee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity updatedEmployee) {
        System.out.println("Employee ID: " + id);
        System.out.println("Updated Employee Data: " + updatedEmployee);
        EmployeeEntity existingEmployee = employeeService.findbyId(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        existingEmployee.setDivision(updatedEmployee.getDivision());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setTeam(updatedEmployee.getTeam());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setJoined_at(updatedEmployee.getJoined_at());
        // Save the updated employee
        employeeService.updateEmployee(existingEmployee);
        // Create a success response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee updated successfully");
        response.put("updatedEmployee", existingEmployee);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/deleteEmployee/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable("id") String id) {
        Map<String, String> response = new HashMap<>();
        try {
            employeeService.deleteEmployee(Long.valueOf(id));
            response.put("message", "Employee deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error deleting employee.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
