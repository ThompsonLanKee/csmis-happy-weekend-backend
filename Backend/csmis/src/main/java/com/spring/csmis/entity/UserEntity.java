package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.csmis.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", unique = true)
    private EmployeeEntity employee;


    private String password;

    @Column(name = "identityNo", unique = true)
    private String identityNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "ENUM('ADMIN', 'OPERATOR')")
    private RoleType role;

    @Column(name = "photo")
    private String photo;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false; // Soft delete flag

    public void setDefaultPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode("123456");
    }
}


