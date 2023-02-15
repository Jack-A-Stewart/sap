package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.CourseStatusInput;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.repository.CourseRepository;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseStatusService {

    private final StudentService studentService;

    private final CourseStatusRepository courseStatusRepository;

    private final CourseRepository courseRepository;

    public CourseStatusService(StudentService studentService, CourseStatusRepository courseStatusRepository, CourseRepository courseRepository) {
        this.studentService = studentService;
        this.courseStatusRepository = courseStatusRepository;
        this.courseRepository = courseRepository;
    }


    public ResponseEntity<?> findAllCoursesStatus() {
        List<CourseStatus> courseStatuses = courseStatusRepository.findAll();
        return ResponseEntity.status(200).body(courseStatuses);
    }

    public ResponseEntity<?> addCourseStatus(CourseStatus courseStatus) {
        CourseStatus newCourseStatus = courseStatusRepository.save(courseStatus);
        return ResponseEntity.status(201).body(newCourseStatus);
    }

    @Transactional
    public ResponseEntity<?> deleteCourseById(Long id) {
        courseStatusRepository.deleteById(id);
        return ResponseEntity.status(200).body(Map.of("message", "Course Status deleted."));
    }

    public ResponseEntity<Map<String, String>> isGraduated(CourseStatusInput courseStatusInput) {
        Optional<Student> student = studentService.findStudentByEmail(courseStatusInput.getEmail());
        Optional<Course> course = courseRepository.findCourseByName(courseStatusInput.getCourseName());

        if (student.isEmpty() || course.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("status", "false"));
        }

        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentIdAndCourseId(student.get().getId(), course.get().getId());

        if (courseStatus.isPresent() && "Completed".equals(courseStatus.get().getStatus())) {
            return ResponseEntity.ok(Map.of("status", "true"));
        }

        return ResponseEntity.status(404).body(Map.of("status", "false"));
    }


}
