package com.aliwert.dto;

import com.aliwert.model.Payment;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoPayment extends DtoBaseEntity {
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentStatus status;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String description;
    private Long userId;
    private String username;
}