package com.ms.project.ms_order.dto;

import com.ms.project.ms_order.model.Order;
import com.ms.project.ms_order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        LocalDateTime orderDate,
        List<ProductResponse> products,
        OrderStatus status
) {
    public OrderResponseDTO(Order order, List<ProductResponse> products) {
        this(
                order.getId(),
                order.getOrderDate(),
                products,
                order.getStatus()
        );
    }
}
