package com.makini.university.repositories;

import com.makini.university.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, BigDecimal> {
    Optional<Payment> findByTransactionReference(String transactionReference);

    boolean existsByTransactionReference(String transactionReference);

    List<Payment> findByStudentIdOrderByPaymentDateDesc(String studentId);

    List<Payment> findByPaymentMethodOrderByPaymentDateDesc(String paymentMethod);

    List<Payment> findByPaymentStatus(String paymentStatus);

    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate " +
            "ORDER BY p.paymentDate DESC")
    List<Payment> findPaymentsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(p.amount), 0.0) FROM Payment p " +
            "WHERE p.studentId = :studentId AND p.paymentStatus = 'SUCCESS'")
    Double getTotalAmountByStudentId(@Param("studentId") String studentId);

    @Query("SELECT p.paymentMethod, COUNT(p), SUM(p.amount) FROM Payment p " +
            "WHERE p.paymentStatus = 'SUCCESS' GROUP BY p.paymentMethod")
    List<Object[]> getPaymentSummaryByMethod();


    @Query("SELECT p FROM Payment p WHERE p.createdAt >= :since ORDER BY p.createdAt DESC")
    List<Payment> findRecentPayments(@Param("since") LocalDateTime since);
}
