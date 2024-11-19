package com.spring.csmis.controller;

import com.spring.csmis.service.EmployeeService;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/excel")
public class EmployeeExcelController {
    private final EmployeeService employeeService;

    public EmployeeExcelController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    @PostMapping("/employee/upload")
//    public ResponseEntity uploadFileEmployee(@RequestParam("file") MultipartFile file) throws IOException {
//        System.out.println("Employee excel upload controller");
//        if (file.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
//        }
//
//        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) continue; // Skip header row
//
//                Cell staffIdCell = row.getCell(1);
//                if (staffIdCell == null || staffIdCell.getCellType() == CellType.BLANK) {
//                    System.out.println("Staff ID cell is null or empty at row: " + (row.getRowNum() + 1));
//                    continue; // Skip this row
//                }
//
//                // Ensure the staff ID is correctly parsed
//                String staffId;
//                if (staffIdCell.getCellType() == CellType.STRING) {
//                    staffId = staffIdCell.getStringCellValue();
//                } else if (staffIdCell.getCellType() == CellType.NUMERIC) {
//                    staffId = String.valueOf((int) staffIdCell.getNumericCellValue());
//                } else {
//                    System.out.println("Invalid cell type for Staff ID at row: " + (row.getRowNum() + 1));
//                    continue;
//                }
//
//                // Now you can safely process the staff ID
//                EmployeeEntity employee = new EmployeeEntity();
//                employee.setStaff_ID(staffId);
//
//                // Name
//                String name = getCellStringEmployeeValue(row.getCell(2));
//                if (name != null) {
//                    employee.setName(name);
//                }
//
//                // Email
//                String email = getCellStringEmployeeValue(row.getCell(3));
//                if (email != null) {
//                    employee.setEmail(email);
//                }
//
//                // Active status
//                Cell activeCell = row.getCell(4);
//                if (activeCell != null && activeCell.getCellType() == CellType.BOOLEAN) {
//                    employee.set_active(activeCell.getBooleanCellValue());
//                }
//
//                // Status
//                StatusType statusType = null;
//                Cell statusCell = row.getCell(5);
//
//                if (statusCell != null && statusCell.getCellType() == CellType.STRING) {
//                    String statusString = statusCell.getStringCellValue().trim().toUpperCase(); // Get string and normalize
//                    try {
//                        statusType = StatusType.valueOf(statusString); // Convert to enum
//                        employee.setStatus(statusType); // Set the enum value
//                    } catch (IllegalArgumentException e) {
//                        System.out.println("Invalid status value at row " + (row.getRowNum() + 1) + ": " + statusString);
//                        // Handle invalid status values (skip row, set default value, log an error, etc.)
//                    }
//                } else {
//                    // Optionally set a default value if the status cell is null
//                    employee.setStatus(StatusType.ACTIVE); // Default status
//                }
//
//
//                // Division, Department, and Team names
//                String divisionName = getCellStringEmployeeValue(row.getCell(6));
//                String departmentName = getCellStringEmployeeValue(row.getCell(7));
//                String teamName = getCellStringEmployeeValue(row.getCell(8));
//
//                // Fetch existing related entities
//                Division division = employeeService.findDivisionByName(divisionName);
//                Department department = employeeService.findDepartmentByName(departmentName);
//                Team team = employeeService.findTeamByName(teamName);
//
//                // Set relationships
//                employee.setDivision(division);
//                employee.setDepartment(department);
//                employee.setTeam(team);
//
//                // Joined date
//                Cell joinedDateCell = row.getCell(9);
//                if (joinedDateCell != null && joinedDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(joinedDateCell)) {
//                    employee.setJoined_at(joinedDateCell.getDateCellValue());
//                }
//
//                employee.setDeleted(false);
//                employeeService.addEmployee(employee);
//            }
//
//            return ResponseEntity.ok("File uploaded successfully");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed due to an I/O error.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed due to an unexpected error.");
//        }
//    }

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
