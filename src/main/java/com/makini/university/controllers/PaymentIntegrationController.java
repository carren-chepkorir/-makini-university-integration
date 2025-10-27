package com.makini.university.controllers;

import com.makini.university.dtos.*;
import com.makini.university.entities.Payment;
import com.makini.university.enums.ResponseStatusEnum;
import com.makini.university.services.PaymentService;
import com.makini.university.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cellulant")
@RequiredArgsConstructor
@Slf4j
public class PaymentIntegrationController {
    private final StudentService studentService;
    private final PaymentService paymentService;

    @PostMapping("/validate-student")
    public ResponseEntity<GenericResponse<StudentValidationResponse>> validateStudent(
            @Valid @RequestBody StudentValidationRequest request
    ) {
        GenericResponse<StudentValidationResponse> response = studentService.validateStudent(request);
        if (response != null && response.getStatus() != null && response.getStatus() != ResponseStatusEnum.SUCCESS) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/payment-notification")
    public ResponseEntity<GenericResponse<PaymentNotificationResponse>> receivePaymentNotification(
            @Valid @RequestBody PaymentNotificationRequest request) {
        GenericResponse<PaymentNotificationResponse> response = paymentService.processPaymentNotification(request);
        if (response != null && response.getStatus() != null && response.getStatus() != ResponseStatusEnum.SUCCESS) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }

    @GetMapping("/payments/student/{studentId}")
    public ResponseEntity<GenericResponse<List<PaymentDto>>> getStudentPayments(
            @PathVariable String studentId) {
        GenericResponse<List<PaymentDto>> response = paymentService.getStudentPayments(studentId);
        if (response != null && response.getStatus() != null && response.getStatus() != ResponseStatusEnum.SUCCESS) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping("/payments/method/{paymentMethod}")
    public ResponseEntity<GenericResponse<List<PaymentDto>>> getPaymentsByMethod(
            @PathVariable String paymentMethod) {
        GenericResponse<List<PaymentDto>> response = paymentService.getPaymentsByMethod(paymentMethod);
        if (response != null && response.getStatus() != null && response.getStatus() != ResponseStatusEnum.SUCCESS) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<GenericResponse<List<PaymentDto>>> getAllPayments() {
        GenericResponse<List<PaymentDto>> response = paymentService.getAllPayments();
        if (response != null && response.getStatus() != null && response.getStatus() != ResponseStatusEnum.SUCCESS) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }
    @GetMapping("/health")
    public ResponseEntity<GenericResponse<String>> healthCheck() {
        log.info("Health check requested at {}", LocalDateTime.now());

        GenericResponse<String> response = GenericResponse.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("API is running")
                ._embedded("Makini University Payment Integration API v1.0")
                .build();

        return ResponseEntity.ok(response);
    }
}