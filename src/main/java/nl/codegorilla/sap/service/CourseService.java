package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.exception.CourseNotFoundException;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;


    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course findCourseByName(String name) {
        return courseRepository.findCourseByName(name)
                .orElseThrow(() -> new CourseNotFoundException("Course with name: " + name + " not found."));
    }

    public Course findCourseById(Long id) {
        return courseRepository.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found."));
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

}
