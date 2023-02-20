package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.exception.CourseNotFoundException;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    private final CourseRepository courseRepository;


    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<?> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.status(200).body(courses);
    }

    public ResponseEntity<?> addCourse(Course course) {
        Course newCourse = courseRepository.save(course);
        return ResponseEntity.status(201).body(newCourse);
    }

    @Transactional
    public ResponseEntity<?> deleteCourse(Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.status(200).body(Map.of("message", "Course deleted."));
    }

    public Course findCourseByName(String name) {
        return courseRepository.findCourseByName(name)
                .orElseThrow(() -> new CourseNotFoundException("Course with name: " + name + " not found."));
    }
}
