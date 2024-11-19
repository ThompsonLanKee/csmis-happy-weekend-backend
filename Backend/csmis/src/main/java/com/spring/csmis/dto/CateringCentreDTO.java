package com.spring.csmis.dto;

import com.spring.csmis.entity.MealEntity;
import com.spring.csmis.enums.CentreType;
import com.spring.csmis.enums.DayName;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CateringCentreDTO {
    private Long id;
    private String centreName;
    private String centreAddress;
    private CentreType centreType;
    private String centrePhone;
    private String receiverName;
}
