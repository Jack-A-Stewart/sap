package nl.codegorilla.sap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.codegorilla.sap.exception.StudentNotFoundException;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.CourseStatusKey;
import nl.codegorilla.sap.model.MailCourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.model.dto.CourseNameStatusDTO;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.model.dto.MailStatusDTO;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CourseStatusService.class})
@ExtendWith(SpringExtension.class)
class CourseStatusServiceTest {
    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseStatusRepository courseStatusRepository;

    @Autowired
    private CourseStatusService courseStatusService;

    @MockBean
    private StudentService studentService;

    /**
     * Method under test: {@link CourseStatusService#findAllCoursesStatus()}
     */
    @Test
    void testFindAllCoursesStatus() {
        ArrayList<CourseStatus> courseStatusList = new ArrayList<>();
        when(courseStatusRepository.findAll()).thenReturn(courseStatusList);
        List<CourseStatus> actualFindAllCoursesStatusResult = courseStatusService.findAllCoursesStatus();
        assertSame(courseStatusList, actualFindAllCoursesStatusResult);
        assertTrue(actualFindAllCoursesStatusResult.isEmpty());
        verify(courseStatusRepository).findAll();
    }

    /**
     * Method under test: {@link CourseStatusService#findAllCoursesStatus()}
     */
    @Test
    void testFindAllCoursesStatus2() {
        when(courseStatusRepository.findAll()).thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.findAllCoursesStatus());
        verify(courseStatusRepository).findAll();
    }

    /**
     * Method under test: {@link CourseStatusService#addCourseStatus(CourseStatus)}
     */
    @Test
    void testAddCourseStatus() {
        CourseStatus courseStatus = new CourseStatus();
        when(courseStatusRepository.save((CourseStatus) any())).thenReturn(courseStatus);
        assertSame(courseStatus, courseStatusService.addCourseStatus(new CourseStatus()));
        verify(courseStatusRepository).save((CourseStatus) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addCourseStatus(CourseStatus)}
     */
    @Test
    void testAddCourseStatus2() {
        when(courseStatusRepository.save((CourseStatus) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.addCourseStatus(new CourseStatus()));
        verify(courseStatusRepository).save((CourseStatus) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addCourseStatus(CourseNameStatusDTO)}
     */
    @Test
    void testAddCourseStatus3() {
        Student student = new Student(1L, "Jane", "Doe", "jane.doe@example.org");

        when(studentService.findStudentById((Long) any())).thenReturn(student);
        when(courseStatusRepository.save((CourseStatus) any())).thenReturn(new CourseStatus());
        Course course = new Course("Name");
        when(courseService.findCourseByName((String) any())).thenReturn(course);
        CourseStatus actualAddCourseStatusResult = courseStatusService.addCourseStatus(new CourseNameStatusDTO());
        assertSame(course, actualAddCourseStatusResult.getCourse());
        assertSame(student, actualAddCourseStatusResult.getStudent());
        assertNull(actualAddCourseStatusResult.getStatus());
        CourseStatusKey id = actualAddCourseStatusResult.getId();
        assertEquals(1L, id.getStudentId());
        assertEquals(0L, id.getCourseId());
        verify(studentService).findStudentById((Long) any());
        verify(courseStatusRepository).save((CourseStatus) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addCourseStatus(CourseNameStatusDTO)}
     */
    @Test
    void testAddCourseStatus4() {
        when(studentService.findStudentById((Long) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.save((CourseStatus) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        assertThrows(StudentNotFoundException.class,
                () -> courseStatusService.addCourseStatus(new CourseNameStatusDTO()));
        verify(studentService).findStudentById((Long) any());
        verify(courseStatusRepository).save((CourseStatus) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#deleteCourseById(Long)}
     */
    @Test
    void testDeleteCourseById() {
        doNothing().when(courseStatusRepository).deleteById((Long) any());
        courseStatusService.deleteCourseById(1L);
        verify(courseStatusRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#deleteCourseById(Long)}
     */
    @Test
    void testDeleteCourseById2() {
        doThrow(new StudentNotFoundException("An error occurred")).when(courseStatusRepository).deleteById((Long) any());
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.deleteCourseById(1L));
        verify(courseStatusRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#isGraduated(CourseStatusInputDTO)}
     */
    @Test
    void testIsGraduated() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(Optional.of(new CourseStatus()));
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        MailStatusDTO actualIsGraduatedResult = courseStatusService
                .isGraduated(new CourseStatusInputDTO("jane.doe@example.org", "Course Name"));
        assertEquals("jane.doe@example.org", actualIsGraduatedResult.getEmail());
        String expectedStatus = Boolean.FALSE.toString();
        assertEquals(expectedStatus, actualIsGraduatedResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#isGraduated(CourseStatusInputDTO)}
     */
    @Test
    void testIsGraduated2() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        assertThrows(StudentNotFoundException.class,
                () -> courseStatusService.isGraduated(new CourseStatusInputDTO("jane.doe@example.org", "Course Name")));
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#isGraduated(CourseStatusInputDTO)}
     */
    @Test
    void testIsGraduated4() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenReturn("Status");
        Optional<CourseStatus> ofResult = Optional.of(courseStatus);
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(ofResult);
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        MailStatusDTO actualIsGraduatedResult = courseStatusService
                .isGraduated(new CourseStatusInputDTO("jane.doe@example.org", "Course Name"));
        assertEquals("jane.doe@example.org", actualIsGraduatedResult.getEmail());
        String expectedStatus = Boolean.FALSE.toString();
        assertEquals(expectedStatus, actualIsGraduatedResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseStatus).getStatus();
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#isGraduated(CourseStatusInputDTO)}
     */
    @Test
    void testIsGraduated5() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenReturn("graduated");
        Optional<CourseStatus> ofResult = Optional.of(courseStatus);
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(ofResult);
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        MailStatusDTO actualIsGraduatedResult = courseStatusService
                .isGraduated(new CourseStatusInputDTO("jane.doe@example.org", "Course Name"));
        assertEquals("jane.doe@example.org", actualIsGraduatedResult.getEmail());
        String expectedStatus = Boolean.TRUE.toString();
        assertEquals(expectedStatus, actualIsGraduatedResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseStatus).getStatus();
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#isGraduated(CourseStatusInputDTO)}
     */
    @Test
    void testIsGraduated6() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(Optional.empty());
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        MailStatusDTO actualIsGraduatedResult = courseStatusService
                .isGraduated(new CourseStatusInputDTO("jane.doe@example.org", "Course Name"));
        assertEquals("jane.doe@example.org", actualIsGraduatedResult.getEmail());
        String expectedStatus = Boolean.FALSE.toString();
        assertEquals(expectedStatus, actualIsGraduatedResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#isGraduated(CourseStatusInputDTO)}
     */
    @Test
    void testIsGraduated9() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenThrow(new StudentNotFoundException("An error occurred"));
        Optional<CourseStatus> ofResult = Optional.of(courseStatus);
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(ofResult);
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));
        assertThrows(StudentNotFoundException.class,
                () -> courseStatusService.isGraduated(new CourseStatusInputDTO("jane.doe@example.org", "Course Name")));
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseStatus).getStatus();
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#courseNameStatusList(Long)}
     */
    @Test
    void testCourseNameStatusList() {
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenReturn(new ArrayList<>());
        assertTrue(courseStatusService.courseNameStatusList(1L).isEmpty());
        verify(courseStatusRepository).findAllByStudentId((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#courseNameStatusList(Long)}
     */
    @Test
    void testCourseNameStatusList3() {
        when(courseStatusRepository.findAllByStudentId((Long) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.courseNameStatusList(1L));
        verify(courseStatusRepository).findAllByStudentId((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#courseNameStatusList(Long)}
     */
    @Test
    void testCourseNameStatusList6() {
        CourseStatusKey courseStatusKey = mock(CourseStatusKey.class);
        when(courseStatusKey.getCourseId()).thenReturn(1L);

        CourseStatus courseStatus = new CourseStatus();
        courseStatus.setCourse(new Course("Name"));
        courseStatus.setId(courseStatusKey);

        ArrayList<CourseStatus> courseStatusList = new ArrayList<>();
        courseStatusList.add(courseStatus);
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenReturn(courseStatusList);
        List<CourseNameStatusDTO> actualCourseNameStatusListResult = courseStatusService.courseNameStatusList(1L);
        assertEquals(1, actualCourseNameStatusListResult.size());
        CourseNameStatusDTO getResult = actualCourseNameStatusListResult.get(0);
        assertEquals("Name", getResult.getCourseName());
        assertNull(getResult.getStatus());
        assertEquals(1L, getResult.getId().longValue());
        verify(courseStatusRepository).findAllByStudentId((Long) any());
        verify(courseStatusKey).getCourseId();
    }

    /**
     * Method under test: {@link CourseStatusService#courseNameStatusList(Long)}
     */
    @Test
    void testCourseNameStatusList7() {
        CourseStatusKey courseStatusKey = mock(CourseStatusKey.class);
        when(courseStatusKey.getCourseId()).thenReturn(1L);
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenReturn("Status");
        when(courseStatus.getCourse()).thenReturn(new Course("Name"));
        when(courseStatus.getId()).thenReturn(new CourseStatusKey(1L, 1L));
        doNothing().when(courseStatus).setId((CourseStatusKey) any());
        courseStatus.setId(courseStatusKey);

        ArrayList<CourseStatus> courseStatusList = new ArrayList<>();
        courseStatusList.add(courseStatus);
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenReturn(courseStatusList);
        List<CourseNameStatusDTO> actualCourseNameStatusListResult = courseStatusService.courseNameStatusList(1L);
        assertEquals(1, actualCourseNameStatusListResult.size());
        CourseNameStatusDTO getResult = actualCourseNameStatusListResult.get(0);
        assertEquals("Name", getResult.getCourseName());
        assertEquals("Status", getResult.getStatus());
        assertEquals(1L, getResult.getId().longValue());
        verify(courseStatusRepository).findAllByStudentId((Long) any());
        verify(courseStatus).getStatus();
        verify(courseStatus).getCourse();
        verify(courseStatus).getId();
        verify(courseStatus).setId((CourseStatusKey) any());
    }

    /**
     * Method under test: {@link CourseStatusService#courseNameStatusList(Long)}
     */
    @Test
    void testCourseNameStatusList8() {
        CourseStatusKey courseStatusKey = mock(CourseStatusKey.class);
        when(courseStatusKey.getCourseId()).thenReturn(1L);
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenThrow(new StudentNotFoundException("An error occurred"));
        when(courseStatus.getCourse()).thenReturn(new Course("Name"));
        when(courseStatus.getId()).thenReturn(new CourseStatusKey(1L, 1L));
        doNothing().when(courseStatus).setId((CourseStatusKey) any());
        courseStatus.setId(courseStatusKey);

        ArrayList<CourseStatus> courseStatusList = new ArrayList<>();
        courseStatusList.add(courseStatus);
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenReturn(courseStatusList);
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.courseNameStatusList(1L));
        verify(courseStatusRepository).findAllByStudentId((Long) any());
        verify(courseStatus).getStatus();
        verify(courseStatus).getCourse();
        verify(courseStatus).getId();
        verify(courseStatus).setId((CourseStatusKey) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addStatus(MailCourseStatus)}
     */
    @Test
    void testAddStatus() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(Optional.of(new CourseStatus()));
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        MailCourseStatus actualAddStatusResult = courseStatusService.addStatus(mailCourseStatus);
        assertSame(mailCourseStatus, actualAddStatusResult);
        assertNull(actualAddStatusResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addStatus(MailCourseStatus)}
     */
    @Test
    void testAddStatus2() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.addStatus(mailCourseStatus));
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addStatus(MailCourseStatus)}
     */
    @Test
    void testAddStatus4() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenReturn("Status");
        Optional<CourseStatus> ofResult = Optional.of(courseStatus);
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(ofResult);
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        MailCourseStatus actualAddStatusResult = courseStatusService.addStatus(mailCourseStatus);
        assertSame(mailCourseStatus, actualAddStatusResult);
        assertEquals("Status", actualAddStatusResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseStatus).getStatus();
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addStatus(MailCourseStatus)}
     */
    @Test
    void testAddStatus5() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(Optional.empty());
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        MailCourseStatus actualAddStatusResult = courseStatusService.addStatus(mailCourseStatus);
        assertSame(mailCourseStatus, actualAddStatusResult);
        assertEquals("Unknown", actualAddStatusResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#addStatus(MailCourseStatus)}
     */
    @Test
    void testAddStatus7() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        CourseStatus courseStatus = mock(CourseStatus.class);
        when(courseStatus.getStatus()).thenThrow(new StudentNotFoundException("An error occurred"));
        Optional<CourseStatus> ofResult = Optional.of(courseStatus);
        when(courseStatusRepository.findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any()))
                .thenReturn(ofResult);
        when(courseService.findCourseByName((String) any())).thenReturn(new Course("Name"));

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        assertThrows(StudentNotFoundException.class, () -> courseStatusService.addStatus(mailCourseStatus));
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findCourseStatusByStudentIdAndCourseId((Long) any(), (Long) any());
        verify(courseStatus).getStatus();
        verify(courseService).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseStatusService#csvCheckMultipleCourses(MailCourseStatus)}
     */
    @Test
    void testCsvCheckMultipleCourses() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenReturn(new ArrayList<>());

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        assertTrue(courseStatusService.csvCheckMultipleCourses(mailCourseStatus).isEmpty());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findAllByStudentId((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#csvCheckMultipleCourses(MailCourseStatus)}
     */
    @Test
    void testCsvCheckMultipleCourses2() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findAllByStudentId((Long) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        List<MailCourseStatus> actualCsvCheckMultipleCoursesResult = courseStatusService
                .csvCheckMultipleCourses(mailCourseStatus);
        assertEquals(1, actualCsvCheckMultipleCoursesResult.size());
        MailCourseStatus getResult = actualCsvCheckMultipleCoursesResult.get(0);
        assertEquals("Unknown", getResult.getCourse());
        assertEquals("Unknown", getResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findAllByStudentId((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#csvCheckMultipleCourses(MailCourseStatus)}
     */
    @Test
    void testCsvCheckMultipleCourses3() {
        when(studentService.findStudentByEmail((String) any()))
                .thenReturn(new Student(1L, "Jane", "Doe", "jane.doe@example.org"));
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenThrow(new RuntimeException());

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        assertThrows(RuntimeException.class, () -> courseStatusService.csvCheckMultipleCourses(mailCourseStatus));
        verify(studentService).findStudentByEmail((String) any());
        verify(courseStatusRepository).findAllByStudentId((Long) any());
    }

    /**
     * Method under test: {@link CourseStatusService#csvCheckMultipleCourses(MailCourseStatus)}
     */
    @Test
    void testCsvCheckMultipleCourses5() {
        Student student = mock(Student.class);
        when(student.getId()).thenThrow(new StudentNotFoundException("An error occurred"));
        when(studentService.findStudentByEmail((String) any())).thenReturn(student);
        when(courseStatusRepository.findAllByStudentId((Long) any())).thenReturn(new ArrayList<>());

        MailCourseStatus mailCourseStatus = new MailCourseStatus();
        mailCourseStatus.setCourse("Course");
        mailCourseStatus.setEmail("jane.doe@example.org");
        mailCourseStatus.setStatus("Status");
        List<MailCourseStatus> actualCsvCheckMultipleCoursesResult = courseStatusService
                .csvCheckMultipleCourses(mailCourseStatus);
        assertEquals(1, actualCsvCheckMultipleCoursesResult.size());
        MailCourseStatus getResult = actualCsvCheckMultipleCoursesResult.get(0);
        assertEquals("Unknown", getResult.getCourse());
        assertEquals("Unknown", getResult.getStatus());
        verify(studentService).findStudentByEmail((String) any());
        verify(student).getId();
    }
}

