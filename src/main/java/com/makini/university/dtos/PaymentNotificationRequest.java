package com.makini.university.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentNotificationRequest {
    @JsonProperty("studentId")
    @NotBlank(message = "Student ID is required")
    private String studentId;

    @JsonProperty("amount")
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @JsonProperty("transactionReference")
    private String transactionReference;

    @JsonProperty("paymentMethod")
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @JsonProperty("currency")
    private String currency = "KES";

    @JsonProperty("paymentStatus")
    private String paymentStatus = "SUCCESS";

    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

    @JsonProperty("description")
    private String description;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("customerPhone")
    private String customerPhone;

    @JsonProperty("customerEmail")
    private String customerEmail;
}
