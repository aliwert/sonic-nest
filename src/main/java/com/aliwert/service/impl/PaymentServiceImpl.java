package com.aliwert.service.impl;

import com.aliwert.dto.DtoPayment;
import com.aliwert.dto.insert.DtoPaymentInsert;
import com.aliwert.dto.update.DtoPaymentUpdate;
import com.aliwert.exception.ErrorMessage;
import com.aliwert.exception.MessageType;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public List<DtoPayment> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoPayment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPayment getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        return convertToDto(payment);
    }

    @Override
    public DtoPayment getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        return convertToDto(payment);
    }

    @Override
    public List<DtoPayment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPayment createPayment(DtoPaymentInsert dtoPaymentInsert) {
        Payment payment = new Payment();
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        updatePaymentFromDto(payment, dtoPaymentInsert);
        return convertToDto(paymentRepository.save(payment));
    }

    @Override
    public DtoPayment updatePayment(Long id, DtoPaymentUpdate dtoPaymentUpdate) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "Payment").prepareErrorMessage()));
        updatePaymentFromDto(payment, dtoPaymentUpdate);
        return convertToDto(paymentRepository.save(payment));
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
        return convertToDto(paymentRepository.save(payment));
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
        return convertToDto(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private DtoPayment convertToDto(Payment payment) {
        DtoPayment dto = new DtoPayment();
        dto.setId(payment.getId());
        dto.setTransactionId(payment.getTransactionId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setStatus(payment.getStatus());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setDescription(payment.getDescription());
        
        // handle user information
        if (payment.getUser() != null) {
            dto.setUserId(payment.getUser().getId());
            dto.setUsername(payment.getUser().getUsername());
        }
        
        if (payment.getCreatedTime() != null) {
            if (payment.getCreatedTime() instanceof java.sql.Date) {
                dto.setCreateTime((java.sql.Date) payment.getCreatedTime());
            } else {
                dto.setCreateTime(new java.sql.Date(payment.getCreatedTime().getTime()));
            }
        }
        
        return dto;
    }

    private void updatePaymentFromDto(Payment payment, Object dto) {
        if (dto instanceof DtoPaymentInsert insert) {
            payment.setAmount(insert.getAmount());
            payment.setCurrency(insert.getCurrency());
            payment.setPaymentMethod(insert.getPaymentMethod());
            payment.setDescription(insert.getDescription());
            
            if (insert.getUserId() != null) {
                User user = userRepository.findById(insert.getUserId())
                        .orElseThrow(() -> new RuntimeException(new ErrorMessage(MessageType.NOT_FOUND, "User").prepareErrorMessage()));
                payment.setUser(user);
            }
        } else if (dto instanceof DtoPaymentUpdate update) {
            if (update.getAmount() != null) {
                payment.setAmount(update.getAmount());
            }
            
            if (update.getCurrency() != null) {
                payment.setCurrency(update.getCurrency());
            }
            
            if (update.getStatus() != null) {
                payment.setStatus(update.getStatus());
            }
            
            if (update.getPaymentMethod() != null) {
                payment.setPaymentMethod(update.getPaymentMethod());
            }
            
            if (update.getPaymentDate() != null) {
                payment.setPaymentDate(update.getPaymentDate());
            }
            
            if (update.getDescription() != null) {
                payment.setDescription(update.getDescription());
            }
        }
    }
}