package com.ms.project.ms_order.dto;

import com.ms.project.ms_order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDTO(
        LocalDateTime orderDate,
        List<Long> productIds,
        OrderStatus status
) {
}
