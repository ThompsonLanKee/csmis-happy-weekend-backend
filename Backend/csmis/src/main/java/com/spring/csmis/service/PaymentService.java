package com.spring.csmis.service;

import com.spring.csmis.dto.PaymentDTO;


import java.util.List;

public interface PaymentService {
    PaymentDTO addPayment(PaymentDTO paymentDTO);
    List<PaymentDTO> getAllPayments();
    List<PaymentDTO> getAllRemovedPayments();
    PaymentDTO getPaymentById(Long id);
    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO); // New method for updating menu
    void softDeletePayment(Long id);
    void hardDeletePayment(Long id);
    void restorePayment(Long id);  // New method for restoring soft deleted menu

}
