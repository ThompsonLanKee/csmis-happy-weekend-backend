package com.spring.csmis.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avoidmeat")
public class AvoidMeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meat_name", nullable = false, unique = true)
    private String meat_name;

    @Column(name="is_delete", columnDefinition ="boolean default false")
    private boolean isDelete;
}
