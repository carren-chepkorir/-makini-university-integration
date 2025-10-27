package com.makini.university.services;

import com.makini.university.dtos.GenericResponse;
import com.makini.university.dtos.StudentValidationRequest;
import com.makini.university.dtos.StudentValidationResponse;
import com.makini.university.entities.Student;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Optional;

public interface StudentService {
    GenericResponse<StudentValidationResponse> validateStudent(@Valid StudentValidationRequest request);

   Student getStudentById(@NotBlank(message = "Student ID is required") String studentId);

    void updateStudentBalance(@NotBlank(message = "Student ID is required") String studentId, @NotNull(message = "Amount is required") @Positive(message = "Amount must be positive") Double amount);
}
