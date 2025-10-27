package com.makini.university.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    @JsonProperty("paymentId")
    private BigDecimal paymentId;

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("transactionReference")
    private String transactionReference;

    @JsonProperty("paymentStatus")
    private String paymentStatus;

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

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
