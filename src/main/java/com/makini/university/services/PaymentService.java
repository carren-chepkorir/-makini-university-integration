package com.makini.university.services;

import com.makini.university.dtos.GenericResponse;
import com.makini.university.dtos.PaymentDto;
import com.makini.university.dtos.PaymentNotificationRequest;
import com.makini.university.dtos.PaymentNotificationResponse;
import com.makini.university.entities.Payment;
import jakarta.validation.Valid;

import java.util.List;

public interface PaymentService {
    GenericResponse<PaymentNotificationResponse> processPaymentNotification(@Valid PaymentNotificationRequest request);

    GenericResponse<List<PaymentDto>> getStudentPayments(String studentId);

    GenericResponse<List<PaymentDto>> getPaymentsByMethod(String paymentMethod);

    GenericResponse<List<PaymentDto>> getAllPayments();
}
