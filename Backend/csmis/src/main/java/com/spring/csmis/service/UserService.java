package com.spring.csmis.service;

import com.spring.csmis.dto.UserDTO;
import com.spring.csmis.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity findByStaffId(String staffId);
    UserEntity registerUser(String staffId);
    boolean changePassword(String staffId, String newPassword);

    void softDeleteUser(Long userId);

    List<UserDTO> getAllActiveUsers();
}