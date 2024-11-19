package com.spring.csmis.controller;

import com.spring.csmis.dto.LoginUserDTO;
import com.spring.csmis.dto.OtpVerificationRequest;
import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.repository.UserRepository;
import com.spring.csmis.service.OTPService;
import com.spring.csmis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OTPController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private OTPService otpService;

    @Autowired
    private UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Backend - Received email: " + email);  // Debugging

        Optional<UserEntity> foundemail = repo.findByEmail(email);
        System.out.println("found email" + foundemail);  // Debugging
        Map<String, String> response = new HashMap<>();

        try {
            if (foundemail.isPresent()) {
                System.out.println("Email found in DB: " + email);  // Debugging

                // Generate OTP and send
                otpService.sendOtp(email);

                response.put("message", "OTP sent to email");
                return ResponseEntity.ok(response);
            } else {
                System.out.println("Email not found in DB: " + email);  // Debugging
                response.put("error", "Email does not exist in our records.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            System.err.println("Error sending OTP: " + e.getMessage());  // Debugging
            response.put("error", "An error occurred while sending OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestBody Map<String, String> request) {
        System.out.println("Backend Controller");
        String email = request.get("email");
        Map<String, String> response = new HashMap<>();

        try {
            otpService.resendOtp(email);  // Attempt to resend the OTP
            response.put("message", "OTP resent successfully.");
            return ResponseEntity.ok(response);
        } catch (OTPService.UserNotFoundException e) {
            response.put("error", "Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (OTPService.EmailServiceException e) {
            response.put("error", "Error occurred while sending OTP.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            e.printStackTrace();  // Log any unexpected exceptions
            response.put("error", "An unknown error occurred while resending OTP.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getOtpCode());
        System.out.println("Verifying OTP...");
        String email=request.getEmail();
        String otpCode= request.getOtpCode();

        boolean isOtpValid = otpService.verifyOtp(request.getEmail(), request.getOtpCode());

        if (isOtpValid) {
            // OTP is valid, return user details
            Optional<UserEntity> userOpt = repo.findByEmail(request.getEmail());
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                LoginUserDTO loggedInUser = new LoginUserDTO();
                loggedInUser.setUid(user.getId());
                loggedInUser.setUname(user.getName());
                loggedInUser.setEmail(user.getEmployee().getEmail());
                loggedInUser.setRole(user.getRole());

                return ResponseEntity.ok(loggedInUser);
            } else {
                // User not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found for the provided email.");
            }
        } else {
            // Invalid OTP
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid OTP or OTP expired.");
        }
    }
}
