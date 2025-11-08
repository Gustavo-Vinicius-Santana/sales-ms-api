package com.ms.project.ms_payments.controller;

import com.ms.project.ms_payments.dto.PaymentRequestDTO;
import com.ms.project.ms_payments.dto.PaymentResponseDTO;
import com.ms.project.ms_payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO createdPayment = paymentService.createPayment(paymentRequestDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @PostMapping("/registrar")
    public ResponseEntity<PaymentResponseDTO> registrarPagamento(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO payment = paymentService.registrarPagamento(paymentRequestDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<PaymentResponseDTO> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        PaymentResponseDTO payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO updatedPayment = paymentService.updatePayment(id, paymentRequestDTO);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}