package com.ms.project.ms_order.service;

import com.ms.project.ms_order.client.ProductClient;
import com.ms.project.ms_order.dto.OrderRequestDTO;
import com.ms.project.ms_order.dto.OrderResponseDTO;
import com.ms.project.ms_order.dto.ProductResponse;
import com.ms.project.ms_order.exception.OrderNotFoundException;
import com.ms.project.ms_order.model.Order;
import com.ms.project.ms_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceIml implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        validarProdutos(orderRequestDTO);

        var order = Order.fromDto(orderRequestDTO);
        var savedOrder = orderRepository.save(order);

        List<ProductResponse> produtosDoPedido = filtrarProdutosDoPedido(orderRequestDTO);

        return new OrderResponseDTO(savedOrder, produtosDoPedido);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<ProductResponse> todosProdutos = productClient.getAllProducts();

        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    List<ProductResponse> produtosDoPedido = todosProdutos.stream()
                            .filter(p -> order.getProductIds().contains(p.id()))
                            .toList();
                    return new OrderResponseDTO(order, produtosDoPedido);
                })
                .toList();
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        var order = buscarOrderPorId(id);
        List<ProductResponse> produtos = buscarProdutosDoPedido(order);
        return new OrderResponseDTO(order, produtos);
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.productIds() == null || orderRequestDTO.productIds().isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto informado para atualização.");
        }

        var existingOrder = buscarOrderPorId(id);
        validarProdutos(orderRequestDTO);

        existingOrder.updateFromDto(orderRequestDTO);
        var updatedOrder = orderRepository.save(existingOrder);

        List<ProductResponse> produtosDoPedido = filtrarProdutosDoPedido(orderRequestDTO);

        return new OrderResponseDTO(updatedOrder, produtosDoPedido);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

    private Order buscarOrderPorId(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private List<ProductResponse> buscarProdutosDoPedido(Order order) {
        return productClient.getAllProducts().stream()
                .filter(p -> order.getProductIds().contains(p.id()))
                .toList();
    }

    private List<ProductResponse> filtrarProdutosDoPedido(OrderRequestDTO dto) {
        List<ProductResponse> produtosDisponiveis = productClient.getAllProducts();
        return produtosDisponiveis.stream()
                .filter(p -> dto.productIds().contains(p.id()))
                .toList();
    }

    private void validarProdutos(OrderRequestDTO dto) {
        List<ProductResponse> produtosDisponiveis = productClient.getAllProducts();
        Set<Long> idsValidos = produtosDisponiveis.stream()
                .map(ProductResponse::id)
                .collect(Collectors.toSet());

        boolean todosExistem = dto.productIds().stream().allMatch(idsValidos::contains);

        if (!todosExistem) {
            throw new IllegalArgumentException("Um ou mais produtos informados não existem no catálogo.");
        }
    }
}
