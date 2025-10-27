package com.makini.university.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentValidationResponse {
    @JsonProperty("valid")
    private boolean valid;

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("program")
    private String program;

    @JsonProperty("yearOfStudy")
    private Integer yearOfStudy;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("message")
    private String message;
}
