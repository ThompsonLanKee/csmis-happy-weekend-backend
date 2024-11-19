package com.spring.csmis.dto;

import com.spring.csmis.entity.MealEntity;
import com.spring.csmis.enums.DayName;
import com.spring.csmis.enums.PayStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PaymentDTO {
    private Long id;
    private String voucherNo;
    private LocalDate paymentDate;
    private Long menuWeekId;
    private PayStatus status;
    private String paymentMethod;
    private Long cashierId;
    private Long managerId;
}
