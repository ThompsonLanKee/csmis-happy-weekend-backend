package com.spring.csmis.service;

import com.spring.csmis.dto.PaymentDTO;
import com.spring.csmis.dto.feedback.FeedBackDTO;
import com.spring.csmis.entity.FeedBackEntity;
import com.spring.csmis.entity.PaymentEntity;
import com.spring.csmis.enums.PayStatus;
import com.spring.csmis.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
    private final MenuWeekRepository menuWeekRepository;
    private final ModelMapper modelMapper;

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentServiceImpl(MenuWeekRepository menuWeekRepository, ModelMapper modelMapper,PaymentRepository paymentRepository, UserRepository userRepository) {
        this.menuWeekRepository = menuWeekRepository;
        this.modelMapper = modelMapper;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        PaymentEntity paymentEntity = convertToEntity(paymentDTO);

        // Set Menu, User, and Category
        paymentEntity.setMenuWeek(menuWeekRepository.findById(paymentDTO.getMenuWeekId())
                .orElseThrow(() -> new RuntimeException("MenuWeek Id not found")));
        paymentEntity.setCashier(userRepository.findById(paymentDTO.getCashierId())
                .orElseThrow(() -> new RuntimeException("Cashier Id not found")));
        paymentEntity.setManager(userRepository.findById(paymentDTO.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager Id not found")));

        // Ensure Feedback Types are managed entities

        paymentEntity.setStatus(PayStatus.valueOf("Unpaid"));
        PaymentEntity savedPayment = paymentRepository.save(paymentEntity);
        return convertToDTO(savedPayment);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllRemovedPayments() {
        return null;
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        return null;
    }

    @Override
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        return null;
    }

    @Override
    public void softDeletePayment(Long id) {

    }

    @Override
    public void hardDeletePayment(Long id) {

    }

    @Override
    public void restorePayment(Long id) {

    }

    private PaymentDTO convertToDTO(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, PaymentDTO.class);
    }

    private PaymentEntity convertToEntity(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, PaymentEntity.class);
    }
}
