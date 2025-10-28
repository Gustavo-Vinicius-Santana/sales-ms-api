package com.ms.project.ms_order.service;

import com.ms.project.ms_order.dto.OrderRequestDTO;
import com.ms.project.ms_order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);
    void deleteOrder(Long id);
}
