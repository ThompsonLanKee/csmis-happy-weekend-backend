package com.spring.csmis.service;


import com.spring.csmis.entity.DivisionEntity;
import com.spring.csmis.repository.DivisionRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {
    private final DivisionRepository divisionRepository;

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public void importDivisionsFromExcel(InputStream inputStream) throws IOException {
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is on the first sheet
//
//        for (Row row : sheet) {
//            if (row.getRowNum() == 0) continue; // Skip the header row
//
//            // Assuming the department data is in the 3rd column (index 2)
//            Cell divisionCell = row.getCell(6);
//            if (divisionCell != null) {
//                String divisionName;
//
//                // Check the type of the cell before trying to retrieve the value
//                if (divisionCell.getCellType() == CellType.STRING) {
//                    divisionName = divisionCell.getStringCellValue().trim();
//                } else if (divisionCell.getCellType() == CellType.NUMERIC) {
//                    // Convert numeric cell value to string
//                    divisionName = String.valueOf((int) divisionCell.getNumericCellValue()).trim();
//                } else {
//                    // Handle other cell types if necessary
//                    System.err.println("Unexpected cell type: " + divisionCell.getCellType());
//                    continue; // Skip this row if the cell is not of expected type
//                }
//
//                // Check if the department already exists in the database
//                Optional<Division> existingDivision = divisionRepository.findByNameOptional(divisionName);
//
//                if (!existingDivision.isPresent()) {
//                    // Create a new department if it doesn't exist
//                    Division division = new Division();
//                    division.setName(divisionName);
//                    divisionRepository.save(division);
//                }
//            }
//        }
//
//        workbook.close();
    }

    public DivisionEntity insertDivision(DivisionEntity division){
        return divisionRepository.save(division);
    }

    public List<DivisionEntity> showAllDivisions(){
        return divisionRepository.showAllDivisions();
    }

    public Optional<DivisionEntity> showbyDivisionId(Integer id){
        return divisionRepository.findById(id);
    }

    public Optional<DivisionEntity> showbyDivisionName(String name){
        return divisionRepository.findDivisionByName(name);
    }

    public DivisionEntity updateDivision(DivisionEntity division){
        return divisionRepository.save(division);
    }

    public  void deleteDivision(Integer id){
        divisionRepository.deleteDivision(id);
    }
}
