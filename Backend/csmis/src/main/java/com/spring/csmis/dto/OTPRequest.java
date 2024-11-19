package com.spring.csmis.dto;


import lombok.Data;

@Data
public class OTPRequest {
//    @Email(message = "Email should be valid")
//    @NotBlank(message = "Email is required")
    private String email;
}
