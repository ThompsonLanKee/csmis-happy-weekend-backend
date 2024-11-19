package com.spring.csmis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.csmis.enums.StatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="staff_id")
    private String staffID;

    @Column(name = "name")
    private String name;  // It seems like you want this to be unique

    @Column(name="email")
    private String email;



    @Column(name="joined_at")
    private Date joined_at;

    @Column(name="isDeleted",columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar (255) default 'ACTIVE'")
    private StatusType status;


    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private DivisionEntity division;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

//    @OneToOne(mappedBy = "employee")
//    private UserEntity user;  // The reciprocal relationship


    @OneToOne
    @JoinColumn(name = "doorlog_no", referencedColumnName = "doorLogNo", nullable = false, unique = true)
    private DoorLogEntity doorLog;


    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = StatusType.valueOf("ACTIVE"); // Default value
        }
    }
}