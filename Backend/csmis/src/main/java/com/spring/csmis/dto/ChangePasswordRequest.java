package com.spring.csmis.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private Long id;

    private String currentPassword;

    private String newPassword;

    private  String confirmNewPassword;
}
