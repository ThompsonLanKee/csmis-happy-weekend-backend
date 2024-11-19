package com.spring.csmis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="doorlogaccesstransaction")
public class DoorLogAccessTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="department")
    private String Department;

    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "doorlog_no", referencedColumnName = "doorLogNo", nullable = false)
    private DoorLogEntity doorLog;

    @Column(name="DateTime")
    private Date Date_Time;

    @Column(name="location_id")
    private int location;

    @Column(name="ID_Number")
    private Integer id_no;

    @Column(name="verifyCode")
    private String verifyCode;

    @Column(name="CardNo")
    private Long card_no;





}
