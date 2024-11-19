package com.spring.csmis.service;

import com.spring.csmis.entity.DoorLogAccessTransactionEntity;
import com.spring.csmis.entity.DoorLogEntity;
import com.spring.csmis.repository.DoorLogAccessTransactionRepository;
import com.spring.csmis.repository.DoorLogRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DoorLogAccessTransactionServiceImpl implements DoorLogAccessTransactionService {

    private final DoorLogAccessTransactionRepository doorLogAccessTransactionRepository;
    private final DoorLogRepository doorLogRepository;
    private static final Logger logger = LoggerFactory.getLogger(DoorLogAccessTransactionServiceImpl.class);

    public DoorLogAccessTransactionServiceImpl(DoorLogAccessTransactionRepository doorLogAccessTransactionRepository,
                                               DoorLogRepository doorLogRepository) {
        this.doorLogAccessTransactionRepository = doorLogAccessTransactionRepository;
        this.doorLogRepository = doorLogRepository;
    }

    @Override
    @Transactional
    public void importDoorLogFromExcel(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is null or empty");
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Skip header and blank rows
                if (row == null || row.getRowNum() <= 1 || isRowEmpty(row)) continue;

                DoorLogAccessTransactionEntity entity = new DoorLogAccessTransactionEntity();
                try {
                    // Populate fields for each row
                    setDepartment(row, entity);
                    setName(row, entity);
                    setDoorLog(row, entity);
                    setDateTime(row, entity);
                    setLocation(row, entity);
                    setIdNo(row, entity);
                    setVerifyCode(row, entity);
                    setCardNo(row, entity);

                    // Save entity to repository
                    doorLogAccessTransactionRepository.save(entity);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Validation error on row " + (row.getRowNum() + 1) + ": " + e.getMessage());
                }
            }
        }
    }

    private void setDepartment(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(0);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            entity.setDepartment(cell.getStringCellValue().trim());
        }
    }

    private void setName(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(1);
        if (cell != null) {
            String name = cell.getCellType() == CellType.STRING
                    ? cell.getStringCellValue().trim()
                    : String.valueOf((int) cell.getNumericCellValue());
            entity.setName(name);
        }
    }
    private void setDoorLog(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(2);
        if (cell == null || (cell.getCellType() == CellType.BLANK)) {
            // Allow blank or null DoorLogNo
            entity.setDoorLog(null);
            return;
        }

        String doorLogNo = cell.getCellType() == CellType.STRING
                ? cell.getStringCellValue().trim()
                : String.valueOf((long) cell.getNumericCellValue());

        if (doorLogNo.isEmpty()) {
            entity.setDoorLog(null); // Allow empty DoorLogNo
            return;
        }

        DoorLogEntity doorLogEntity = doorLogRepository.findByDoorLogNo(doorLogNo);
        if (doorLogEntity == null) {
            throw new IllegalArgumentException("No matching DoorLogEntity found for DoorLogNo: " + doorLogNo);
        }
        entity.setDoorLog(doorLogEntity);
    }


    private void setDateTime(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(3);
        if (cell != null) {
            try {
                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    entity.setDate_Time(cell.getDateCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    entity.setDate_Time(new SimpleDateFormat("MM-dd-yyyy").parse(cell.getStringCellValue().trim()));
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format in 'Date/Time': " + cell.getStringCellValue());
            }
        }
    }

    private void setLocation(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(4);
        if (cell != null) {
            try {
                if (cell.getCellType() == CellType.NUMERIC) {
                    entity.setLocation((int) cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    entity.setLocation(Integer.parseInt(cell.getStringCellValue().trim()));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number in 'Location': " + cell.getStringCellValue());
            }
        }
    }

    private void setIdNo(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(5);
        if (cell != null) {
            try {
                if (cell.getCellType() == CellType.NUMERIC) {
                    entity.setId_no((int) cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    String value = cell.getStringCellValue().trim();
                    if (!value.isEmpty()) {
                        entity.setId_no(Integer.parseInt(value));
                    }
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number in 'ID No': " + cell.getStringCellValue());
            }
        } else {
            entity.setId_no(null);
        }
    }

    private void setVerifyCode(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(6);
        if (cell != null) {
            String verifyCode = cell.getCellType() == CellType.STRING
                    ? cell.getStringCellValue().trim()
                    : String.valueOf((int) cell.getNumericCellValue());
            entity.setVerifyCode(verifyCode);
        }
    }

    private void setCardNo(Row row, DoorLogAccessTransactionEntity entity) {
        Cell cell = row.getCell(7);
        if (cell != null) {
            try {
                if (cell.getCellType() == CellType.NUMERIC) {
                    entity.setCard_no((long) cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    String value = cell.getStringCellValue().trim();
                    if (!value.isEmpty()) {
                        entity.setCard_no(Long.parseLong(value));
                    }
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number in 'CardNo': " + cell.getStringCellValue());
            }
        } else {
            entity.setCard_no(null);
        }
    }

    private boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}

