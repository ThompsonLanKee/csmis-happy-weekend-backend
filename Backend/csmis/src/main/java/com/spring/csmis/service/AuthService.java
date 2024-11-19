package com.spring.csmis.service;

import com.spring.csmis.component.util.JwtUtil;
import com.spring.csmis.dto.ChangePasswordRequest;
import com.spring.csmis.entity.EmployeeEntity;
import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.enums.RoleType;
import com.spring.csmis.repository.EmployeeRepository;
import com.spring.csmis.repository.UserRepository;
import com.spring.csmis.security.LoginRequest;
import com.spring.csmis.security.jwt.JwtResponse;
import com.spring.csmis.service.user.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String DEFAULT_PASSWORD = "123456";

    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
        // Check if staffId exists in EmployeeEntity
        EmployeeEntity employee = employeeRepository.findByStaffID(loginRequest.getStaffId());
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse(null, null, null, null, null, false));
        }

        // Check if staffId exists in UserEntity
        UserEntity user = userRepository.findByEmployeeStaffID(loginRequest.getStaffId());

        if (user == null) {
            if (DEFAULT_PASSWORD.equals(loginRequest.getPassword())) {
                // Register new user with default password
                user = new UserEntity();
                user.setIdentityNo(loginRequest.getStaffId());
                user.setEmployee(employee);
                user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
                user.setName(employee.getName()); // Set name from EmployeeEntity
                user.setRole(RoleType.OPERATOR);

                userRepository.save(user);

                // Authenticate and generate token
                Authentication authentication = authenticateUser(loginRequest);
                String jwtToken = jwtUtil.generateJwtToken((UserDetailsImpl) authentication.getPrincipal());

                Collection<GrantedAuthority> authorities = java.util.Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().name())
                );

                return ResponseEntity.ok(new JwtResponse(jwtToken, user.getId(), user.getEmployee().getEmail(),
                        authorities, loginRequest.getStaffId(), true)); // Set isDefaultPassword to true
            }

            // Invalid credentials for a new user
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtResponse(null, null, null, null, null, false));
        }

        // Existing user found, validate password
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Ensure user name is set (update it if needed)
            if (user.getName() == null || !user.getName().equals(employee.getName())) {
                user.setName(employee.getName());
                userRepository.save(user);
            }

            Authentication authentication = authenticateUser(loginRequest);
            String jwtToken = jwtUtil.generateJwtToken((UserDetailsImpl) authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            boolean isDefaultPassword = DEFAULT_PASSWORD.equals(loginRequest.getPassword());

            Collection<GrantedAuthority> authorities = java.util.Collections.singletonList(
                    new SimpleGrantedAuthority(user.getRole().name())
            );

            return ResponseEntity.ok(new JwtResponse(jwtToken, user.getId(), user.getEmployee().getEmail(),
                    authorities, loginRequest.getStaffId(), isDefaultPassword));
        }

        // Invalid credentials for an existing user
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new JwtResponse(null, null, null, null, null, false));
    }

    // Helper method to authenticate the user
    private Authentication authenticateUser(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getStaffId(), loginRequest.getPassword()));
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        Optional<UserEntity> userOptional = userRepository.findById(changePasswordRequest.getId());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
                return "PASSWORD_CHANGED";
            } else {
                return "CURRENT_PASSWORD_INCORRECT";
            }
        }

        return "USER_NOT_FOUND";
    }
}

