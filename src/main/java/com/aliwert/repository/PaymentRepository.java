package com.aliwert.repository;

import com.aliwert.model.Payment;
import com.aliwert.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
}