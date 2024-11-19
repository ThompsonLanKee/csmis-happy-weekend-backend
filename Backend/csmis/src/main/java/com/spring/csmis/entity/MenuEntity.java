package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.csmis.enums.DayName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_name", nullable = false)
    private DayName dayName;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "menu_has_meals",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<MealEntity> meals;

//    @Column(name = "price", nullable = true)
//    private double price;

    @Column(name = "no_of_pax", nullable = true)
    private Long no_of_pax;

    @Column(name = "remark", nullable = true)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "optional_meal_id", nullable = true) // Renamed for clarity
    private MealEntity optional;


    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;



    @ManyToMany(mappedBy = "menus")
    @JsonIgnore  // This will prevent infinite recursion
    private List<MenuWeekEntity> menuWeeks;


}
