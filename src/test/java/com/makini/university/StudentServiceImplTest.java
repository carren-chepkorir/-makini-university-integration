package com.makini.university;

import com.makini.university.dtos.GenericResponse;
import com.makini.university.dtos.StudentValidationRequest;
import com.makini.university.dtos.StudentValidationResponse;
import com.makini.university.entities.Student;
import com.makini.university.enums.ResponseStatusEnum;
import com.makini.university.repositories.StudentRepository;
import com.makini.university.services.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student activeStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        activeStudent = new Student();
        activeStudent.setStudentId("STU001");
        activeStudent.setFirstName("John");
        activeStudent.setLastName("Doe");
        activeStudent.setEmail("john.doe@makini.ac.ke");
        activeStudent.setPhoneNumber("+254712345678");
        activeStudent.setProgram("Computer Science");
        activeStudent.setYearOfStudy(3);
        activeStudent.setIsActive(true);
        activeStudent.setAccountBalance(1000.0);
    }


    @Test
    void testValidateStudent_Success() {
        StudentValidationRequest request = new StudentValidationRequest();
        request.setStudentId("STU001");
        request.setEmail("john.doe@makini.ac.ke");
        request.setPhoneNumber("+254712345678");

        when(studentRepository.findById("STU001")).thenReturn(Optional.of(activeStudent));

        GenericResponse<StudentValidationResponse> response = studentService.validateStudent(request);

        assertEquals(ResponseStatusEnum.SUCCESS, response.getStatus());
        assertEquals("Validation of Student Successful", response.getMessage());
        assertNotNull(response.get_embedded());
        assertEquals("John Doe", response.get_embedded().getFullName());
    }

    @Test
    void testValidateStudent_StudentNotFound() {
        StudentValidationRequest request = new StudentValidationRequest();
        request.setStudentId("STU999");

        when(studentRepository.findById("STU999")).thenReturn(Optional.empty());

        GenericResponse<StudentValidationResponse> response = studentService.validateStudent(request);

        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertEquals("Student not found", response.getMessage());
        assertNull(response.get_embedded());
    }


    @Test
    void testValidateStudent_InactiveStudent() {
        activeStudent.setIsActive(false);

        StudentValidationRequest request = new StudentValidationRequest();
        request.setStudentId("STU001");

        when(studentRepository.findById("STU001")).thenReturn(Optional.of(activeStudent));

        GenericResponse<StudentValidationResponse> response = studentService.validateStudent(request);

        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertEquals("Student is not active", response.getMessage());
    }


    @Test
    void testValidateStudent_EmailMismatch() {
        StudentValidationRequest request = new StudentValidationRequest();
        request.setStudentId("STU001");
        request.setEmail("wrong.email@makini.ac.ke");

        when(studentRepository.findById("STU001")).thenReturn(Optional.of(activeStudent));

        GenericResponse<StudentValidationResponse> response = studentService.validateStudent(request);

        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertEquals("Email mismatch for student", response.getMessage());
    }


    @Test
    void testValidateStudent_PhoneMismatch() {
        StudentValidationRequest request = new StudentValidationRequest();
        request.setStudentId("STU001");
        request.setPhoneNumber("+254700000000");

        when(studentRepository.findById("STU001")).thenReturn(Optional.of(activeStudent));

        GenericResponse<StudentValidationResponse> response = studentService.validateStudent(request);

        assertEquals(ResponseStatusEnum.ERROR, response.getStatus());
        assertEquals("Phone number mismatch for student", response.getMessage());
    }


    @Test
    void testGetStudentById_Success() {
        when(studentRepository.findById("STU001")).thenReturn(Optional.of(activeStudent));

        Student student = studentService.getStudentById("STU001");

        assertEquals("STU001", student.getStudentId());
        assertEquals("John", student.getFirstName());
    }


    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById("STU999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> studentService.getStudentById("STU999"));

        assertTrue(ex.getMessage().contains("Student not found"));
    }


    @Test
    void testUpdateStudentBalance_Success() {
        when(studentRepository.findById("STU001")).thenReturn(Optional.of(activeStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(activeStudent);

        studentService.updateStudentBalance("STU001", 500.0);

        verify(studentRepository, times(1)).save(activeStudent);
        assertEquals(1500.0, activeStudent.getAccountBalance());
    }


    @Test
    void testUpdateStudentBalance_StudentNotFound() {
        when(studentRepository.findById("STU999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> studentService.updateStudentBalance("STU999", 500.0));

        assertTrue(ex.getMessage().contains("Student not found"));
    }


    @Test
    void testGetAllActiveStudents() {
        when(studentRepository.findByIsActiveTrue()).thenReturn(List.of(activeStudent));

        List<Student> students = studentService.getAllActiveStudents();

        assertEquals(1, students.size());
        assertEquals("STU001", students.get(0).getStudentId());
    }
}

