package com.aliwert.controller;

import com.aliwert.dto.DtoPayment;
import com.aliwert.dto.insert.DtoPaymentInsert;
import com.aliwert.dto.update.DtoPaymentUpdate;

import java.util.List;

public interface IPaymentController {
    RootEntity<List<DtoPayment>> getAllPayments();
    RootEntity<List<DtoPayment>> getPaymentsByUserId(Long userId);
    RootEntity<DtoPayment> getPaymentById(Long id);
    RootEntity<DtoPayment> getPaymentByTransactionId(String transactionId);
    RootEntity<List<DtoPayment>> getPaymentsByStatus(String status);
    RootEntity<DtoPayment> createPayment(DtoPaymentInsert dtoPaymentInsert);
    RootEntity<DtoPayment> updatePayment(Long id, DtoPaymentUpdate dtoPaymentUpdate);
    RootEntity<DtoPayment> processPayment(Long id);
    RootEntity<DtoPayment> refundPayment(Long id);
    RootEntity<Void> deletePayment(Long id);
}