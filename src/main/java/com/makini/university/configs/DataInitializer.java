package com.makini.university.configs;

import com.makini.university.entities.Student;
import com.makini.university.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Student> students = Arrays.asList(
                createStudent("STU001", "John", "Mwangi", "john.mwangi@makini.ac.ke",
                        "+254712345678", "Computer Science", 3),
                createStudent("STU002", "Jane", "Nashipai", "jane.nashipai@makini.ac.ke",
                        "+254723456789", "Business Administration", 2),
                createStudent("STU003", "Peter", "Omondi", "peter.omondi@makini.ac.ke",
                        "+254734567890", "Engineering", 4),
                createStudent("STU004", "Mary", "Akinyi", "mary.akinyi@makini.ac.ke",
                        "+254745678901", "Medicine", 5),
                createStudent("STU005", "David", "Kamau", "david.kamau@makini.ac.ke",
                        "+254756789012", "Law", 1),
                createStudent("STU006", "Sarah", "Njeri", "sarah.njeri@makini.ac.ke",
                        "+254767890123", "Computer Science", 2),
                createStudent("STU007", "James", "Otieno", "james.otieno@makini.ac.ke",
                        "+254778901234", "Architecture", 3),
                createStudent("STU008", "Grace", "Moraa", "grace.moraa@makini.ac.ke",
                        "+254789012345", "Education", 4),
                createStudent("STU009","Carren","Chepkorir","carren.chepkorir@makini.ac.ke",
                        "+254742295861","Computer Science",4)
        );
        studentRepository.saveAll(students);

    }
    private Student createStudent(String id, String firstName, String lastName,
                                  String email, String phone, String program, int year) {
        Student student = new Student();
        student.setStudentId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhoneNumber(phone);
        student.setProgram(program);
        student.setYearOfStudy(year);
        student.setEnrollmentDate(LocalDate.now().minusYears(year - 1));
        student.setIsActive(true);
        student.setAccountBalance(0.0);
        return student;
    }
}
