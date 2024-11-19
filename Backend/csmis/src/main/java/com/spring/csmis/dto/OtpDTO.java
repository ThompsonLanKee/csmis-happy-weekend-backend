package com.spring.csmis.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpDTO {
    private String email;
    private String otpCode;
    private LocalDateTime expirationTime;

    public OtpDTO(String email, String otpCode, LocalDateTime expirationTime) {
        this.email = email;
        this.otpCode = otpCode;
        this.expirationTime = expirationTime;
    }
}
