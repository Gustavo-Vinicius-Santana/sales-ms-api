package com.ms.project.ms_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private String codigoPagamento;
    private BigDecimal valor;
    private Long pedidoId;
    private String status;
    private LocalDateTime dataExpiracao;
}