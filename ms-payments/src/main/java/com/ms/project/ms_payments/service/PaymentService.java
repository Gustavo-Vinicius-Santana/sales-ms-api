package com.ms.project.ms_payments.service;

import com.ms.project.ms_payments.dto.PaymentRequestDTO;
import com.ms.project.ms_payments.dto.PaymentResponseDTO;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    List<PaymentResponseDTO> getAllPayments();
    PaymentResponseDTO getPaymentById(Long id);
    PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO paymentRequestDTO);
    void deletePayment(Long id);
    PaymentResponseDTO registrarPagamento(PaymentRequestDTO paymentRequestDTO);
}