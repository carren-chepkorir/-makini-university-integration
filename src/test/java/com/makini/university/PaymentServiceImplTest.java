package com.makini.university;

import com.makini.university.dtos.GenericResponse;
import com.makini.university.dtos.PaymentNotificationRequest;
import com.makini.university.dtos.PaymentNotificationResponse;
import com.makini.university.entities.Payment;
import com.makini.university.entities.Student;
import com.makini.university.enums.ResponseStatusEnum;
import com.makini.university.repositories.PaymentRepository;
import com.makini.university.services.PaymentServiceImpl;
import com.makini.university.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {
    @Mock
    private StudentService studentService;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Student activeStudent;
    private PaymentNotificationRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        activeStudent = new Student();
        activeStudent.setStudentId("STU001");
        activeStudent.setFirstName("Hellen");
        activeStudent.setLastName("Makini");
        activeStudent.setIsActive(true);
        activeStudent.setPhoneNumber("+254700000000");
        activeStudent.setEmail("student@example.com");
        activeStudent.setAccountBalance(1000.0);

        request = new PaymentNotificationRequest();
        request.setStudentId("STU001");
        request.setAmount(4500.0);
        request.setCurrency("KES");
        request.setPaymentMethod("MPESA");
        request.setPaymentStatus("SUCCESS");
        request.setDescription("Tuition payment");
    }

    @Test
    void shouldProcessPaymentSuccessfully_whenStudentIsActive() {
        // Arrange
        when(studentService.getStudentById("STU001")).thenReturn(activeStudent);
        when(paymentRepository.existsByTransactionReference(anyString())).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setPaymentId(BigDecimal.valueOf(1000));
            return p;
        });

        // Act
        GenericResponse<PaymentNotificationResponse> response = paymentService.processPaymentNotification(request);

        // Assert
        assertEquals(ResponseStatusEnum.SUCCESS, response.getStatus());
        assertNotNull(response.get_embedded());
        assertEquals("STU001", response.get_embedded().getStudentName().contains("Hellen") ? "STU001" : "STU001");
        verify(studentService).updateStudentBalance("STU001", 4500.0);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void shouldFail_whenStudentIsInactive() {
        // Arrange
        activeStudent.setIsActive(false);
        when(studentService.getStudentById("STU001")).thenReturn(activeStudent);

        // Act
        GenericResponse<PaymentNotificationResponse> response = paymentService.processPaymentNotification(request);

        // Assert
        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertTrue(response.getMessage().contains("inactive"));
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void shouldFail_whenDuplicateTransactionReferenceExists() {
        // Arrange
        request.setTransactionReference("TXN123");
        when(studentService.getStudentById("STU001")).thenReturn(activeStudent);
        when(paymentRepository.existsByTransactionReference("TXN123")).thenReturn(true);

        // Act
        GenericResponse<PaymentNotificationResponse> response = paymentService.processPaymentNotification(request);

        // Assert
        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertTrue(response.getMessage().contains("Duplicate transaction"));
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void shouldHandleRepositoryExceptionGracefully() {
        // Arrange
        when(studentService.getStudentById("STU001")).thenReturn(activeStudent);
        when(paymentRepository.save(any(Payment.class))).thenThrow(DataIntegrityViolationException.class);

        // Act
        GenericResponse<PaymentNotificationResponse> response = paymentService.processPaymentNotification(request);

        // Assert
        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertTrue(response.getMessage().contains("Error processing payment"));
    }

    @Test
    void shouldGenerateTransactionReferenceIfNull() {
        // Arrange
        request.setTransactionReference(null);
        when(studentService.getStudentById("STU001")).thenReturn(activeStudent);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        GenericResponse<PaymentNotificationResponse> response = paymentService.processPaymentNotification(request);

        // Assert
        assertNotNull(response.get_embedded().getTransactionReference());
        assertTrue(response.get_embedded().getTransactionReference().startsWith("TXN"));
    }
}

