package com.spring.csmis.component.util;

import java.security.SecureRandom;

public class OTPUtil {
    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Generates a digit between 0-9
        }
        return otp.toString();
    }
}
