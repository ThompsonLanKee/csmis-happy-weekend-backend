package com.spring.csmis.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Identity Number is required")
    private String staffId;

    @NotBlank(message = "Password is required")
    private String password;

}