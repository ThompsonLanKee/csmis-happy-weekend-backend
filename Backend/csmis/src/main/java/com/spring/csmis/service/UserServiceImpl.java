package com.spring.csmis.service;

import com.spring.csmis.dto.UserDTO;
import com.spring.csmis.entity.EmployeeEntity;
import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.enums.RoleType;
import com.spring.csmis.repository.EmployeeRepository;
import com.spring.csmis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findByStaffId(String staffId) {
        return userRepository.findByEmployeeStaffID(staffId);
    }

    @Override
    public UserEntity registerUser(String staffId) {
        EmployeeEntity employee = employeeRepository.findByStaffID(staffId);

        if (employee == null) {
            throw new IllegalArgumentException("Employee with staffId " + staffId + " not found.");
        }

        // Check if the user already exists
        UserEntity user = userRepository.findByEmployeeStaffID(staffId);

        if (user == null) {
            // Create new user and set default password
            user = new UserEntity();
            user.setEmployee(employee);
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));

            // Assign default OPERATOR role to the user
            user.setRole(RoleType.OPERATOR);  // Directly assigning the RoleType enum to the user

            // Save the user to the database
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public boolean changePassword(String staffId, String newPassword) {
        UserEntity user = userRepository.findByEmployeeStaffID(staffId);

        if (user != null  ) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void softDeleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getEmployee().getName(),
                        user.getEmployee().getEmail(),
                        user.getEmployee().getDepartment().getName(),
                        user.getEmployee().getDivision().getName(),
                        user.getEmployee().getTeam().getName()
                ))
                .collect(Collectors.toList());
    }

}
