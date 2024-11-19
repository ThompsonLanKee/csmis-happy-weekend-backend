package com.spring.csmis.service;



import com.spring.csmis.dto.EmployeeDTO;
import com.spring.csmis.dto.EmployeeMapper;
import com.spring.csmis.entity.DepartmentEntity;
import com.spring.csmis.entity.DivisionEntity;
import com.spring.csmis.entity.EmployeeEntity;

import com.spring.csmis.entity.TeamEntity;
import com.spring.csmis.repository.DepartmentRepository;
import com.spring.csmis.repository.DivisionRepository;
import com.spring.csmis.repository.EmployeeRepository;
import com.spring.csmis.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DivisionRepository divisionRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DivisionRepository divisionRepository,
                           DepartmentRepository departmentRepository,
                           TeamRepository teamRepository) {
        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;
        this.departmentRepository = departmentRepository;
        this.teamRepository = teamRepository;
    }

    public EmployeeEntity addEmployee(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public Page<EmployeeEntity> showAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.showAllUsers(pageable);
    }

    public Optional<EmployeeEntity> showbyUserId(Long id) {
        return employeeRepository.findById(id);
    }

    public EmployeeEntity updateEmployee(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public DivisionEntity findDivisionByName(String name) throws RuntimeException {
        return divisionRepository.findDivisionByName(name)
                .orElseThrow(() -> new RuntimeException("Division not found: " + name));
    }

    public List<DivisionEntity> getAllDivisions() {
        return divisionRepository.findAll();
    }

    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<EmployeeDTO> showAllEmployees() {
        try {
            List<EmployeeEntity> employees = employeeRepository.showAllEmployees();
            return EmployeeMapper.toDTOList(employees);
//            return employees;

        } catch (Exception e) {
            // Log the error and handle it
            e.printStackTrace();
            throw new RuntimeException("Error fetching employees from the database");
        }
    }

    public Optional<EmployeeDTO> showbyId(Long id) {
        try {
            Optional<EmployeeEntity> employee = employeeRepository.findById(id);
            return employee.map(EmployeeMapper::toDTO); // Map only if employee is present
//            return employee;
        } catch (Exception e) {
            // Log the error and handle it
            e.printStackTrace();
            throw new RuntimeException("Error fetching employee from the database");
        }
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteEmployee(id);
    }

    public Optional<EmployeeEntity> findbyId(Long id) {
        try {
            Optional<EmployeeEntity> employee = employeeRepository.findById(id);
            return employee; // Map only if employee is present
//            return employee;
        } catch (Exception e) {
            // Log the error and handle it
            e.printStackTrace();
            throw new RuntimeException("Error fetching employee from the database");
        }
    }
}
