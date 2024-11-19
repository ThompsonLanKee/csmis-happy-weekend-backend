package com.spring.csmis.dto;

import lombok.Data;

@Data
public class OtpVerificationRequest {
    private String email;
    private String otpCode;
}
