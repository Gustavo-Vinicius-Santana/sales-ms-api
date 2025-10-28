package com.ms.project.ms_order.service;

import com.ms.project.ms_order.dto.OrderRequestDTO;
import com.ms.project.ms_order.dto.OrderResponseDTO;
import com.ms.project.ms_order.model.Order;
import com.ms.project.ms_order.model.OrderStatus;
import com.ms.project.ms_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceIml implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        var order = Order.fromDto(orderRequestDTO);
        var savedOrder = orderRepository.save(order);
        return new OrderResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDTO::new)
                .toList();
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        var existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        existingOrder.updateFromDto(orderRequestDTO);
        var updatedOrder = orderRepository.save(existingOrder);

        return new OrderResponseDTO(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}