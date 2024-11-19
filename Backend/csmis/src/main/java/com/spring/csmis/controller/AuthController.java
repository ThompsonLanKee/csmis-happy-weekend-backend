package com.spring.csmis.controller;

import com.spring.csmis.dto.ChangePasswordRequest;
import com.spring.csmis.repository.UserRepository;
import com.spring.csmis.security.LoginRequest;
import com.spring.csmis.security.jwt.JwtResponse;
import com.spring.csmis.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // Replace with your Angular app URL
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseEntity<JwtResponse> response = authService.login(loginRequest);

        JwtResponse jwtResponse = response.getBody();
        if (jwtResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        } else if (jwtResponse.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        return response; // Return the JWT response directly if authentication is successful
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String result = authService.changePassword(changePasswordRequest);

        if ("PASSWORD_CHANGED".equals(result)) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to change password.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully.");
    }
}
