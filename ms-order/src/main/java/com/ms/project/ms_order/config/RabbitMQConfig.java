package com.ms.project.ms_order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.queue-order}")
    private String orderQueue;

    @Value("${app.rabbitmq.queue-payment-response}")
    private String paymentResponseQueue;

    @Value("${app.rabbitmq.routing-key-order}")
    private String orderRoutingKey;

    @Value("${app.rabbitmq.routing-key-payment-response}")
    private String paymentResponseRoutingKey;

    // ✅ ADICIONE ESTE BEAN
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue orderPaymentQueue() {
        return new Queue(orderQueue, true);
    }

    @Bean
    public Queue paymentResponseQueue() {
        return new Queue(paymentResponseQueue, true);
    }

    @Bean
    public Binding orderBinding(Queue orderPaymentQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(orderPaymentQueue)
                .to(paymentExchange)
                .with(orderRoutingKey);
    }

    @Bean
    public Binding paymentResponseBinding(Queue paymentResponseQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentResponseQueue)
                .to(paymentExchange)
                .with(paymentResponseRoutingKey);
    }
}