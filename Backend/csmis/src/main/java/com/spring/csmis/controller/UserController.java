package com.spring.csmis.controller;

import com.spring.csmis.dto.UserDTO;
import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.service.UserProfileService;
import com.spring.csmis.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllActiveUsers();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserEntity> getUserProfile(@PathVariable Long id) {
        UserEntity user = userProfileService.getUserProfile(id);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{id}")
    public String softDeleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return "User soft deleted successfully";
    }



    @PutMapping("/update-email")
    public ResponseEntity<UserEntity> updateEmail(@RequestParam Long userId, @RequestParam String newEmail) {
        UserEntity updatedUser = userProfileService.updateEmail(userId, newEmail);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam Long userId, @RequestParam("file") MultipartFile file) {
        String photoUrl = userProfileService.uploadPhoto(userId, file);
        return ResponseEntity.ok(photoUrl);
    }
    @PutMapping("/user/{userId}/name")
    public ResponseEntity<UserEntity> updateUserName(@PathVariable Long userId, @RequestBody String newName) {
        UserEntity updatedUser = userProfileService.updateUserName(userId, newName);
        return ResponseEntity.ok(updatedUser);
    }


}
