package com.spring.csmis.service;

import com.spring.csmis.component.util.OTPUtil;
import com.spring.csmis.dto.OtpDTO;
import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OTPService {
//    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
//    private final long EXPIRATION_TIME_MS = 5 * 60 * 1000; // 5 minutes expiration
//
//    // Save OTP with timestamp
//    public void storeOTP(String key, String otp) {
//        long timestamp = System.currentTimeMillis() + EXPIRATION_TIME_MS;
//        otpStorage.put(key, otp + ":" + timestamp);
//    }
//
//    // Validate OTP
//    public boolean validateOTP(String key, String otp) {
//        if (!otpStorage.containsKey(key)) {
//            return false;
//        }
//
//        String storedOTPWithTimestamp = otpStorage.get(key);
//        String[] parts = storedOTPWithTimestamp.split(":");
//        String storedOTP = parts[0];
//        long expirationTime = Long.parseLong(parts[1]);
//
//        if (System.currentTimeMillis() > expirationTime) {
//            otpStorage.remove(key);
//            return false; // OTP expired
//        }
//
//        if (storedOTP.equals(otp)) {
//            otpStorage.remove(key); // OTP is valid, remove after use
//            return true;
//        }
//
//        return false; // OTP mismatch
//    }

//    @Autowired
//    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService; // For sending emails

//    @Autowired
//    private OTPGenerator otpGenerator;
//
//    @Autowired
//    private OTPRepository otpRepository;

    private Map<String, OtpDTO> otpStorage = new HashMap<>(); // Store OTPs in memory

    // Generate OTP, store it in the Map, and send via email
    @Async
    public void sendOtp(String email) {
        String otpCode = OTPUtil.generateOTP();

        // Set OTP expiration time (e.g., 5 minutes)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Create and store OtpDTO
        OtpDTO otpDto = new OtpDTO(email, otpCode, expirationTime);
        otpStorage.put(email, otpDto);

        // Send OTP via email
        emailService.sendOTP(email, otpCode);
    }


    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public class EmailServiceException extends RuntimeException {
        public EmailServiceException(String message) {
            super(message);
        }
    }


//    public void resendOtp(String email) {
//        // Generate a new OTP and update it in the storage (DB or in-memory)
//        String newOtp = OTPUtil.generateOTP(); // Generate a new OTP
//        storeOtp(email, newOtp);  // Store the new OTP in the database or cache
//    }

        // Resend the OTP for the given email
//        public void resendOtp(String email) throws UserNotFoundException, EmailServiceException {
//        System.out.println("Backend Service");
//            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
//            if (!userOpt.isPresent()) {
//                throw new UserNotFoundException("Email not found");
//            }
//
//            UserEntity user = userOpt.get();
//
//            // Generate and send a new OTP
//            String newOtp = OTPUtil.generateOTP();
//            storeOtp(user.getEmail(), newOtp);
//
//            // Send the OTP via email (or other means)
//            boolean emailSent = emailService.sendOTP(user.getEmail(), newOtp);
//            if (!emailSent) {
//                throw new EmailServiceException("Failed to send OTP email.");
//            }
//        }

    @Async
    public void resendOtp(String email) throws UserNotFoundException, EmailServiceException {
        // Check if the email exists in the system
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException("Email not found");
        }

        UserEntity user = userOpt.get();

        // Generate a new OTP
        String newOtp = OTPUtil.generateOTP();

        // Set new OTP expiration time (e.g., 5 minutes)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Create and store the updated OtpDTO (overwriting the previous OTP)
        OtpDTO newOtpDto = new OtpDTO(email, newOtp, expirationTime);
        otpStorage.put(email, newOtpDto);  // Store the new OTP in a map, database, or cache

        // Send the new OTP via email
        boolean emailSent = emailService.sendOTP(email, newOtp);
        if (!emailSent) {
            throw new EmailServiceException("Failed to send OTP email.");
        }

        System.out.println("New OTP generated and sent for email: " + email);
    }





    // Other OTP-related methods such as verifyOtp(), generateOtp(), and storeOtp()...



    @Async
    public boolean verifyOtp(String email, String otpCode) {
        OtpDTO otpDto = otpStorage.get(email);

        if (otpDto == null) {
            throw new RuntimeException("OTP not found for this email.");
        }

        // Check if OTP is expired
        if (otpDto.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }

        // Check if OTP is correct
        return otpDto.getOtpCode().equals(otpCode);
    }


    // Clean up expired OTPs (optional, can be run in a separate thread/service)
    public void cleanExpiredOtps() {
        otpStorage.values().removeIf(otpDto -> otpDto.getExpirationTime().isBefore(LocalDateTime.now()));
    }

    // Sending the OTP via email (you can reuse EmailService)
    private void sendOtpEmail(String email, String otpCode) {
        // Logic to send OTP via email
        System.out.println("Sending OTP: " + otpCode + " to email: " + email);
        // Implement actual email sending logic here.
    }
}
