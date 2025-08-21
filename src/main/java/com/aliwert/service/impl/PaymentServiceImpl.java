package com.aliwert.service.impl;

import com.aliwert.dto.DtoPayment;
import com.aliwert.dto.insert.DtoPaymentInsert;
import com.aliwert.dto.update.DtoPaymentUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
import com.aliwert.mapper.PaymentMapper;
import com.aliwert.model.Payment;
import com.aliwert.model.User;
import com.aliwert.repository.PaymentRepository;
import com.aliwert.repository.UserRepository;
import com.aliwert.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<DtoPayment> getAllPayments() {
        return paymentMapper.toDtoList(paymentRepository.findAll());
    }

    @Override
    public List<DtoPayment> getPaymentsByUserId(Long userId) {
        return paymentMapper.toDtoList(paymentRepository.findByUserId(userId));
    }

    @Override
    public DtoPayment getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        return paymentMapper.toDto(payment);
    }

    @Override
    public DtoPayment getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        return paymentMapper.toDto(payment);
    }

    @Override
    public List<DtoPayment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentMapper.toDtoList(paymentRepository.findByStatus(status));
    }

    @Override
    public DtoPayment createPayment(DtoPaymentInsert dtoPaymentInsert) {
        Payment payment = paymentMapper.toEntity(dtoPaymentInsert);
        
        // set required fields that are not in the DTO
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        
        // set user relationship
        if (dtoPaymentInsert.getUserId() != null) {
            User user = userRepository.findById(dtoPaymentInsert.getUserId())
                    .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
            payment.setUser(user);
        }
        
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public DtoPayment updatePayment(Long id, DtoPaymentUpdate dtoPaymentUpdate) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        
        paymentMapper.updateEntityFromDto(dtoPaymentUpdate, payment);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public DtoPayment processPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        
        if (payment.getStatus() != Payment.PaymentStatus.PENDING) {
            throw new RuntimeException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, 
                "Can only process payments with PENDING status").prepareErrorMessage());
        }
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public DtoPayment refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        
        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new RuntimeException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, 
                "Can only refund payments with COMPLETED status").prepareErrorMessage());
        }
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}