package com.spring.csmis.service;

import com.spring.csmis.entity.DepartmentEntity;
import com.spring.csmis.repository.DepartmentRepository;
import com.spring.csmis.repository.DivisionRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DivisionRepository divisionRepository; // Inject DivisionRepository to find divisions

    public DepartmentService(DepartmentRepository departmentRepository, DivisionRepository divisionRepository) {
        this.departmentRepository = departmentRepository;
        this.divisionRepository = divisionRepository;
    }

    public void importDepartmentsFromExcel(InputStream inputStream) throws IOException {
//        System.out.println("DepartmentService");
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is on the first sheet
//
//        for (Row row : sheet) {
//            if (row.getRowNum() == 0) continue; // Skip the header row
//
//            // Assuming the department data is in the 3rd column (index 2)
//            Cell departmentCell = row.getCell(7);
//            Cell divisionCell = row.getCell(6); // Assuming the division name is in the 8th column (index 7)
//
//            if (departmentCell != null && divisionCell != null) {
//                String departmentName;
//                String divisionName;
//
//                // Extract department name
//                if (departmentCell.getCellType() == CellType.STRING) {
//                    departmentName = departmentCell.getStringCellValue().trim();
//                } else if (departmentCell.getCellType() == CellType.NUMERIC) {
//                    departmentName = String.valueOf((int) departmentCell.getNumericCellValue()).trim();
//                } else {
//                    System.err.println("Unexpected department cell type: " + departmentCell.getCellType());
//                    continue; // Skip this row if the department cell is not of expected type
//                }
//
//                // Extract division name
//                if (divisionCell.getCellType() == CellType.STRING) {
//                    divisionName = divisionCell.getStringCellValue().trim();
//                } else if (divisionCell.getCellType() == CellType.NUMERIC) {
//                    divisionName = String.valueOf((int) divisionCell.getNumericCellValue()).trim();
//                } else {
//                    System.err.println("Unexpected division cell type: " + divisionCell.getCellType());
//                    continue; // Skip this row if the division cell is not of expected type
//                }
//
//                // Find the specific division by name
//                Optional<Division> divisionOptional = divisionRepository.findByNameOptional(divisionName);
//                if (!divisionOptional.isPresent()) {
//                    System.err.println("Division not found: " + divisionName);
//                    continue; // Skip this row if the division is not found
//                }
//                Division division = divisionOptional.get(); // Get the division entity
//
//                // Check if the department already exists in the database under this division
////                Optional<Department> existingDepartment = departmentRepository.findByNameAndDivision(departmentName, division);
//
////                if (!existingDepartment.isPresent()) {
//                    // Create a new department if it doesn't exist under the division
//                    Department department = new Department();
//                    department.setName(departmentName);
//                    department.setDivision(division); // Set the division relationship
//                    departmentRepository.save(department);
////                }
//            }
//        }
//
//        workbook.close();
    }

    public DepartmentEntity insertDepartment(DepartmentEntity department){
        return departmentRepository.save(department);
    }

    public List<DepartmentEntity> showAllDepartments(){
        return departmentRepository.showAllDepartments();
    }

    public Optional<DepartmentEntity> showbyDepartmentId(Integer id){
        return departmentRepository.findById(id);
    }

    public DepartmentEntity updateDepartment(DepartmentEntity department){
        return departmentRepository.save(department);
    }

    public  void deleteDepartment(Integer id){
        departmentRepository.deleteDepartment(id);
    }

    public List<DepartmentEntity> getDepartmentsByDivision(int divisionId) {
        return departmentRepository.findByDivisionId(divisionId);
    }

//    public Optional<Department> getDepartmentbyDivisionName(String department,String division){
//        return departmentRepository.findByNameAndDivision(department,division);
//    }

    public Optional<DepartmentEntity> getDepartmentByDivisionName(String name, String division) {
        return departmentRepository.findByNameAndDivision(name, division);
    }
}
