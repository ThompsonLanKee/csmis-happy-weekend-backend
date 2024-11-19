package com.spring.csmis.dto;

import java.sql.Timestamp;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String departmentName;
    private String divisionName;
    private String teamName;

    public UserDTO(Long id,String name, String email, String departmentName, String divisionName, String teamName) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
        this.divisionName = divisionName;
        this.teamName = teamName;
    }

    // Getters and setters
}
