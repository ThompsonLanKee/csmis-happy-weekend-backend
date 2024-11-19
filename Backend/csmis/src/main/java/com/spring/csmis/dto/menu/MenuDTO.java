package com.spring.csmis.dto.menu;

import com.spring.csmis.entity.MealEntity;
import com.spring.csmis.enums.DayName;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class MenuDTO {
    private Long id;
    private DayName dayName;
    private LocalDate date;
    private List<MealEntity> meals;
    private Long optionalMealId;
    private String optionalMealName; // Or similar field
    private Long no_of_pax;
    //private double price;
    private String remark;

}
