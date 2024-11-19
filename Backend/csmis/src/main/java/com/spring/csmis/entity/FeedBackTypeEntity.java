package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "feedbacktype")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class FeedBackTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false, length = 40, unique = true)
    private String typeName;



    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private boolean isDeleted; // Soft delete flag


    @ManyToMany(mappedBy = "types")
    private List<FeedBackCategoryEntity> categories;

    @ManyToMany(mappedBy = "types")
    @JsonIgnore  // This will prevent infinite recursion
    private List<FeedBackEntity> feedbacks;

}
