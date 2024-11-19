package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.csmis.enums.MealCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbackcategory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FeedBackCategoryEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @ManyToMany
    @JoinTable(
            name = "feedbackcategory_has_feedbacktypes",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<FeedBackTypeEntity> types;


    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private boolean isDeleted; // Soft delete flag

}