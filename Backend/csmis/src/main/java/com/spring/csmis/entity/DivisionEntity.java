package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="dvision")
@Entity
public class DivisionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="isDeleted",columnDefinition = "boolean default false")
    private boolean isDeleted;
}