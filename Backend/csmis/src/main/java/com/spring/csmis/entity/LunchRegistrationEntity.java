package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lunchregistration")
public class LunchRegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "month_id", nullable = false)
    private MonthEntity month;

    @ElementCollection
    @CollectionTable(name = "daily_lunch_status", joinColumns = @JoinColumn(name = "registration_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "hasLunch")
    private Map<Integer, Boolean> dailyLunchStatus = new HashMap<>();
    // Each day maps to a lunch status (true for "Having Lunch", false for "Not Having Lunch")

    @ManyToMany
    @JoinTable(name = "registration_avoid_meat",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "meat_id"))
    private Set<AvoidMeatEntity> avoidMeats = new HashSet<>();
    // List of meats to avoid

    //private LocalTime modificationDeadline;  // Optional field to set deadline time, like 3:00 PM

    // Constructors, Getters, and Setters
}
