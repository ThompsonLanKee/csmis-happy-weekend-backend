package com.spring.csmis.service;

import com.spring.csmis.entity.*;
import com.spring.csmis.enums.StatusType;
import com.spring.csmis.repository.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class ImportService {

    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    private final DivisionRepository divisionRepository;
    private final EmployeeRepository employeeRepository;
    private final DoorLogRepository doorLogRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    public ImportService(TeamRepository teamRepository,
                         DepartmentRepository departmentRepository,
                         DivisionRepository divisionRepository,
                         EmployeeRepository employeeRepository,
                         DoorLogRepository doorLogRepository) {
        this.teamRepository = teamRepository;
        this.departmentRepository = departmentRepository;
        this.divisionRepository = divisionRepository;
        this.employeeRepository = employeeRepository;
        this.doorLogRepository = doorLogRepository;
    }

    public String importDataFromExcel(InputStream inputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet contains the data

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Process Division
                String divisionName = extractCellValue(row.getCell(5));
                if (divisionName == null) {
                    logger.warn("Skipping row {} due to missing Division", row.getRowNum());
                    continue;
                }
                DivisionEntity division = findOrCreateDivision(divisionName);

                // Process Department
                String departmentName = extractCellValue(row.getCell(6));
                if (departmentName == null) {
                    logger.warn("Skipping row {} due to missing Department", row.getRowNum());
                    continue;
                }
                DepartmentEntity department = findOrCreateDepartment(departmentName, division);

                // Process Team
                String teamName = extractCellValue(row.getCell(7));
                if (teamName == null) {
                    logger.warn("Skipping row {} due to missing Team", row.getRowNum());
                    continue;
                }
                TeamEntity team = findOrCreateTeam(teamName, department);

                String doorlogName = extractCellValue(row.getCell(9));
                if (doorlogName == null) {
                    logger.warn("Skipping row {} due to missing Team", row.getRowNum());
                    continue;
                }
                DoorLogEntity doorLog = findOrCreateDoorlog(doorlogName);

                // Process Employee
                EmployeeEntity employee = createEmployeeFromRow(row, division, department, team,doorLog);
                if (employee != null) {
                    employeeRepository.save(employee);
                    logger.info("Employee '{}' added successfully.", employee.getName());
                }
            }
            return "Data imported successfully!";
        }  catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during data import: " + e.getMessage();
    }

    }

    // Helper method to extract cell values
    private String extractCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()).trim();
            default:
                return null;
        }
    }

    // Helper methods to find or create entities
    private DivisionEntity findOrCreateDivision(String name) {
        return divisionRepository.findByName(name).stream()
                .findFirst()
                .orElseGet(() -> {
                    DivisionEntity division = new DivisionEntity();
                    division.setName(name);
                    return divisionRepository.save(division);
                });
    }

    private DoorLogEntity findOrCreateDoorlog(String doorLogNo) {
        return doorLogRepository.findAllByDoorLogNo(doorLogNo).stream()
                .findFirst()
                .orElseGet(() -> {
                    DoorLogEntity doorlog = new DoorLogEntity();
                    doorlog.setDoorLogNo(doorLogNo);
                    return doorLogRepository.save(doorlog);
                });
    }

    private DepartmentEntity findOrCreateDepartment(String name, DivisionEntity division) {
        return departmentRepository.findByNameAndDivision(name, division).stream()
                .findFirst()
                .orElseGet(() -> {
                    DepartmentEntity department = new DepartmentEntity();
                    department.setName(name);
                    department.setDivision(division);
                    return departmentRepository.save(department);
                });
    }

    private TeamEntity findOrCreateTeam(String name, DepartmentEntity department) {
        return teamRepository.findAllByNameAndDepartment(name, department).stream()
                .findFirst()
                .orElseGet(() -> {
                    TeamEntity team = new TeamEntity();
                    team.setName(name);
                    team.setDepartment(department);
                    return teamRepository.save(team);
                });
    }

    private EmployeeEntity createEmployeeFromRow(Row row, DivisionEntity division, DepartmentEntity department, TeamEntity team,DoorLogEntity doorLog) {
        try {
            // Validate and extract required fields
            String staffId = extractCellValue(row.getCell(1));
            String name = extractCellValue(row.getCell(2));
            String email = extractCellValue(row.getCell(3));
            if (staffId == null || name == null || email == null) {
                logger.warn("Skipping row {} due to missing required fields (Staff ID, Name, or Email).", row.getRowNum());
                return null;
            }

//            // Active status
//            boolean isActive = false;
//            Cell activeCell = row.getCell(4);
//            if (activeCell != null && activeCell.getCellType() == CellType.BOOLEAN) {
//                isActive = activeCell.getBooleanCellValue();
//            }

            // Status
            String statusString = extractCellValue(row.getCell(4));
            StatusType status = StatusType.ACTIVE; // Default status
            if (statusString != null) {
                try {
                    status = StatusType.valueOf(statusString.toUpperCase());
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid status '{}' at row {}, defaulting to ACTIVE.", statusString, row.getRowNum());
                }
            }

            // Join date
            Date joinedDate = null;
            Cell joinedDateCell = row.getCell(8);
            if (joinedDateCell != null && DateUtil.isCellDateFormatted(joinedDateCell)) {
                joinedDate = joinedDateCell.getDateCellValue();
            }

            // DoorLog
//            String doorLogNo = extractCellValue(row.getCell(9));
//            DoorLogEntity doorLogEntity = null;
//            if (doorLogNo != null && !doorLogNo.isEmpty()) {
//                doorLogEntity = doorLogRepository.findByDoorLogNo(doorLogNo);
//                if (doorLogEntity == null) {
//                    logger.warn("No matching DoorLogEntity found for DoorLogNo: '{}'", doorLogNo);
//                    return null;
//                }
//            }

            // Create and populate EmployeeEntity
            EmployeeEntity employee = new EmployeeEntity();
            employee.setStaffID(staffId);
            employee.setName(name);
            employee.setEmail(email);
            employee.setStatus(status);
            employee.setJoined_at(joinedDate);
            employee.setDivision(division);
            employee.setDepartment(department);
            employee.setTeam(team);
            employee.setDoorLog(doorLog);
            employee.setDeleted(false);


            return employee;
        } catch (Exception e) {
            logger.error("Failed to create employee from row {}: {}", row.getRowNum(), e.getMessage());
            return null;
        }
    }
}
