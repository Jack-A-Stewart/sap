package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseStatusService {

    private final CourseStatusRepository courseStatusRepository;

    public CourseStatusService(CourseStatusRepository courseStatusRepository) {
        this.courseStatusRepository = courseStatusRepository;
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

    public ResponseEntity<?> findCourseStatusById(Long id) {
        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentId(id);
        if (courseStatus.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "CourseStatus with id: " + id + " not found."));
        } else {
            return ResponseEntity.status(200).body(courseStatus.get().getStatus());
        }
    }


}
