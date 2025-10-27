package com.makini.university.services;

import com.makini.university.dtos.GenericResponse;
import com.makini.university.dtos.StudentDto;
import com.makini.university.dtos.StudentValidationRequest;
import com.makini.university.dtos.StudentValidationResponse;
import com.makini.university.entities.Student;
import com.makini.university.enums.ResponseStatusEnum;
import com.makini.university.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    /**
     * Validate student for payment processing
     */
    @Transactional(readOnly = true)
    @Override
    public GenericResponse<StudentValidationResponse> validateStudent(StudentValidationRequest request) {
        log.info("Validating student with ID: {}", request.getStudentId());

        try {
            Student studentOpt = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new NoSuchElementException("Student not found"));


            // Check if student is active
            if (!studentOpt.getIsActive()) {
                throw new RuntimeException("Student is not active");
            }

            if (request.getEmail() != null && !request.getEmail().equals(studentOpt.getEmail())) {
                throw new RuntimeException("Email mismatch for student");
            }

            if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(studentOpt.getPhoneNumber())) {
                throw new RuntimeException("Phone number mismatch for student");
            }
            StudentValidationResponse response = StudentValidationResponse.builder()
                    .isActive(studentOpt.getIsActive())
                    .email(studentOpt.getEmail())
                    .program(studentOpt.getProgram())
                    .yearOfStudy(studentOpt.getYearOfStudy())
                    .fullName(studentOpt.getFirstName() + " " + studentOpt.getLastName())
                    .studentId(studentOpt.getStudentId())
                    .build();

            return GenericResponse.<StudentValidationResponse>builder()
                    .message("Validation of Student Successful")
                    .status(ResponseStatusEnum.SUCCESS)
                    ._embedded(response)
                    .build();
        } catch (RuntimeException e) {
            return GenericResponse.<StudentValidationResponse>builder()
                    .message(e.getMessage())
                    .status(ResponseStatusEnum.ERROR)
                    .build();
        }


    }

    @Transactional(readOnly = true)
    public Student getStudentById(String studentId) {
        try {
          return studentRepository.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Student> getAllActiveStudents() {
        return studentRepository.findByIsActiveTrue();
    }

    @Transactional
    public void updateStudentBalance(String studentId, Double amount) {
        try {
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student not found"));

            student.setAccountBalance(student.getAccountBalance() + amount);
            studentRepository.save(student);
            log.info("Updated balance for student {}: new balance = {}",
                    studentId, student.getAccountBalance());
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e);
        }
    }

}
