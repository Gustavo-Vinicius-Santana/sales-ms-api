package com.ms.project.ms_order.service;

import com.ms.project.ms_order.dto.PaymentMessageDTO; // MUDOU AQUI
import com.ms.project.ms_order.dto.PaymentResponseDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key-order}")
    private String orderRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPaymentRequest(PaymentMessageDTO paymentMessage) { // MUDOU AQUI
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, paymentMessage);
        System.out.println("✅ Payment request sent for order: " + paymentMessage.getOrderId());
    }

    @RabbitListener(queues = "${app.rabbitmq.queue-payment-response}")
    public void receivePaymentResponse(PaymentResponseDTO paymentResponse) {
        System.out.println("✅ Payment response received for order: " + paymentResponse.getPedidoId());
        System.out.println("📊 Payment Status: " + paymentResponse.getStatus());
        System.out.println("💰 Payment Amount: " + paymentResponse.getValor());

        // Aqui você pode atualizar o status do pedido com base na resposta do pagamento
        // Exemplo: orderService.updateOrderStatus(paymentResponse.getPedidoId(), paymentResponse.getStatus());
    }
}