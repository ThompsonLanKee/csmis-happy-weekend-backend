package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "month")
public class MonthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // e.g., "November"
    private Integer year;  // e.g., 2024

    @ElementCollection
    private Set<Integer> weekends = new HashSet<>();  // Days of the month that are weekends

    @OneToMany(mappedBy = "month")
    private List<HolidayEntity> holidays = new ArrayList<>();  // Days that are holidays

    // Constructors, Getters, and Setters
}
