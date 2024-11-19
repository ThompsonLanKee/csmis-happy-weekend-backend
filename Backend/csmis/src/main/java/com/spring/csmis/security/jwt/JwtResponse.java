package com.spring.csmis.security.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private String token;
    private Long id;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private boolean isDefaultPassword; // New field

    // Constructor
    public JwtResponse(String token, Long id, String email, Collection<? extends GrantedAuthority> authorities, String username,boolean isDefaultPassword) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.authorities = authorities;
        this.username = username;
        this.isDefaultPassword = isDefaultPassword;

    }

    // Getters and setters
}

//public class JwtResponse {
//    private String token;
//    private String staffId;
//    private String email;
//    private Set<RoleEntity> roles;
//
//    public JwtResponse(String token, String staffId, String email, Set<RoleEntity> roles) {
//        this.token = token;
//        this.staffId = staffId;
//        this.email = email;
//        this.roles = roles;
//    }
//}