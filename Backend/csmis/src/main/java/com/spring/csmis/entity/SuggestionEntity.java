//package com.spring.csmis.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "suggestion")
//public class SuggestionEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "content" , nullable = false ,length = 300)
//    private String content;
//
//    @Column(name = "date" , nullable = false)
//    private LocalDate date;
//
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserEntity user;
//
//    @ManyToOne
//    @JoinColumn(name = "menuweek_id", nullable = false)
//    private MenuEntity menu;
//
//    @Column(name = "is_deleted", columnDefinition = "boolean default false")
//    private boolean isDeleted;
//}
