package com.spring.csmis.service;

import com.spring.csmis.entity.TeamEntity;
import com.spring.csmis.repository.DepartmentRepository;
import com.spring.csmis.repository.DivisionRepository;
import com.spring.csmis.repository.TeamRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;

    private  final DivisionRepository divisionRepository;

    public TeamService(TeamRepository teamRepository, DepartmentRepository departmentRepository, DivisionRepository divisionRepository) {
        this.teamRepository = teamRepository;
        this.departmentRepository = departmentRepository;
        this.divisionRepository = divisionRepository;
    }



    public void importTeamsFromExcel(InputStream inputStream) throws IOException {
//        System.out.println("TeamService - Importing Teams");
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is on the first sheet
//
//        for (Row row : sheet) {
//            if (row.getRowNum() == 0) continue; // Skip the header row
//
//            // Extracting the required fields from the row (team, department, division)
//            Cell teamCell = row.getCell(8);
//            Cell departmentCell = row.getCell(7);
//            Cell divisionCell = row.getCell(6);
//
//            if (teamCell != null && departmentCell != null && divisionCell != null) {
//                String divisionName = extractCellValue(divisionCell);
//                String departmentName = extractCellValue(departmentCell);
//                String teamName = extractCellValue(teamCell);
//
//                if (divisionName == null || departmentName == null || teamName == null) {
//                    continue; // Skip if any required field is missing
//                }
//
//                // Step 1: Check if the division exists, otherwise create it
//                Division division = divisionRepository.findByNameOption(divisionName)
//                        .orElseGet(() -> {
//                            Division newDivision = new Division();
//                            newDivision.setName(divisionName);
//                            newDivision.setDeleted(false);
//                            return divisionRepository.save(newDivision);
//                        });
//
//                // Step 2: Check if the department exists in the division, otherwise create it
//                Department department = departmentRepository.findByNameAndDivision(departmentName, division)
//                        .orElseGet(() -> {
//                            Department newDepartment = new Department();
//                            newDepartment.setName(departmentName);
//                            newDepartment.setDivision(division);
//                            newDepartment.setDeleted(false);
//                            return departmentRepository.save(newDepartment);
//                        });
//
//                // Step 3: Check if the team exists in the department, otherwise create it
//                Optional<Team> existingTeam = teamRepository.findByNameAndDepartmentOptional(teamName, department);
//
//                if (!existingTeam.isPresent()) {
//                    // No matching team found, create a new team
//                    Team newTeam = new Team();
//                    newTeam.setName(teamName);
//                    newTeam.setDepartment(department);
//                    teamRepository.save(newTeam);
//                    System.out.println("Added new team: " + teamName);
//                }
//            }
//        }
//
//        workbook.close();
    }
    public TeamEntity insertTeam(TeamEntity team){
        return teamRepository.save(team);
    }

    public List<TeamEntity> showAllTeams(){
        return teamRepository.showAllTeams();
    }

    public Optional<TeamEntity> showbyTeamId(Integer id){
        return teamRepository.findById(id);
    }


    public TeamEntity updateTeam(TeamEntity team){
        return teamRepository.save(team);
    }

    public  void deleteTeam(Integer id){
        teamRepository.deleteTeam(id);
    }

    public List<TeamEntity> getTeamsByDepartment(int departmentId) {
        return teamRepository.findByDepartmentId(departmentId);
    }

    public Optional<TeamEntity> getTeamByDepartmentName( String department,String division,String name) {
        return teamRepository.findByNameAndDepartment(department,division,name);
    }

    private String extractCellValue(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue()).trim();
        } else {
            System.err.println("Unexpected cell type: " + cell.getCellType());
            return null;
        }
    }
}
