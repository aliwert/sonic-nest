package com.aliwert.dto.update;

import com.aliwert.model.Payment;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DtoPaymentUpdate {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentStatus status;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String description;
}