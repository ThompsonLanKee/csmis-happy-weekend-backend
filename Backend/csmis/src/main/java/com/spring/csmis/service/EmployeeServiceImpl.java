//package com.spring.csmis.service;
//
//import com.spring.csmis.entity.EmployeeEntity;
//import com.spring.csmis.repository.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class EmployeeServiceImpl implements EmployeeService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Override
//    public List<EmployeeEntity> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
//
//    @Override
//    public Optional<EmployeeEntity> findEmployeeById(String employeeId) {
//        return employeeRepository.findById(employeeId);
//    }
//
//    @Override
//    public EmployeeEntity saveEmployee(EmployeeEntity employee) {
//        return employeeRepository.save(employee);
//    }
//
//    @Override
//    public EmployeeEntity updateEmployee(EmployeeEntity employee) {
//        // Assuming employee exists, otherwise you may need to add additional validation
//        return employeeRepository.save(employee);
//    }
//
//    @Override
//    public void deleteEmployeeById(String employeeId) {
//        employeeRepository.deleteById(employeeId);
//    }
//
////    @Override
////    public String getLatestEmployeeId() {
////        return employeeRepository.findLatestEmployeeId();
////    }
//
//    @Override
//    public String generateNewEmployeeId() {
//        String latestEmployeeId = employeeRepository.findLatestEmployeeId();
//
//        if (latestEmployeeId == null) {
//            // Start with the first ID if no employee exists
//            return "26-00001";
//        }
//
//        // Extract the numeric part of the latest employee ID
//        String numericPart = latestEmployeeId.split("-")[1];
//        int newIdNumber = Integer.parseInt(numericPart) + 1;
//
//        // Pad the number with leading zeros
//        String newNumericPart = String.format("%05d", newIdNumber);
//
//        // Combine prefix with the new numeric part
//        return "26-" + newNumericPart;
//    }
//}
