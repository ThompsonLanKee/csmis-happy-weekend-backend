package com.spring.csmis.service;

import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    private final String photoDirectory = "uploads/photos/";

    public UserEntity getUserProfile(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserEntity updateEmail(Long userId, String newEmail) {
        UserEntity user = getUserProfile(userId);
        user.getEmployee().setEmail(newEmail);
        return userRepository.save(user);
    }

    public String uploadPhoto(Long userId, MultipartFile file) {
        try {
            UserEntity user = getUserProfile(userId);
            String fileName = userId + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(photoDirectory + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            user.setPhoto(filePath.toString());
            userRepository.save(user);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload photo", e);
        }
    }

    public UserEntity updateUserName(Long userId, String newName) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure that the name is updated in the associated EmployeeEntity
        user.getEmployee().setName(newName);
        userRepository.save(user);

        return user;
    }

}

