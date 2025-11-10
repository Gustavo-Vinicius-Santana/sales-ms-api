package com.ms.project.ms_order.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String nome,
        int quantidade,
        String descricao,
        BigDecimal preco
) {}
