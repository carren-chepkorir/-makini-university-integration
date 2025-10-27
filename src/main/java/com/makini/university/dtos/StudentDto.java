package com.makini.university.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("enrollmentDate")
    private LocalDate enrollmentDate;

    @JsonProperty("program")
    private String program;

    @JsonProperty("yearOfStudy")
    private Integer yearOfStudy;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("accountBalance")
    private Double accountBalance;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
