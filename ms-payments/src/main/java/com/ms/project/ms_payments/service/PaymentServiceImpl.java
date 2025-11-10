package com.ms.project.ms_payments.service;

import com.ms.project.ms_payments.model.Payment;
import com.ms.project.ms_payments.dto.PaymentRequestDTO;
import com.ms.project.ms_payments.dto.PaymentResponseDTO;
import com.ms.project.ms_payments.repository.PaymentRepository;
import com.ms.project.ms_payments.exception.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private PaymentResponseDTO convertToResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getCodigoPagamento(),
                payment.getValor(),
                payment.getPedidoId(),
                payment.getStatus(),
                payment.getDataExpiracao()
        );
    }

    private Payment convertToEntity(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = new Payment();
        payment.setCodigoPagamento(UUID.randomUUID().toString());
        payment.setValor(paymentRequestDTO.getValor());
        payment.setPedidoId(paymentRequestDTO.getPedidoId());
        payment.setStatus("CONFIRMADO");
        payment.setDataExpiracao(LocalDateTime.now().plusHours(24));
        return payment;
    }

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = convertToEntity(paymentRequestDTO);
        Payment savedPayment = paymentRepository.save(payment);
        return convertToResponseDTO(savedPayment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        return convertToResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO paymentRequestDTO) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        existingPayment.setValor(paymentRequestDTO.getValor());
        existingPayment.setPedidoId(paymentRequestDTO.getPedidoId());

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return convertToResponseDTO(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException(id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentResponseDTO registrarPagamento(PaymentRequestDTO paymentRequestDTO) {
        return createPayment(paymentRequestDTO);
    }
}