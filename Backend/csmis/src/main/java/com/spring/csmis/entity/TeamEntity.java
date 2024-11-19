package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="team")
@Entity
public class TeamEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @ManyToOne()
    @JoinColumn(name = "department_id", nullable = true)
    private DepartmentEntity department;
    @Column(name="isDeleted",columnDefinition = "boolean default false")
    private boolean isDeleted;



}