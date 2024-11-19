package com.spring.csmis.dto;

import com.spring.csmis.enums.StatusType;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {
    private Long id;
    private String staff_ID;
    private String name;
    private String email;
    private StatusType status;
    private String division;
    private String department;
    private String team;
    private Date joined_at;
    private boolean isDeleted;
}
