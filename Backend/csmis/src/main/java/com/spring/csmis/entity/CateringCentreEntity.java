package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.csmis.enums.CentreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cateringcentre")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CateringCentreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "centre_name", nullable = false, length = 40, unique = true)
    private String centreName;

    @Column(name = "address", nullable = false, length = 300, unique = true)
    private String centreAddress;

    @Column(name = "phone", nullable = false, length = 25)
    private String centrePhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "centre_type", nullable = false, length = 40)
    private CentreType centreType;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "receiver" ,nullable = false, length = 40)
    private String receiverName;
}