package com.ms.project.ms_payments.service;

import com.ms.project.ms_payments.dto.PaymentRequestDTO;
import com.ms.project.ms_payments.dto.PaymentResponseDTO;
import com.ms.project.ms_payments.model.Payment;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    List<PaymentResponseDTO> getAllPayments();
    PaymentResponseDTO getPaymentById(Long id);
    PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO paymentRequestDTO);
    void deletePayment(Long id);
    PaymentResponseDTO registrarPagamento(PaymentRequestDTO paymentRequestDTO);

    Payment processPayment(Payment payment);
}