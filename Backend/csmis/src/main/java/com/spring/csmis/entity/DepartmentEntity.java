package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="department")
@Entity
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @ManyToOne()
    @JoinColumn(name = "division_id", nullable = true)//
    private DivisionEntity division;
    @Column(name="isDeleted",columnDefinition = "boolean default false")
    private boolean isDeleted;
}