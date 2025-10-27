package com.makini.university.services;

import com.makini.university.dtos.GenericResponse;
import com.makini.university.dtos.PaymentDto;
import com.makini.university.dtos.PaymentNotificationRequest;
import com.makini.university.dtos.PaymentNotificationResponse;
import com.makini.university.entities.Payment;
import com.makini.university.entities.Student;
import com.makini.university.enums.ResponseStatusEnum;
import com.makini.university.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final StudentService studentService;
    private final PaymentRepository paymentRepository;

    @Transactional
    public GenericResponse<PaymentNotificationResponse> processPaymentNotification(PaymentNotificationRequest request) {

        try {
            //  Validate Student Exists and is Active

            log.info("Step 1: Validating student exists...");

            Student student = studentService.getStudentById(request.getStudentId());

            String fullName = student.getFirstName() + " " + student.getLastName();

            // Check if student account is active
            if (!student.getIsActive()) {
                throw new RuntimeException("Payment rejected: Student account is inactive" + request.getStudentId());
            }


            // Determine the transaction reference
            String transactionReference = request.getTransactionReference();

            if (transactionReference == null || transactionReference.isBlank()) {
                transactionReference = "TXN" + UUID.randomUUID().toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
                log.info("Generated new transaction reference: {}", transactionReference);
            } else if (paymentRepository.existsByTransactionReference(transactionReference)) {
                throw new RuntimeException("Duplicate transaction: Payment already processed - " + transactionReference);
            }

            Payment payment = new Payment();
            payment.setStudentId(request.getStudentId());
            payment.setAmount(request.getAmount());
            payment.setCurrency(request.getCurrency() != null ? request.getCurrency() : "KES");
            payment.setPaymentMethod(request.getPaymentMethod());
            payment.setTransactionReference(transactionReference);
            payment.setPaymentStatus(request.getPaymentStatus() != null ?
                    request.getPaymentStatus() : "SUCCESS");

            // use provided date or current timestamp
            payment.setPaymentDate(request.getPaymentDate() != null ?
                    request.getPaymentDate() : LocalDateTime.now());
            payment.setDescription(request.getDescription());
            payment.setCustomerName(request.getCustomerName() != null ?
                    request.getCustomerName() : fullName);
            payment.setCustomerPhone(request.getCustomerPhone() != null ?
                    request.getCustomerPhone() : student.getPhoneNumber());
            payment.setCustomerEmail(request.getCustomerEmail() != null ?
                    request.getCustomerEmail() : student.getEmail());

            log.info("Payment record prepared:");
            log.info("  - Amount: {} {}", payment.getAmount(), payment.getCurrency());
            log.info("  - Method: {}", payment.getPaymentMethod());
            log.info("  - Status: {}", payment.getPaymentStatus());
            log.info("  - Customer: {}", payment.getCustomerName());


            Payment savedPayment = paymentRepository.save(payment);

            log.info(" Payment saved successfully with ID: {}", savedPayment.getPaymentId());


            // Update Student Balance (if payment successful)

            if ("SUCCESS".equalsIgnoreCase(request.getPaymentStatus())) {
                studentService.updateStudentBalance(
                        request.getStudentId(),
                        request.getAmount()
                );

            }
            PaymentNotificationResponse response = PaymentNotificationResponse.builder()
                    .paymentMethod(savedPayment.getPaymentMethod())
                    .transactionReference(transactionReference)
                    .paymentDate(savedPayment.getPaymentDate())
                    .processedAt(LocalDateTime.now())
                    .amount(savedPayment.getAmount())
                    .currency(savedPayment.getCurrency())
                    .studentName(fullName)
                    .paymentId(savedPayment.getPaymentId())
                    .success(true)
                    .message("Payment successfully processed")
                    .build();
            return GenericResponse.<PaymentNotificationResponse>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .status(ResponseStatusEnum.SUCCESS)
                    ._embedded(response)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.<PaymentNotificationResponse>builder()
                    .message("Error processing payment: " + e.getMessage())
                    .status(ResponseStatusEnum.ERROR)
                    .build();

        }
    }

    @Transactional(readOnly = true)
    @Override
    public GenericResponse<List<PaymentDto>> getStudentPayments(String studentId) {
        List<PaymentDto> response = new ArrayList<>();
        try {
            List<Payment> payments = paymentRepository.findByStudentIdOrderByPaymentDateDesc(studentId);
            for (Payment payment : payments) {
                PaymentDto paymentDTO = toPaymentDTO(payment);
                response.add(paymentDTO);
            }
            return GenericResponse.<List<PaymentDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved student payments")
                    ._embedded(response)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.<List<PaymentDto>>builder()
                    .message("Error processing payments: " + e.getMessage())
                    .status(ResponseStatusEnum.ERROR)
                    .build();

        }
    }


    @Transactional(readOnly = true)
    public GenericResponse<List<PaymentDto>> getPaymentsByMethod(String paymentMethod) {
        List<PaymentDto> response = new ArrayList<>();
        try {
            List<Payment> payments = paymentRepository.findByPaymentMethodOrderByPaymentDateDesc(paymentMethod);
            for (Payment payment : payments) {
                PaymentDto paymentDTO = toPaymentDTO(payment);
                response.add(paymentDTO);
            }
            return GenericResponse.<List<PaymentDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved  payments")
                    ._embedded(response)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.<List<PaymentDto>>builder()
                    .message("Error processing payments: " + e.getMessage())
                    .status(ResponseStatusEnum.ERROR)
                    .build();

        }
    }

    @Transactional(readOnly = true)
    public GenericResponse<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> response = new ArrayList<>();
        try {
            List<Payment> payments = paymentRepository.findAll();
            for (Payment payment : payments) {
                PaymentDto paymentDTO = toPaymentDTO(payment);
                response.add(paymentDTO);
            }
            return GenericResponse.<List<PaymentDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved all payments")
                    ._embedded(response)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.<List<PaymentDto>>builder()
                    .message("Error processing payments: " + e.getMessage())
                    .status(ResponseStatusEnum.ERROR)
                    .build();

        }
    }

    public PaymentDto toPaymentDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .studentId(payment.getStudentId())
                .studentName(payment.getStudent() != null
                        ? payment.getStudent().getFirstName() + "." + payment.getStudent().getLastName()
                        : null)
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .transactionReference(payment.getTransactionReference())
                .paymentStatus(payment.getPaymentStatus())
                .paymentDate(payment.getPaymentDate())
                .description(payment.getDescription())
                .customerName(payment.getCustomerName())
                .customerPhone(payment.getCustomerPhone())
                .customerEmail(payment.getCustomerEmail())
                .createdAt(payment.getCreatedAt())
                .build();
    }


}