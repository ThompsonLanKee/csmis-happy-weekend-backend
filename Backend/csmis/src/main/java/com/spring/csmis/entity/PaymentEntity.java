package com.spring.csmis.entity;

import com.spring.csmis.enums.PayStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_no", nullable = true)
    private String voucherNo;

    @Column(name = "payment_date" , nullable = false)
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "menuweek_id", nullable = false) // Renamed for clarity
    private MenuWeekEntity menuWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PayStatus status;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = true) // Renamed for clarity
    private UserEntity cashier;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true) // Renamed for clarity
    private UserEntity manager;

}
