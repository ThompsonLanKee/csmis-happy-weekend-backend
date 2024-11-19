package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meal")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meal_name", nullable = false, length = 40, unique = true)
    private String mealName;

    @ManyToOne
    @JoinColumn(name = "mealcategory_id", nullable = false)
    private MealCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "mealtype_id", nullable = false)
    private MealTypeEntity type;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToMany(mappedBy = "meals")
    @JsonIgnore  // This will prevent infinite recursion
    private List<MenuEntity> menus;
}
