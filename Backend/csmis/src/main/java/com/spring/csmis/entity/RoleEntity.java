//package com.spring.csmis.entity;
//
//import com.spring.csmis.enums.RoleType;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "roles")
//public class RoleEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 20)
//    private RoleType name;
//}