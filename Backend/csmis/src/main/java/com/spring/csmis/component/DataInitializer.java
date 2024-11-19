package com.spring.csmis.component;

import com.spring.csmis.entity.MonthEntity;
import com.spring.csmis.repository.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MonthRepository monthRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if months are already populated
        if (monthRepository.count() == 0) {
            // Define the range of years you want to pre-populate
            int startYear = LocalDate.now().getYear();
            int endYear = startYear + 5; // Next 5 years

            for (int year = startYear; year <= endYear; year++) {
                for (int month = 1; month <= 12; month++) {
                    String monthName = LocalDate.of(year, month, 1).getMonth().name();
                    monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();

                    // Calculate weekends for the month
                    Set<Integer> weekends = calculateWeekends(year, month);

                    MonthEntity monthEntity = new MonthEntity();
                    monthEntity.setName(monthName);
                    monthEntity.setYear(year);
                    monthEntity.setWeekends(weekends);

                    // Optionally, populate holidays here or manage separately

                    monthRepository.save(monthEntity);
                }
            }

            System.out.println("MonthEntity table populated successfully.");
        } else {
            System.out.println("MonthEntity table already populated.");
        }
    }

    private Set<Integer> calculateWeekends(int year, int month) {
        Set<Integer> weekends = new HashSet<>();
        LocalDate date = LocalDate.of(year, month, 1);
        int lengthOfMonth = date.lengthOfMonth();

        for (int day = 1; day <= lengthOfMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                weekends.add(day);
            }
        }

        return weekends;
    }
}
