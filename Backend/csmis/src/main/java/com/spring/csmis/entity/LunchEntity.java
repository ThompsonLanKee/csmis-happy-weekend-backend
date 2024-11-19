//package com.spring.csmis.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "lunch")
//public class LunchEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "day_name", nullable = false, length = 40)
//    private String day_name;
//
//    @Column(name ="date" , nullable = false, length = 40, unique = true)
//    private LocalDate date;
//
//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "lunch_has_menu" ,
//    joinColumns = @JoinColumn(name = "lunch_id"),
//    inverseJoinColumns = @JoinColumn(name = "menu_id"))
//    private List<MealEntity> menulist = new ArrayList();
//
//}