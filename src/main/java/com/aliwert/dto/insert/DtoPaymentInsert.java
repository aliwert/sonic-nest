package com.aliwert.dto.insert;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DtoPaymentInsert {
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String description;
    private Long userId;
}