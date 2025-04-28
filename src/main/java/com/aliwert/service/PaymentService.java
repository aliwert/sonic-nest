package com.aliwert.service;

import com.aliwert.dto.DtoPayment;
import com.aliwert.dto.insert.DtoPaymentInsert;
import com.aliwert.dto.update.DtoPaymentUpdate;
import com.aliwert.model.Payment;

import java.util.List;

public interface PaymentService {
    List<DtoPayment> getAllPayments();
    List<DtoPayment> getPaymentsByUserId(Long userId);
    DtoPayment getPaymentById(Long id);
    DtoPayment getPaymentByTransactionId(String transactionId);
    List<DtoPayment> getPaymentsByStatus(Payment.PaymentStatus status);
    DtoPayment createPayment(DtoPaymentInsert dtoPaymentInsert);
    DtoPayment updatePayment(Long id, DtoPaymentUpdate dtoPaymentUpdate);
    DtoPayment processPayment(Long id);
    DtoPayment refundPayment(Long id);
    void deletePayment(Long id);
}