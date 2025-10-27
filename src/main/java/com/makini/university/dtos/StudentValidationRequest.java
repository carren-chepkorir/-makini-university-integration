package com.makini.university.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentValidationRequest {
    @NotBlank(message = "Student ID is required")
    @JsonProperty(value = "studentId")
    private String studentId;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
}
