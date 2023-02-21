package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.model.*;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseStatusService {

    private final StudentService studentService;


    private final CourseStatusRepository courseStatusRepository;

    private final CourseService courseService;


    public CourseStatusService(StudentService studentService, CourseStatusRepository courseStatusRepository, CourseService courseService) {
        this.studentService = studentService;
        this.courseStatusRepository = courseStatusRepository;
        this.courseService = courseService;
    }

    public List<CourseStatus> findAllCoursesStatus() {
        return courseStatusRepository.findAll();
    }

    public CourseStatus addCourseStatus(CourseStatus courseStatus) {
        return courseStatusRepository.save(courseStatus);
    }

    @Transactional
    public void deleteCourseById(Long id) {
        courseStatusRepository.deleteById(id);
    }

    public Map<String, String> isGraduated(CourseStatusInput courseStatusInput) {
        Optional<Student> student = studentService.findStudentByEmail(courseStatusInput.getEmail());
        Course course = courseService.findCourseByName(courseStatusInput.getCourseName());

         // add check if course was not found.
        if (student.isEmpty())  {
            return Map.of("status", "false");
        }

        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentIdAndCourseId(student.get().getId(), course.getId());

        if (courseStatus.isPresent() && "Completed".equals(courseStatus.get().getStatus())) {
            return Map.of("status", "true");
        }

        return Map.of("status", "false");
    }

    public CourseStatus addCourseStatus(CourseNameStatus courseNameStatus) {
        Student student = studentService.findStudentById(courseNameStatus.getId());
        Course course = courseService.findCourseByName(courseNameStatus.getCourseName());
        String status = courseNameStatus.getStatus();

        CourseStatus courseStatus = new CourseStatus(student, course, status);
        courseStatusRepository.save(courseStatus);
        return courseStatus;
    }

    public Map<String, String> courseNameStatusList(Long id) {
        List<CourseStatus> courseStatusList = courseStatusRepository.findAllByStudentId(id);

        Map<String, String> courseNameAndCourseStatus = new HashMap<>();

        for (CourseStatus courseStatus : courseStatusList) {
            courseNameAndCourseStatus.put(courseStatus.getCourse().getName(), courseStatus.getStatus());
        }

        return courseNameAndCourseStatus;
    }


}
