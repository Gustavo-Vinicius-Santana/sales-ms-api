package com.ms.project.ms_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessageDTO {
    private Long orderId;
    private BigDecimal amount;
    private String paymentCode;
    private Long customerId;
}