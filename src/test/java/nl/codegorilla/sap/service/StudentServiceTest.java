package nl.codegorilla.sap.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.codegorilla.sap.exception.StudentNotFoundException;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    /**
     * Method under test: {@link StudentService#findAllStudents()}
     */
    @Test
    void testFindAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(studentList);
        List<Student> actualFindAllStudentsResult = studentService.findAllStudents();
        assertSame(studentList, actualFindAllStudentsResult);
        assertTrue(actualFindAllStudentsResult.isEmpty());
        verify(studentRepository).findAll();
    }

    /**
     * Method under test: {@link StudentService#findAllStudents()}
     */
    @Test
    void testFindAllStudents2() {
        when(studentRepository.findAll()).thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> studentService.findAllStudents());
        verify(studentRepository).findAll();
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent() {
        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.org");

        when(studentRepository.save((Student) any())).thenReturn(student);
        assertSame(student, studentService.addStudent(new Student(1L, "Jane", "Doe", "jane.doe@example.org")));
        verify(studentRepository).save((Student) any());
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent2() {
        when(studentRepository.save((Student) any())).thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class,
                () -> studentService.addStudent(new Student(1L, "Jane", "Doe", "jane.doe@example.org")));
        verify(studentRepository).save((Student) any());
    }

    /**
     * Method under test: {@link StudentService#findStudentById(Long)}
     */
    @Test
    void testFindStudentById() {
        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.org");

        when(studentRepository.findStudentById((Long) any())).thenReturn(Optional.of(student));
        assertSame(student, studentService.findStudentById(1L));
        verify(studentRepository).findStudentById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#findStudentById(Long)}
     */
    @Test
    void testFindStudentById2() {
        when(studentRepository.findStudentById((Long) any())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentById(1L));
        verify(studentRepository).findStudentById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#findStudentById(Long)}
     */
    @Test
    void testFindStudentById3() {
        when(studentRepository.findStudentById((Long) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentById(1L));
        verify(studentRepository).findStudentById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#updateStudent(Student)}
     */
    @Test
    void testUpdateStudent() {
        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.org");

        when(studentRepository.save((Student) any())).thenReturn(student);
        assertSame(student, studentService.updateStudent(new Student(1L, "Jane", "Doe", "jane.doe@example.org")));
        verify(studentRepository).save((Student) any());
    }

    /**
     * Method under test: {@link StudentService#updateStudent(Student)}
     */
    @Test
    void testUpdateStudent2() {
        when(studentRepository.save((Student) any())).thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class,
                () -> studentService.updateStudent(new Student(1L, "Jane", "Doe", "jane.doe@example.org")));
        verify(studentRepository).save((Student) any());
    }

    /**
     * Method under test: {@link StudentService#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById((Long) any());
        studentService.deleteStudent(1L);
        verify(studentRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent2() {
        doThrow(new StudentNotFoundException("An error occurred")).when(studentRepository).deleteById((Long) any());
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(1L));
        verify(studentRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#findStudentByEmail(String)}
     */
    @Test
    void testFindStudentByEmail() {
        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.org");

        when(studentRepository.findStudentByEmail((String) any())).thenReturn(Optional.of(student));
        assertSame(student, studentService.findStudentByEmail("jane.doe@example.org"));
        verify(studentRepository).findStudentByEmail((String) any());
    }

    /**
     * Method under test: {@link StudentService#findStudentByEmail(String)}
     */
    @Test
    void testFindStudentByEmail2() {
        when(studentRepository.findStudentByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentByEmail("jane.doe@example.org"));
        verify(studentRepository).findStudentByEmail((String) any());
    }

    /**
     * Method under test: {@link StudentService#findStudentByEmail(String)}
     */
    @Test
    void testFindStudentByEmail3() {
        when(studentRepository.findStudentByEmail((String) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentByEmail("jane.doe@example.org"));
        verify(studentRepository).findStudentByEmail((String) any());
    }
}

