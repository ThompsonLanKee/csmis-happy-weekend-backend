package com.spring.csmis.service;

import com.spring.csmis.entity.HolidayEntity;
import com.spring.csmis.enums.Month;
import com.spring.csmis.repository.HolidayRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    public void saveHolidays(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet contains holiday data

            List<HolidayEntity> holidays = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String dateStr = row.getCell(0).getStringCellValue(); // Holiday date
                String name = row.getCell(1).getStringCellValue();    // Holiday name

                // Parse date and month
                LocalDate date = parseHolidayDate(dateStr);
                if (date == null) {
                    System.err.println("Skipping invalid date: " + dateStr);
                    continue; // Skip invalid datesw
                }

                Month month = Month.valueOf(date.getMonth().name());

                // Create and save the holiday entity
                HolidayEntity holiday = new HolidayEntity();
                holiday.setDate(date);
                holiday.setName(name);
                holiday.setMonth(month.ordinal());
                holiday.setIsBankHoliday(name.contains("Bank Holiday"));
                holidays.add(holiday);
            }

            holidayRepository.saveAll(holidays);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the file", e);
        }
    }

    // Method to parse the holiday date
//    public LocalDate parseHolidayDate(String dateStr) {
//        // Remove the day of the week (first part of the string)
//        String cleanDateStr = dateStr.replaceAll("^\\w+,\\s*", "");
//
//        // Define the format to match "January 1, 2023"
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
//
//        try {
//            return LocalDate.parse(cleanDateStr, formatter);
//        } catch (DateTimeParseException e) {
//            System.out.println("Error parsing date: " + e.getMessage());
//            return null; // Handle as needed
//        }
//    }

    public LocalDate parseHolidayDate(String dateStr) {
        // Remove the day of the week (the part after the last space) and any ordinal indicators
        String cleanDateStr = dateStr.replaceAll("\\s\\w+$", "") // Remove the day of the week
                .replaceAll("(\\d+)(st|nd|rd|th)", "$1") // Remove ordinal indicators
                .trim(); // Trim any leading or trailing whitespace

        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Append the current year to the cleaned date string
        String dateWithYear = cleanDateStr + ", " + currentYear;

        // Define the format to match "d MMMM, yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy");

        try {
            return LocalDate.parse(dateWithYear, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return null; // Handle as needed
        }
    }





    @Override
    public List<HolidayEntity> getAllHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public List<HolidayEntity> findByMonthAndYear(int month, int year) {
        return holidayRepository.findByMonthAndYear(month, year);
    }

}
