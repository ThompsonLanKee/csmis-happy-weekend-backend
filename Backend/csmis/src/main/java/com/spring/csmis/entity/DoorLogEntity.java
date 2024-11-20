package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doorlog")
public class DoorLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String doorLogNo;

    @OneToOne(mappedBy = "doorLog")
    private EmployeeEntity employee;


    @Column(nullable = false)
    private boolean deleted = false;  // Soft delete flag
//    @OneToMany(mappedBy = "doorLog", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<DoorLogAccessTransactionEntity> accessTransactions = new ArrayList<>();

    // Getters and Setters
}
