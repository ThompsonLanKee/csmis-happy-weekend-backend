package com.spring.csmis.dto;

import lombok.Data;

@Data
public class AvoidMeatDTO {
    private Long id;
    private String meatName;
    private Boolean isDeleted = false;
}
