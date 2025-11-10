package com.ms.project.ms_payments.service;

import com.ms.project.ms_payments.dto.PaymentMessageDTO; // MUDOU AQUI
import com.ms.project.ms_payments.dto.PaymentResponseDTO;
import com.ms.project.ms_payments.model.Payment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentConsumer {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key-payment-response}")
    private String paymentResponseRoutingKey;

    private final PaymentService paymentService;
    private final RabbitTemplate rabbitTemplate;

    public PaymentConsumer(PaymentService paymentService, RabbitTemplate rabbitTemplate) {
        this.paymentService = paymentService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue-order}")
    public void receivePaymentRequest(PaymentMessageDTO paymentMessage) { // MUDOU AQUI
        System.out.println("✅ Received payment request for order: " + paymentMessage.getOrderId());

        try {
            // Processar o pagamento
            Payment payment = new Payment();
            payment.setCodigoPagamento(paymentMessage.getPaymentCode());
            payment.setValor(paymentMessage.getAmount()); // amount → valor
            payment.setPedidoId(paymentMessage.getOrderId()); // orderId → pedidoId
            payment.setStatus("PROCESSING");
            payment.setDataExpiracao(LocalDateTime.now().plusHours(24));

            Payment processedPayment = paymentService.processPayment(payment);

            // Enviar resposta para o ms-order
            PaymentResponseDTO paymentResponse = new PaymentResponseDTO(
                    processedPayment.getId(),
                    processedPayment.getCodigoPagamento(),
                    processedPayment.getValor(),
                    processedPayment.getPedidoId(),
                    processedPayment.getStatus(),
                    processedPayment.getDataExpiracao()
            );

            rabbitTemplate.convertAndSend(exchange, paymentResponseRoutingKey, paymentResponse);

            System.out.println("✅ Payment processed and response sent: " + processedPayment.getId());

        } catch (Exception e) {
            System.err.println("❌ Error processing payment for order: " + paymentMessage.getOrderId() + " - " + e.getMessage());
        }
    }
}