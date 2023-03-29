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

import nl.codegorilla.sap.exception.CourseNotFoundException;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CourseService.class})
@ExtendWith(SpringExtension.class)
class CourseServiceTest {
    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    /**
     * Method under test: {@link CourseService#findAllCourses()}
     */
    @Test
    void testFindAllCourses() {
        ArrayList<Course> courseList = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(courseList);
        List<Course> actualFindAllCoursesResult = courseService.findAllCourses();
        assertSame(courseList, actualFindAllCoursesResult);
        assertTrue(actualFindAllCoursesResult.isEmpty());
        verify(courseRepository).findAll();
    }

    /**
     * Method under test: {@link CourseService#findAllCourses()}
     */
    @Test
    void testFindAllCourses2() {
        when(courseRepository.findAll()).thenThrow(new CourseNotFoundException("An error occurred"));
        assertThrows(CourseNotFoundException.class, () -> courseService.findAllCourses());
        verify(courseRepository).findAll();
    }

    /**
     * Method under test: {@link CourseService#addCourse(Course)}
     */
    @Test
    void testAddCourse() {
        Course course = new Course("Name");
        when(courseRepository.save((Course) any())).thenReturn(course);
        assertSame(course, courseService.addCourse(new Course("Name")));
        verify(courseRepository).save((Course) any());
    }

    /**
     * Method under test: {@link CourseService#addCourse(Course)}
     */
    @Test
    void testAddCourse2() {
        when(courseRepository.save((Course) any())).thenThrow(new CourseNotFoundException("An error occurred"));
        assertThrows(CourseNotFoundException.class, () -> courseService.addCourse(new Course("Name")));
        verify(courseRepository).save((Course) any());
    }

    /**
     * Method under test: {@link CourseService#deleteCourse(Long)}
     */
    @Test
    void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById((Long) any());
        courseService.deleteCourse(1L);
        verify(courseRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link CourseService#deleteCourse(Long)}
     */
    @Test
    void testDeleteCourse2() {
        doThrow(new CourseNotFoundException("An error occurred")).when(courseRepository).deleteById((Long) any());
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(1L));
        verify(courseRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link CourseService#findCourseByName(String)}
     */
    @Test
    void testFindCourseByName() {
        Course course = new Course("Name");
        when(courseRepository.findCourseByName((String) any())).thenReturn(Optional.of(course));
        assertSame(course, courseService.findCourseByName("Name"));
        verify(courseRepository).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseService#findCourseByName(String)}
     */
    @Test
    void testFindCourseByName2() {
        when(courseRepository.findCourseByName((String) any())).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.findCourseByName("Name"));
        verify(courseRepository).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseService#findCourseByName(String)}
     */
    @Test
    void testFindCourseByName3() {
        when(courseRepository.findCourseByName((String) any()))
                .thenThrow(new CourseNotFoundException("An error occurred"));
        assertThrows(CourseNotFoundException.class, () -> courseService.findCourseByName("Name"));
        verify(courseRepository).findCourseByName((String) any());
    }

    /**
     * Method under test: {@link CourseService#findCourseById(Long)}
     */
    @Test
    void testFindCourseById() {
        Course course = new Course("Name");
        when(courseRepository.findCourseById((Long) any())).thenReturn(Optional.of(course));
        assertSame(course, courseService.findCourseById(1L));
        verify(courseRepository).findCourseById((Long) any());
    }

    /**
     * Method under test: {@link CourseService#findCourseById(Long)}
     */
    @Test
    void testFindCourseById2() {
        when(courseRepository.findCourseById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.findCourseById(1L));
        verify(courseRepository).findCourseById((Long) any());
    }

    /**
     * Method under test: {@link CourseService#findCourseById(Long)}
     */
    @Test
    void testFindCourseById3() {
        when(courseRepository.findCourseById((Long) any())).thenThrow(new CourseNotFoundException("An error occurred"));
        assertThrows(CourseNotFoundException.class, () -> courseService.findCourseById(1L));
        verify(courseRepository).findCourseById((Long) any());
    }

    /**
     * Method under test: {@link CourseService#updateCourse(Course)}
     */
    @Test
    void testUpdateCourse() {
        Course course = new Course("Name");
        when(courseRepository.save((Course) any())).thenReturn(course);
        assertSame(course, courseService.updateCourse(new Course("Name")));
        verify(courseRepository).save((Course) any());
    }

    /**
     * Method under test: {@link CourseService#updateCourse(Course)}
     */
    @Test
    void testUpdateCourse2() {
        when(courseRepository.save((Course) any())).thenThrow(new CourseNotFoundException("An error occurred"));
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(new Course("Name")));
        verify(courseRepository).save((Course) any());
    }
}

