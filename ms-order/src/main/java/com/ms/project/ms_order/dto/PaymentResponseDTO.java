package com.ms.project.ms_order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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