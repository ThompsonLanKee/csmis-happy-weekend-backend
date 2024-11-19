package com.spring.csmis.dto;

import com.spring.csmis.enums.RoleType;
import lombok.Data;

@Data
public class LoginUserDTO {
    private Long uid;
    private String uname;
    private String email;
    private RoleType role;

    public LoginUserDTO(int uid, String email, String role) {
    }

    public LoginUserDTO() {

    }

    public LoginUserDTO(int uid, String uname, String email, String role) {
    }
}
