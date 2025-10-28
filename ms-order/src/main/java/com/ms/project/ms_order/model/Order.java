package com.ms.project.ms_order.model;

import com.ms.project.ms_order.dto.OrderRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    public static Order fromDto(OrderRequestDTO dto) {
        Order order = new Order();
        order.setOrderDate(dto.orderDate() != null ? dto.orderDate() : LocalDateTime.now());
        order.setProductIds(dto.productIds());
        order.setStatus(dto.status() != null ? dto.status() : OrderStatus.CREATED);
        return order;
    }

    public void updateFromDto(OrderRequestDTO dto) {
        if (dto.orderDate() != null) {
            this.orderDate = dto.orderDate();
        }
        if (dto.productIds() != null) {
            this.productIds = dto.productIds();
        }
        if (dto.status() != null) {
            this.status = dto.status();
        }
    }
}
