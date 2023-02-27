package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.exception.CourseNotFoundException;
import nl.codegorilla.sap.model.*;
import nl.codegorilla.sap.model.dto.CourseNameStatusDTO;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Map<String, String> isGraduated(CourseStatusInputDTO courseStatusInput) {
        Optional<Student> student = studentService.findStudentByEmail(courseStatusInput.getEmail());
        Course course;
        try {
            course = courseService.findCourseByName(courseStatusInput.getCourseName());
        } catch (CourseNotFoundException courseNotFoundException) {
            return Map.of("status", "false");
        }


        // add check if course was not found.
        if (student.isEmpty()) {
            return Map.of("status", "false");
        }


        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentIdAndCourseId(student.get().getId(), course.getId());

        if (courseStatus.isPresent() && "Completed".equals(courseStatus.get().getStatus())) {
            return Map.of("status", "true");
        }

        return Map.of("status", "false");
    }


    // adds a new courseStatus
    public CourseStatus addCourseStatus(CourseNameStatusDTO courseNameStatus) {
        Student student = studentService.findStudentById(courseNameStatus.getId());
        Course course = courseService.findCourseByName(courseNameStatus.getCourseName());
        String status = courseNameStatus.getStatus();

        CourseStatus courseStatus = new CourseStatus(student, course, status);
        courseStatusRepository.save(courseStatus);
        return courseStatus;
    }


    // gets a list of all the courseStatus's with the given student ID
    public List<CourseNameStatusDTO> courseNameStatusList(Long id) {
        List<CourseStatus> courseStatusList = courseStatusRepository.findAllByStudentId(id);

        List<CourseNameStatusDTO> courseNameStatusList = new ArrayList<>();

        for (CourseStatus courseStatus : courseStatusList) {
            courseNameStatusList.add(new CourseNameStatusDTO(courseStatus.getId().getCourseId(), courseStatus.getCourse().getName(), courseStatus.getStatus()));
        }

        return courseNameStatusList;
    }


}
